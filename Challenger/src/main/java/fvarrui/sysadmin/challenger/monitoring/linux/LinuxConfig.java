package fvarrui.sysadmin.challenger.monitoring.linux;

import java.io.File;

import fvarrui.sysadmin.challenger.model.command.BASHScript;
import fvarrui.sysadmin.challenger.monitoring.Config;

public class LinuxConfig implements Config {
	
	private static final File LINUX_SCRIPT = new File("config/config-linux-monitoring.sh");
	
	public boolean test() {
		System.out.print("Comprobando monitorización de intérpretes de comandos en GNU/Linux ... ");
		boolean enabled = new BASHScript(LINUX_SCRIPT, "--test").execute().getExitValue() == 0;
		if (enabled) {
	        System.out.println("[Habilitada]");
		} else {
	        System.out.println("[Deshabilitada]");
		}
		return enabled;
	}
	
	public void enable() {
		System.out.print("Habilitando monitorización de intérpretes de comandos en GNU/Linux ... ");
		new BASHScript(true, LINUX_SCRIPT, "--enable").execute();
		System.out.println("[Completado]");
	}
	
	public void disable() {
		System.out.print("Deshabilitando monitorización de intérpretes de comandos en GNU/Linux ... ");
		new BASHScript(true, LINUX_SCRIPT, "--disable").execute();
		System.out.println("[Completado]");
	}

}
