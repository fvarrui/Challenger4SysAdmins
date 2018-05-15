package fvarrui.sysadmin.challenger.monitoring;

import java.time.LocalDateTime;

import org.apache.commons.lang.SystemUtils;

import fvarrui.sysadmin.challenger.model.test.ExecutedCommand;
import fvarrui.sysadmin.challenger.monitoring.linux.LinuxConfig;
import fvarrui.sysadmin.challenger.monitoring.linux.LinuxShellMonitor;
import fvarrui.sysadmin.challenger.monitoring.mac.MacConfig;
import fvarrui.sysadmin.challenger.monitoring.windows.WindowsConfig;
import fvarrui.sysadmin.challenger.monitoring.windows.WindowsShellMonitor;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

public class Monitoring {
	
	private static ListProperty<ExecutedCommand> COMMANDS = new SimpleListProperty<>(FXCollections.observableArrayList());
	private static final Config CONFIG = newConfig();
	private static ShellMonitor MONITOR;
	
	private static Config newConfig() {
		if (SystemUtils.IS_OS_WINDOWS) return new WindowsConfig();
		if (SystemUtils.IS_OS_LINUX) return new LinuxConfig();
		if (SystemUtils.IS_OS_MAC_OSX) return new MacConfig();
		return null;
	}
	
	private static ShellMonitor newMonitor() {
		ShellMonitor shellMonitor = null;
		if (SystemUtils.IS_OS_WINDOWS) shellMonitor = new WindowsShellMonitor();
		if (SystemUtils.IS_OS_LINUX) shellMonitor = new LinuxShellMonitor();
		if (SystemUtils.IS_OS_MAC_OSX) shellMonitor = null; // TODO comprobar si el monitor de Linux vale en MacOSX		
		return shellMonitor;
	}
	
	public static boolean test() {
		System.out.print("Comprobando estado de la monitorización de intérpretes de comandos ... ");		
		boolean enabled = CONFIG.test();
		if (enabled) {
	        System.out.println("[Habilitada]");
		} else {
	        System.out.println("[Deshabilitada]");
		}
		return enabled;

	}
	
	public static boolean enable() {
		System.out.print("Habilitando monitorización de intérpretes de comandos ... ");		
		boolean success = CONFIG.enable();
		if (success)
			System.out.println("[Completado]");
		else
			System.out.println("[Error]");
		return success;
	}

	public static boolean disable() {
		System.out.print("Deshabilitando monitorización de intérpretes de comandos ... ");		
		boolean success = CONFIG.disable();
		if (success)
			System.out.println("[Completado]");
		else
			System.out.println("[Error]");
		return success;
	}
	
	public static void start() {
		stop();
		MONITOR = newMonitor();
		MONITOR.addListener((monitor, data) -> {
			ExecutedCommand cmd = new ExecutedCommand();
			cmd.setShell((String) data.get(ShellMonitor.SHELL));
			cmd.setCommand((String) data.get(ShellMonitor.COMMAND));
			cmd.setUsername((String) data.get(ShellMonitor.USERNAME));
			cmd.setTimestamp((LocalDateTime) data.get(ShellMonitor.TIMESTAMP));
			cmd.setPwd((String) data.get(ShellMonitor.PWD));
			cmd.setOldPwd((String) data.get(ShellMonitor.OLDPWD));
			COMMANDS.add(cmd);
		});
		MONITOR.start();
	}
	
	public static void stop() {
		if (MONITOR != null && MONITOR.isAlive()) {
			MONITOR.requestStop();
		}
	}

	public static ListProperty<ExecutedCommand> getExecutedCommands() {
		return COMMANDS;
	}

}
