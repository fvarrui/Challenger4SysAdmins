package fvarrui.sysadmin.challenger.monitoring.windows;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fvarrui.sysadmin.challenger.common.utils.Chronometer;
import fvarrui.sysadmin.challenger.common.utils.DateTimeUtils;
import fvarrui.sysadmin.challenger.common.utils.Sleep;
import fvarrui.sysadmin.challenger.common.utils.XMLUtils;
import fvarrui.sysadmin.challenger.model.command.Command;
import fvarrui.sysadmin.challenger.model.command.DOSCommand;
import fvarrui.sysadmin.challenger.model.command.ExecutionResult;
import fvarrui.sysadmin.challenger.model.command.PSCommand;
import fvarrui.sysadmin.challenger.monitoring.ShellMonitor;

public class PSEventMonitor extends ShellMonitor {
	
	private static final long DELAY = 1000L;
	private static final String QUERY_EVENTS_CMD = "wevtutil query-events \"Microsoft-Windows-PowerShell/Operational\" /q:\"*[System[TimeCreated[@SystemTime>'${TIME}']][EventID=4104]]\"";
	
	private long delay;
	private Command command;
	
	private Command resolveUsername = new PSCommand("(Get-LocalUser | Where SID -eq '${SID}').Name");
	private String lastResolveUsernameCommand;
	
	public PSEventMonitor(long delay) {
		super("PowerShell Event Monitor");
		this.delay = delay;
		this.command = new DOSCommand(QUERY_EVENTS_CMD);
		this.getExcludedCommands().add("prompt");
		this.getExcludedCommands().add("{ Set-StrictMode -Version 1; $_.OriginInfo }");
		this.getExcludedCommands().add("{ Set-StrictMode -Version 1; $_.ErrorCategory_Message }"); 
		this.getExcludedCommands().add("{ Set-StrictMode -Version 1; $_.PSMessageDetails }");
		this.getExcludedCommands().add("{ Set-StrictMode -Version 1; $this.DisplayHint }");
		this.getExcludedCommands().add("[Microsoft.Windows.PowerShell.Gui.Internal.HostTextWriter]::RegisterHost($host.ui)");
		this.getExcludedCommands().add("filter more { $_ }");
		this.getExcludedCommands().add("\n" + 
			"function psEdit([Parameter(Mandatory=$true)]$filenames)\n" + 
			"{\n" + 
			"    foreach ($filename in $filenames)\n" + 
			"    {\n" + 
			"        dir $filename | where {!$_.PSIsContainer} | %{\n" + 
			"            $psISE.CurrentPowerShellTab.Files.Add($_.FullName) > $null\n" + 
			"        }\n" + 
			"    }\n" + 
			"}");
		this.getExcludedCommands().add("$OutputEncoding = [System.Console]::OutputEncoding");
		this.getExcludedCommands().add("ipmo ISE");
		this.getExcludedCommands().add("{ Set-StrictMode -Version 1; $this.Exception.InnerException.PSMessageDetails }");
		this.getExcludedCommands().add("$global:?");
	}
	
	public PSEventMonitor() {
		this(DELAY);
	}

	/**
	 * Implementa el trabajo realizado por el monitorizador
	 */
	@Override
	public void doWork() {
		ZonedDateTime dateTime = ZonedDateTime.now(ZoneOffset.UTC);

		Chronometer chrono = new Chronometer();
		
		do {

			chrono.init();
			
			Map<String, Object> data = new HashMap<>();
			data.put("TIME", dateTime.toString());
			
			ExecutionResult result = command.execute(data);
			
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
										
					if (!getExcludedCommands().contains(command) && !command.equals(lastResolveUsernameCommand)) {
						
						String username = resolveUsername(userId);
						
						Map<String, Object> event = new HashMap<>();
						event.put(COMMAND, command);
						event.put(USERNAME, username);
						event.put(TIMESTAMP, LocalDateTime.ofInstant(timestamp.toInstant(), ZoneId.systemDefault()));
						
						notifyAll(event);
						
					}
					dateTime = timestamp;
				}
				
			}
			
			Sleep.millis(delay - chrono.stop());
			
		} while (!isStopped());
	}
	
	/**
	 * Traduce el ID de usuario (SID) en un nombre (username) 
	 * @param sid Identificador del usuario
	 * @return Nombre del usuario 
	 */
	private String resolveUsername(String sid) {
		Map<String, Object> data = new HashMap<>();
		data.put("SID", sid);
		ExecutionResult usernameResult = resolveUsername.execute(data);
		lastResolveUsernameCommand = usernameResult.getParams();
		return usernameResult.getOutput();		
	}

}
