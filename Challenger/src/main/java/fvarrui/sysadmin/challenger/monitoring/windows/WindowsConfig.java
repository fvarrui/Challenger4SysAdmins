package fvarrui.sysadmin.challenger.monitoring.windows;

import java.io.File;

import fvarrui.sysadmin.challenger.model.command.PSScript;
import fvarrui.sysadmin.challenger.monitoring.Config;

public class WindowsConfig implements Config {
	
	private static final File WINDOWS_SCRIPT = new File("config\\config-windows-monitoring.ps1");
	
	public boolean test() {
		return new PSScript(WINDOWS_SCRIPT, "-Test").execute().getExitValue() == 0;
	}
	
	public boolean enable() {
		return new PSScript(true, WINDOWS_SCRIPT, "-Enable").execute().getExitValue() == 0;
	}
	
	public boolean disable() {
		return new PSScript(true, WINDOWS_SCRIPT, "-Disable").execute().getExitValue() == 0;
	}

}
