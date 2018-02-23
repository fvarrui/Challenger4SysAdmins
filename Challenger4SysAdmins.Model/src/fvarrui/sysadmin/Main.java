package fvarrui.sysadmin;


import java.io.File;

import fvarrui.sysadmin.challenger.Challenge;
import fvarrui.sysadmin.challenger.Goal;
import fvarrui.sysadmin.challenger.command.Command;
import fvarrui.sysadmin.challenger.command.PSCommand;
import fvarrui.sysadmin.challenger.test.CommandTest;
import fvarrui.sysadmin.challenger.test.NotTest;
import fvarrui.sysadmin.challenger.test.Test;

public class Main {

	private static void test() throws Exception {
		// String path = new File("src/fvarrui/sysadmin/challenge").getAbsolutePath();

		// Command c = new Command("cmd /c if exist %s ( exit 0 ) else ( exit 1 )");
		// Command c = new DOSCommand("if exist %s ( exit 0 ) else ( exit 1 )");
		// Command c = new DOSCommand("dir %s");
		// Command c = new DOSCommand("echo hola");
		// Command c = new PSCommand("Get-Process -Id 12345");
		// c.execute("c:\\windows");
		// System.out.println(c.getOutput());
		// System.err.println(c.getError());
		// System.out.println(c.getReturnValue());

		// System.out.println("returnValue : " + c.getReturnValue());
		// System.out.println("output : " + c.getOutput());
		// System.out.println("error : " + c.getError());

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
		
		Command serviceRunning = new PSCommand("if ((Get-Service %s).Status -eq 'Running') { exit 0 } else { exit 1 }");
//		serviceRunning.lastExecutionTimeProperty().addListener((o, ov, nv) -> {
//			System.out.println("Se ha ejecutado el comando: " + serviceRunning.getLastExecutedCommand());
//		});

		Test test1 = new CommandTest("¿Windows Update iniciado?", serviceRunning, "wuauserv");
		Goal goal1 = new Goal("Iniciar Windows Update", "El servicio Windows Update (wuauserv) ha sido iniciado");
		goal1.setTest(test1);

		Test test2 = new CommandTest("¿Servicio Themes iniciado?", serviceRunning, "themes");
		Goal goal2 = new Goal("Detener el servicio Temas", "El servicio Temas (Themes) ha sido detenido");
		goal2.setTest(new NotTest(test2));

		Challenge challenge = new Challenge("Servicios");
		challenge.setDescription("Gestión de servicios");
		challenge.getGoals().add(goal1);
		challenge.getGoals().add(goal2);
//		challenge.start();

		challenge.save(new File("challenges/sample.challenge"));

		// Challenge challenge = Challenge.load(new
		// File("challenges/sample.challenge"));
		// challenge.start();
	}

	public static void main(String[] args) throws Exception {
		test();
	}
}
