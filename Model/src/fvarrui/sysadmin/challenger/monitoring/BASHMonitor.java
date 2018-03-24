package fvarrui.sysadmin.challenger.monitoring;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fvarrui.sysadmin.challenger.command.BASHCommand;
import fvarrui.sysadmin.challenger.command.Command;

public class BASHMonitor extends Monitor {
	
	public static final String COMMAND = "command";
	public static final String USERNAME = "username";
	public static final String TIMESTAMP = "timestamp";
	
//	private static final String READ_SYSDIG_LOG = "Get-Content sysdig.log | ForEach-Object { $_ ; Start-Sleep -Seconds 5 }";
	private static final String SYSDIG = "sysdig -c spy_users";
	
	private Pattern pattern = Pattern.compile("^\\s*\\d+ (\\d{1,2}:\\d{1,2}:\\d{1,2}) (\\w+)\\) (.*)$");
	private Command command;
	private List<String> excludedCommands;
	
	public BASHMonitor() {
		super("BashMonitor");
//		this.command = new PSCommand(READ_SYSDIG_LOG); // para hacer pruebas desde Windows
		this.command = new BASHCommand(SYSDIG);
		this.excludedCommands = new ArrayList<>();
	}
	
	@Override
	public void doWork() {
		try {
			
			System.out.println("ejecutando comando: " + command.getCommand());
			InputStream input = command.longExecute();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			
			String line = null;
			
			System.out.println("iniciando bucle");
			while ((line = reader.readLine()) != null) {

				System.out.println("linea: " + line);

				Matcher matcher = pattern.matcher(line);
				if (matcher.find()) {
					String time = matcher.group(1);
					String username = matcher.group(2);
					String command = matcher.group(3);
					
					if (!excludedCommands.contains(command)) {
					
						LocalDateTime timestamp = LocalDateTime.of(LocalDate.now(), LocalTime.parse(time));
						
						Map<String, Object> data = new HashMap<>();
						data.put(COMMAND, command);
						data.put(USERNAME, username);
						data.put(TIMESTAMP, timestamp);
						notifyAll(data);
						
					}
					
				}
				
			}
			System.out.println("fin del bucle");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<String> getExcludedCommands() {
		return excludedCommands;
	}

}
