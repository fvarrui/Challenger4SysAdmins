package fvarrui.sysadmin.challenger.monitoring.linux;

import java.io.File;

import fvarrui.sysadmin.challenger.model.command.BASHScript;
import fvarrui.sysadmin.challenger.monitoring.Config;

public class LinuxConfig implements Config {
	
	private static final File LINUX_SCRIPT = new File("config/config-linux-monitoring.sh");
	
	public boolean test() {
		return new BASHScript(LINUX_SCRIPT, "--test").execute().getExitValue() == 0;
	}
	
	public boolean enable() {
		return new BASHScript(true, LINUX_SCRIPT, "--enable").execute().getExitValue() == 0;
	}
	
	public boolean disable() {
		return new BASHScript(true, LINUX_SCRIPT, "--disable").execute().getExitValue() == 0;
	}

}
