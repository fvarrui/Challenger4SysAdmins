package fvarrui.sysadmin.challenger.monitoring;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fvarrui.sysadmin.challenger.command.Command;
import fvarrui.sysadmin.challenger.command.DOSCommand;
import fvarrui.sysadmin.challenger.command.ExecutionResult;
import fvarrui.sysadmin.challenger.utils.DateTimeUtils;
import fvarrui.sysadmin.challenger.utils.XMLUtils;

public class PSMonitor extends Monitor {
	
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
	}
	
	public PSMonitor() {
		this(DELAY);
	}

	@Override
	public void doWork() {
		LocalDateTime dateTime = LocalDateTime.now();
		do {
			
			ExecutionResult result = command.execute(dateTime.toString());
			
			if (!result.getOutput().isEmpty()) {
				
				String xml = "<Events>" + result.getOutput() + "</Events>";
				Document doc = XMLUtils.stringToDocument(xml);
				NodeList nodes = doc.getElementsByTagName("Event");
				for (int i = 0; i < nodes.getLength(); i++) {
					Node node = nodes.item(i);
					String command = XMLUtils.searchText(node, "EventData/Data[@Name='ScriptBlockText']");
					String xmlTimeCreated = XMLUtils.searchAttribute(node, "System/TimeCreated", "SystemTime");
					LocalDateTime timeCreated = DateTimeUtils.xmlInstantToLocalDateTime(xmlTimeCreated);
					if (!excludedCommands.contains(command)) {
						notify(command, timeCreated);
					}
					dateTime = timeCreated;
				}
				
			}
			
			delay();
			
		} while (!isStopped());
	}
	
	private void delay() {
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}
	
	public List<String> getExcludedCommands() {
		return excludedCommands;
	}

}
