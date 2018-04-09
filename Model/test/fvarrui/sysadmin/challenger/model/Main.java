package fvarrui.sysadmin.challenger.model;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Pattern;

import org.apache.commons.exec.LogOutputStream;

import fvarrui.sysadmin.challenger.model.Challenge;
import fvarrui.sysadmin.challenger.model.Goal;
import fvarrui.sysadmin.challenger.model.command.BASHCommand;
import fvarrui.sysadmin.challenger.model.command.Command;
import fvarrui.sysadmin.challenger.model.command.DOSCommand;
import fvarrui.sysadmin.challenger.model.command.ExecutionResult;
import fvarrui.sysadmin.challenger.model.command.PSCommand;
import fvarrui.sysadmin.challenger.model.test.CommandTest;
import fvarrui.sysadmin.challenger.model.test.NotTest;
import fvarrui.sysadmin.challenger.model.test.Test;

public class Main {

	private static void test() throws Exception {
		// String path = new File("src/fvarrui/sysadmin/challenge").getAbsolutePath();

		// Command c = new Command("cmd /c if exist %s ( exit 0 ) else ( exit 1 )");
		// Command c = new DOSCommand("if exist %s ( exit 0 ) else ( exit 1 )");
		// Command c = new DOSCommand("echo hola");
		// Command c = new PSCommand("Get-Process -Id 12345");

		// Command c = new BASHCommand("ps -ef");

		// Command c = new
		// PSCommand("[System.Diagnostics.FileVersionInfo]::GetVersionInfo(\\\"$env:windir\\system32\\cmd.exe\\\").FileVersion");

		// Command c = new PSCommand("($PSVersionTable.PSVersion).ToString()");

		// Command c = new PSCommand("Get-WinEvent -LogName
		// \\\"Microsoft-Windows-PowerShell/Operational\\\" -FilterXPath
		// \\\"*[System[TimeCreated[@SystemTime>='2018-04-03T14:30:00']][EventID=4104]]\\\"");

		// Command c = new DOSCommand("wevtutil query-events
		// \"Microsoft-Windows-PowerShell/Operational\"
		// /q:\"*[System[TimeCreated[@SystemTime>='${TIMESTAMP}']][EventID=4104]]\"") ;

		// Command c = new PSCommand("Get-Date -UFormat \"%Y\"");
		//
		// Map<String, Object> vars = new HashMap<>();
		// vars.put("TIMESTAMP", LocalDateTime.now().minusHours(2L).toString());

		// Command c = new BASHCommand("tail -n 0 -f /var/log/syslog");
		//
		// ExecutionResult result = c.execute(false);
		// System.out.println(result);
		//
		// BufferedReader reader = new BufferedReader(new
		// InputStreamReader(result.getOutputStream()));
		// String line = null;
		// while ((line = reader.readLine()) != null) {
		// System.out.println(line);
		// }

		// ExecutionResult result = c.execute(false);
		// System.out.println(result);

		// Process p = Runtime.getRuntime().exec("ping 8.8.8.8");
		// Process p = Runtime.getRuntime().exec("/usr/bin/sysdig -c spy_users
		// --unbuffered");
		// Process p = Runtime.getRuntime().exec("/usr/bin/tail -f /var/log/syslog");
		// Process p = Runtime.getRuntime().exec("bash -c \"while true ; do date ; sleep
		// 1s ; done\"");
		// Process p = Runtime.getRuntime().exec("cmd /c \"ping 8.8.8.8\"");
		// new Thread(new StreamGobbler(p.getInputStream(), s ->
		// System.out.println(s))).start();
		// new Thread(new StreamGobbler(p.getErrorStream(),
		// System.err::println)).start();
		// System.out.println("esperando a que termine");
		// p.waitFor();
		// System.out.println("terminó");
		// err.cancel(true);

		// new Thread(() -> {
		// BufferedReader r = new BufferedReader(new
		// InputStreamReader(p.getInputStream()));
		// String line = null;
		// int i = 1;
		// try {
		// while ((line = r.readLine()) != null) {
		// System.out.println("STDOUT " + i++ + " : " + line);
		// }
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }).start();
		//
		// new Thread(() -> {
		// BufferedReader r = new BufferedReader(new
		// InputStreamReader(p.getErrorStream()));
		// String line = null;
		// int i = 1;
		// try {
		// while ((line = r.readLine()) != null) {
		// System.out.println("STDERR " + i++ + " : " + line);
		// }
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }).start();
		//
		// p.waitFor();

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

		Command serviceRunning = new PSCommand("if ((Get-Service ${SERVICE}).Status -eq 'Running') { exit 0 } else { exit 1 }");

		CommandTest test1 = new CommandTest("¿Windows Update iniciado?", serviceRunning);
		test1.getData().put("SERVICE", "wuauserv");

		CommandTest test2 = new CommandTest("¿Servicio Themes iniciado?", serviceRunning);
		test2.getData().put("SERVICE", "themes");

		Goal goal1 = new Goal("Iniciar Windows Update", "El servicio Windows Update (wuauserv) ha sido iniciado");
		goal1.setTest(test1);

		Goal goal2 = new Goal("Detener el servicio Temas", "El servicio Temas (Themes) ha sido detenido");
		goal2.setTest(new NotTest(test2));

		Challenge challenge = new Challenge("Servicios");
		challenge.setDescription("Gestión de servicios");
		challenge.getGoals().addAll(goal1, goal2);

		challenge.save(new File("challenges/sample.challenge"));

//		Challenge challenge = Challenge.load(new File("challenges/sample.challenge"));
//		challenge.start();
	}

	public static void main(String[] args) throws Exception {
		test();
	}
}
