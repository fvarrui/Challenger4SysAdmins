import java.io.IOException;

import fvarrui.sysadmin.challenger.model.command.Command;
import fvarrui.sysadmin.challenger.model.command.ExecutionResult;
import fvarrui.sysadmin.challenger.model.command.PSCommand;

public class Elevated {

    public static void main(String[] args) throws IOException {
//        enable();
    	disable();
    }

	private static void enable() {
		System.out.println("Habilitando monitorización de intérpretes de comandos en Windows...");
		Command enableMonitoring = new PSCommand("Start-Process -FilePath cmd.exe -ArgumentList \"/d\",\"/c\",\"$PWD\\config\\scripts\\windows\\config-windows-monitoring.cmd\" -Verb RunAs");
        enableMonitoring.execute();
        System.out.println("Monitorización habilitada");
	}
	
	private static void disable() {
		System.out.println("Deshabilitando monitorización de intérpretes de comandos en Windows...");
		Command disableMonitoring = new PSCommand("Start-Process -FilePath cmd.exe -ArgumentList \"/d\",\"/c\",\"$PWD\\config\\scripts\\windows\\remove-windows-monitoring.cmd\" -Verb RunAs");
        disableMonitoring.execute();
        System.out.println("Monitorización deshabilitada");
	}

}
