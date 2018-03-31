package fvarrui.sysadmin.challenger.monitoring;

import java.util.ArrayList;
import java.util.List;

public abstract class ShellMonitor extends Monitor {
	
	public static final String COMMAND = "command";
	public static final String USERNAME = "username";
	public static final String TIMESTAMP = "timestamp";
	
	private List<String> excludedCommands;
	
	public ShellMonitor(String name) {
		super(name);
		this.excludedCommands = new ArrayList<>();
	}
		
	public List<String> getExcludedCommands() {
		return excludedCommands;
	}

}
