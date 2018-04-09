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
import fvarrui.sysadmin.challenger.monitoring.ShellMonitor;

public class PSPromptMonitor extends ShellMonitor {
	
	private static final long DELAY = 1000L;
	private static final String QUERY_EVENTS_CMD = "wevtutil query-events \"Application\" /q:\"*"
			+ "[System"
				+ "[TimeCreated"
				+	 "[@SystemTime>'${TIME}']"
				+ "]"
				+ "[Provider"
				+	 "[@Name='${NAME}']"
				+ "]"
			+ "]\"";
	
	private long delay;
	private Command command;
	
	public PSPromptMonitor(long delay) {
		super("PowerShell Prompt Monitor");
		this.delay = delay;
		this.command = new DOSCommand(QUERY_EVENTS_CMD);
		getExcludedCommands().add("");
	}
	
	public PSPromptMonitor() {
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
			data.put("NAME", "Challenger4SysAdmins");
			
			ExecutionResult result = command.execute(data);
			
			if (!result.getOutput().isEmpty()) {
				
				String xml = "<Events>" + result.getOutput() + "</Events>";
				
				Document doc = XMLUtils.stringToDocument(xml);
				NodeList nodes = doc.getElementsByTagName("Event");
				for (int i = 0; i < nodes.getLength(); i++) {
					Node node = nodes.item(i);
					
					NodeList dataNodes = XMLUtils.search(node, "EventData/Data");
					String command = dataNodes.item(0).getTextContent();
					String username = dataNodes.item(1).getTextContent();
					String pwd = dataNodes.item(2).getTextContent();
					String oldpwd = dataNodes.item(3).getTextContent();
					
					String xmlDateTime = XMLUtils.searchAttribute(node, "System/TimeCreated", "SystemTime");
					ZonedDateTime timestamp = DateTimeUtils.xmlInstantToZonedDateTime(xmlDateTime);
										
					if (!getExcludedCommands().contains(command)) {
						
						Map<String, Object> event = new HashMap<>();
						event.put(COMMAND, command);
						event.put(USERNAME, username);
						event.put(TIMESTAMP, LocalDateTime.ofInstant(timestamp.toInstant(), ZoneId.systemDefault()));
						event.put(PWD, pwd);
						event.put(OLDPWD, oldpwd);
						
						notifyAll(event);
						
					}
					dateTime = timestamp;
				}
				
			}
			
			Sleep.millis(delay - chrono.stop());
			
		} while (!isStopped());
		
	}
	
}
