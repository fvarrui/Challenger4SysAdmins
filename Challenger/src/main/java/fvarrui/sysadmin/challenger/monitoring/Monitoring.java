package fvarrui.sysadmin.challenger.monitoring;

import java.io.File;

import org.apache.commons.lang.SystemUtils;

import fvarrui.sysadmin.challenger.model.command.Command;
import fvarrui.sysadmin.challenger.model.command.PSCommand;
import fvarrui.sysadmin.challenger.model.command.PSScript;

public class Monitoring {
	
	private static boolean testWindows() {
		Command testMonitoring = new PSScript(new File("config\\scripts\\windows\\test-windows-monitoring.ps1"));
        return testMonitoring.execute().getExitValue() == 0;
	}

	private static void enableWindows() {
//		File script = new File("config\\scripts\\windows\\config-windows-monitoring.ps1");
//		Command enableMonitoring = new PSCommand("Start-Process -FilePath powershell -Verb RunAs -ArgumentList \"-File '" + script.getAbsolutePath() + "'\"");
		Command enableMonitoring = new PSCommand("Start-Process -FilePath cmd.exe -ArgumentList \"/d\",\"/c\",\"$PWD\\config\\scripts\\windows\\config-windows-monitoring.cmd\" -Verb RunAs");
        enableMonitoring.execute();
	}
	
	private static void disableWindows() {
		Command disableMonitoring = new PSCommand("Start-Process -FilePath cmd.exe -ArgumentList \"/d\",\"/c\",\"$PWD\\config\\scripts\\windows\\remove-windows-monitoring.cmd\" -Verb RunAs");
        disableMonitoring.execute();
	}
	
	public static boolean test() {
		System.out.println("Comprobando monitorización de intérpretes de comandos...");
		if (SystemUtils.IS_OS_WINDOWS && testWindows()) {
	        System.out.println("Monitorización habilitada en Windows");
	        return true;
		} else if (SystemUtils.IS_OS_LINUX) {
			// TODO habilitar monitorizacion en linux
			System.err.println("Funcionalidad aún no disponible");
			return false;
		}
		return false;
	}
	
	public static void enable() {
		System.out.println("Habilitando monitorización de intérpretes de comandos...");
		if (SystemUtils.IS_OS_WINDOWS) {
			enableWindows();
	        System.out.println("Monitorización habilitada");
		} else if (SystemUtils.IS_OS_LINUX) {
			// TODO habilitar monitorizacion en linux
			System.err.println("Funcionalidad aún no disponible");
		}
	}

	public static void disable() {
		System.out.println("Deshabilitando monitorización de intérpretes de comandos...");
		if (SystemUtils.IS_OS_WINDOWS) {
			disableWindows();
	        System.out.println("Monitorización deshabilitada");
		} else if (SystemUtils.IS_OS_LINUX) {
			// TODO deshabilitar monitorizacion en linux
			System.err.println("Funcionalidad aún no disponible");
		}
	}

}
