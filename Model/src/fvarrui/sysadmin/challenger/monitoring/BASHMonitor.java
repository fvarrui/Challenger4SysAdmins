package fvarrui.sysadmin.challenger.monitoring;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fvarrui.sysadmin.challenger.command.BASHCommand;
import fvarrui.sysadmin.challenger.command.Command;
import fvarrui.sysadmin.challenger.command.DOSCommand;
import fvarrui.sysadmin.challenger.command.ExecutionResult;
import fvarrui.sysadmin.challenger.command.PSCommand;
import fvarrui.sysadmin.challenger.utils.DateTimeUtils;
import fvarrui.sysadmin.challenger.utils.XMLUtils;

public class BASHMonitor extends Monitor {
	
	public static final String COMMAND = "command";
	public static final String USERNAME = "username";
	public static final String TIMESTAMP = "timestamp";
	
	private static final String READ_SYSDIG_LOG = "Get-Content sysdig.log | ForEach-Object { $_ ; Start-Sleep -Seconds 5 }";
	
	private Pattern pattern = Pattern.compile("^\\s*\\d+ (\\d{1,2}:\\d{1,2}:\\d{1,2}) (\\w+)\\) (.*)$");
	private Command command;
	private List<String> excludedCommands;
	
	public BASHMonitor() {
		super("BashMonitor");
		this.command = new PSCommand(READ_SYSDIG_LOG); // TODO BASHCommand
		this.excludedCommands = new ArrayList<>();
	}
	
	@Override
	public void doWork() {
		try {
			InputStream input = command.longExecute();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			String line = null;
			
			while ((line = reader.readLine()) != null) {

				System.out.println("---" + line + "---");
				
				Matcher matcher = pattern.matcher(line);
				while (matcher.find()) {
					System.out.println(matcher.group(1));
					System.out.println(matcher.group(2));
					System.out.println(matcher.group(3));
				}
				
//				if (!excludedCommands.contains(command)) {
//					notify(command, timeCreated);
//				}
//				dateTime = timeCreated;
				
			}
			System.out.println("aquí no");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<String> getExcludedCommands() {
		return excludedCommands;
	}

}
