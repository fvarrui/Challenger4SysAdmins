package fvarrui.sysadmin.challenger.monitoring.linux;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fvarrui.sysadmin.challenger.common.utils.StreamGobbler;
import fvarrui.sysadmin.challenger.model.command.BASHCommand;
import fvarrui.sysadmin.challenger.model.command.Command;
import fvarrui.sysadmin.challenger.model.command.ExecutionResult;
import fvarrui.sysadmin.challenger.monitoring.ShellMonitor;

public class LinuxShellMonitor extends ShellMonitor {
	
	private static final String TAIL_SYSLOG = "tail -n 0 -f /var/log/syslog";
	
	// ejemplo: "Apr  7 01:23:45 ssv-pc Challenger4SysAdmins: bash:username:pwd:oldpwd:tail -n 0 -f /var/log/syslog"
	private Pattern pattern = Pattern.compile("^(\\w+)\\s+(\\d+) (\\d+:\\d+:\\d+) (.+) Challenger4SysAdmins: ([^:]+):([^:]+):([^:]*):([^:]*)?:(.*)$");
	
	private Command command;
	
	public LinuxShellMonitor() {
		super("Linux Shell Monitor");
		this.command = new BASHCommand(TAIL_SYSLOG);
	}
	
	@Override
	public void doWork() {
			
		ExecutionResult result = command.execute(false);
		
		if (result.getExitValue() != 0) {
			System.err.println(result.getError());
			return;
		}
		
		StreamGobbler output = new StreamGobbler(result.getOutputStream(), this::parseLine); 
		StreamGobbler error = new StreamGobbler(result.getErrorStream(), System.err::println); 
		
		output.start();
		error.start();
		
		while (!isStopped() && output.isAlive() && error.isAlive()) {
			// no hace nada
		}
		
		output.requestStop();
		error.requestStop();

	}

	private void parseLine(String line) {
		
		Matcher matcher = pattern.matcher(line);
		if (matcher.find()) {
			String month = matcher.group(1);
			String day = matcher.group(2);
			String time = matcher.group(3);
			String hostname = matcher.group(4);
			String shell = matcher.group(5);
			String username = matcher.group(6);
			String pwd = matcher.group(7);
			String oldpwd = matcher.group(8);
			String command = matcher.group(9);
			
			if (!getExcludedCommands().contains(command)) {
			
				LocalDateTime timestamp = LocalDateTime.of(LocalDate.now(), LocalTime.parse(time));
				
				Map<String, Object> data = new HashMap<>();
				data.put(SHELL, shell);
				data.put(COMMAND, command);
				data.put(USERNAME, username);
				data.put(TIMESTAMP, timestamp);
				data.put(PWD, pwd);
				data.put(OLDPWD, oldpwd);
				data.put("month", month);
				data.put("day", day);
				data.put("hostname", hostname);
				
				notifyAll(data);
				
			}
			
		}
	}

}
