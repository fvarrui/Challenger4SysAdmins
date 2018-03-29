package fvarrui.sysadmin;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.regex.Pattern;

import fvarrui.sysadmin.challenger.Challenge;
import fvarrui.sysadmin.challenger.Goal;
import fvarrui.sysadmin.challenger.command.BASHCommand;
import fvarrui.sysadmin.challenger.command.Command;
import fvarrui.sysadmin.challenger.command.DOSCommand;
import fvarrui.sysadmin.challenger.command.ExecutionResult;
import fvarrui.sysadmin.challenger.command.PSCommand;
import fvarrui.sysadmin.challenger.command.ShellCommand;
import fvarrui.sysadmin.challenger.monitoring.BASHMonitor;
import fvarrui.sysadmin.challenger.monitoring.Monitor;
import fvarrui.sysadmin.challenger.monitoring.PSMonitor;
import fvarrui.sysadmin.challenger.test.CommandTest;
import fvarrui.sysadmin.challenger.test.NotTest;
import fvarrui.sysadmin.challenger.test.Test;

public class Main {
	
	

	private static void test() throws Exception {
		// String path = new File("src/fvarrui/sysadmin/challenge").getAbsolutePath();

		// Command c = new Command("cmd /c if exist %s ( exit 0 ) else ( exit 1 )");
		// Command c = new DOSCommand("if exist %s ( exit 0 ) else ( exit 1 )");
		// Command c = new DOSCommand("echo hola");
		// Command c = new PSCommand("Get-Process -Id 12345");
		
		
//		Command c = new ShellCommand("bash -c \"%s\"", "psx -e --forest");

//		Command c = new PSCommand("[System.Diagnostics.FileVersionInfo]::GetVersionInfo(\\\"$env:windir\\system32\\cmd.exe\\\").FileVersion");

//		Command c = new PSCommand("($PSVersionTable.PSVersion).ToString()");

//		Command c = new PSCommand("Get-WinEvent -LogName \\\"Microsoft-Windows-PowerShell/Operational\\\" -FilterXPath \\\"*[System[TimeCreated[@SystemTime>='2018-03-15T14:30:00']][EventID=4104]]\\\"");

//		Command c = new DOSCommand("wevtutil query-events \"Microsoft-Windows-PowerShell/Operational\" /q:\"*[System[TimeCreated[@SystemTime>='%s']][EventID=4104]]\"");
		
//		Command c = new PSCommand("Get-Date");
//		System.out.println(c.execute("2018-03-16T14:30:00"));
		
		Command c = new BASHCommand("while true ; do date ; sleep 1s ; done");
		InputStream s = c.longExecute();
		BufferedReader r = new BufferedReader(new InputStreamReader(s));
		String line = null;
		int i = 1;
		while ((line = r.readLine()) != null) {
			System.out.println(i++ + " : " + line);
		}
		
//		Monitor l = new BASHMonitor();
		
//		Monitor l = new PSMonitor();
//		l.addListener((data) -> {
//			System.out.println(String.format("%s (%s): %s", 
//					data.get(PSMonitor.TIMESTAMP), 
//					data.get(PSMonitor.USERNAME), 
//					data.get(PSMonitor.COMMAND))
//				);
//		});
//		l.start();
		
		
		
//		Thread.sleep(15000L);
//		l.requestStop();

		
		// Test test1 = new AndTest(
		// "Crear arbol /hola/donpepito",
		// new CommandTest("existe-directorio-hola", c, "/hola"),
		// new CommandTest("existe-directorio-donpepito", c, "/hola/donpepito")
		// );
		
		// 
		// AndTest main = new AndTest("principal", test1, test2);
		//

		// Command c = new PSCommand("Get-ChildItem %s");
//		Command newDirectory = new PSCommand("Get-ChildItem D:\\pepe\\ejemplo\\ejemplo2");
		
//		Command serviceRunning = new PSCommand("if ((Get-Service %s).Status -eq 'Running') { exit 0 } else { exit 1 }");
//
//		Test test1 = new CommandTest("¿Windows Update iniciado?", serviceRunning, "wuauserv");
//		Goal goal1 = new Goal("Iniciar Windows Update", "El servicio Windows Update (wuauserv) ha sido iniciado");
//		goal1.setTest(test1);
//
//		Test test2 = new CommandTest("¿Servicio Themes iniciado?", serviceRunning, "themes");
//		Goal goal2 = new Goal("Detener el servicio Temas", "El servicio Temas (Themes) ha sido detenido");
//		goal2.setTest(new NotTest(test2));
//
//		Challenge challenge = new Challenge("Servicios");
//		challenge.setDescription("Gestión de servicios");
//		challenge.getGoals().add(goal1);
//		challenge.getGoals().add(goal2);
//		challenge.start();

//		challenge.save(new File("challenges/sample.challenge"));

		// Challenge challenge = Challenge.load(new
		// File("challenges/sample.challenge"));
		// challenge.start();
	}

	public static void main(String[] args) throws Exception {
		test();
	}
}
