package fvarrui.sysadmin.challenger.monitoring;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fvarrui.sysadmin.challenger.command.Command;
import fvarrui.sysadmin.challenger.command.DOSCommand;
import fvarrui.sysadmin.challenger.command.ExecutionResult;
import fvarrui.sysadmin.challenger.command.PSCommand;
import fvarrui.sysadmin.challenger.utils.Chronometer;
import fvarrui.sysadmin.challenger.utils.DateTimeUtils;
import fvarrui.sysadmin.challenger.utils.Sleep;
import fvarrui.sysadmin.challenger.utils.XMLUtils;

public class PSMonitor extends Monitor {
	
	public static final String COMMAND = "command";
	public static final String USERNAME = "username";
	public static final String TIMESTAMP = "timestamp";
	
	private static final long DELAY = 1000L;
	private static final String QUERY_EVENTS_CMD = "wevtutil query-events \"Microsoft-Windows-PowerShell/Operational\" /q:\"*[System[TimeCreated[@SystemTime>'%s']][EventID=4104]]\"";
	
	private long delay;
	private Command command;
	private List<String> excludedCommands;
	
	public PSMonitor(long delay) {
		super("PowerShell Monitor");
		this.delay = delay;
		this.command = new DOSCommand(QUERY_EVENTS_CMD);
		this.excludedCommands = new ArrayList<>();
		this.excludedCommands.add("prompt");
		this.excludedCommands.add("{ Set-StrictMode -Version 1; $_.OriginInfo }");
		this.excludedCommands.add("{ Set-StrictMode -Version 1; $_.ErrorCategory_Message }"); 
		this.excludedCommands.add("{ Set-StrictMode -Version 1; $_.PSMessageDetails }");
		this.excludedCommands.add("{ Set-StrictMode -Version 1; $this.DisplayHint }");
		this.excludedCommands.add("[Microsoft.Windows.PowerShell.Gui.Internal.HostTextWriter]::RegisterHost($host.ui)");
		this.excludedCommands.add("filter more { $_ }");
		this.excludedCommands.add("\n" + 
			"function psEdit([Parameter(Mandatory=$true)]$filenames)\n" + 
			"{\n" + 
			"    foreach ($filename in $filenames)\n" + 
			"    {\n" + 
			"        dir $filename | where {!$_.PSIsContainer} | %{\n" + 
			"            $psISE.CurrentPowerShellTab.Files.Add($_.FullName) > $null\n" + 
			"        }\n" + 
			"    }\n" + 
			"}");
		this.excludedCommands.add("$OutputEncoding = [System.Console]::OutputEncoding");
		this.excludedCommands.add("ipmo ISE");
		this.excludedCommands.add("{ Set-StrictMode -Version 1; $this.Exception.InnerException.PSMessageDetails }");
		this.excludedCommands.add("$global:?");
	}
	
	public PSMonitor() {
		this(DELAY);
	}

	@Override
	public void doWork() {
		String resolveUsernameCommand = "";
		Command resolveUsername = new PSCommand("(Get-LocalUser | Where SID -eq '%s').Name");		
		ZonedDateTime dateTime = ZonedDateTime.now(ZoneOffset.UTC);

		Chronometer chrono = new Chronometer();
		
		do {

			chrono.init();
			
			ExecutionResult result = command.execute(dateTime.toString());
			
			if (!result.getOutput().isEmpty()) {
				
				String xml = "<Events>" + result.getOutput() + "</Events>";
				Document doc = XMLUtils.stringToDocument(xml);
				NodeList nodes = doc.getElementsByTagName("Event");
				for (int i = 0; i < nodes.getLength(); i++) {
					Node node = nodes.item(i);
					
					String command = XMLUtils.searchText(node, "EventData/Data[@Name='ScriptBlockText']");
					String userId = XMLUtils.searchAttribute(node, "System/Security", "UserID");
					String xmlDateTime = XMLUtils.searchAttribute(node, "System/TimeCreated", "SystemTime");
					ZonedDateTime timestamp = DateTimeUtils.xmlInstantToZonedDateTime(xmlDateTime);
										
					if (!excludedCommands.contains(command) && !command.equals(resolveUsernameCommand)) {
						
						ExecutionResult usernameResult = resolveUsername.execute(userId);

						resolveUsernameCommand = usernameResult.getParams();
						
						String username = usernameResult.getOutput();
						
						Map<String, Object> data = new HashMap<>();
						data.put(COMMAND, command);
						data.put(USERNAME, username);
						data.put(TIMESTAMP, LocalDateTime.ofInstant(timestamp.toInstant(), ZoneId.systemDefault()));
						
						notifyAll(data);
					}
					dateTime = timestamp;
				}
				
			}
			
			chrono.stop();
			
			Sleep.millis(delay - chrono.getDiff());
			
		} while (!isStopped());
	}
	
	public List<String> getExcludedCommands() {
		return excludedCommands;
	}

}
