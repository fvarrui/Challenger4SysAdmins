package fvarrui.sysadmin.challenger.monitoring;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fvarrui.sysadmin.challenger.command.Command;
import fvarrui.sysadmin.challenger.command.ExecutionResult;
import fvarrui.sysadmin.challenger.utils.StreamGobbler;

public class BASHPromptMonitor extends ShellMonitor {
	
	private static final String SYSDIG = "/usr/bin/tail -n 0 -f /var/log/syslog";
	
	private Pattern pattern = Pattern.compile("^\\s*\\d+ (\\d{1,2}:\\d{1,2}:\\d{1,2}) (\\w+)\\) (.*)$");
	private Command command;
	
	public BASHPromptMonitor() {
		super("Bash Prompt Monitor");
		this.command = new Command(SYSDIG);
	}
	
	@Override
	public void doWork() {
			
		System.out.println("ejecutando comando: " + command.getExecutable());
		ExecutionResult result = command.execute(false);
		
		if (result.getExitValue() != 0) {
			System.err.println(result.getError());
			return;
		}
		
		Thread output = new Thread(new StreamGobbler(result.getOutputStream(), this::parseLine));
		Thread error = new Thread(new StreamGobbler(result.getErrorStream(), System.err::println));
		
		output.start();
		error.start();
		
		while (!isStopped()) {}
		
		output.interrupt();
		error.interrupt();

	}

	private void parseLine(String line) {
		System.out.println("linea: " + line);
		
		Matcher matcher = pattern.matcher(line);
		if (matcher.find()) {
			String time = matcher.group(1);
			String username = matcher.group(2);
			String command = matcher.group(3);
			
			if (!getExcludedCommands().contains(command)) {
			
				LocalDateTime timestamp = LocalDateTime.of(LocalDate.now(), LocalTime.parse(time));
				
				Map<String, Object> data = new HashMap<>();
				data.put(COMMAND, command);
				data.put(USERNAME, username);
				data.put(TIMESTAMP, timestamp);
				notifyAll(data);
				
			}
			
		}
	}

}
