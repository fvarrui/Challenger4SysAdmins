package fvarrui.sysadmin;


import fvarrui.sysadmin.challenger.Challenge;
import fvarrui.sysadmin.challenger.Goal;
import fvarrui.sysadmin.challenger.command.Command;
import fvarrui.sysadmin.challenger.command.PSCommand;
import fvarrui.sysadmin.challenger.test.CommandTest;
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
		serviceRunning.execute("wuauserv");
		System.out.println(serviceRunning.getLastExecutedCommand());
		System.out.println(serviceRunning.getReturnValue());

		Test test = new CommandTest("¿Windows Update iniciado?", serviceRunning, "wuauserv");
		
		Goal goal1 = new Goal("Iniciar el servicio Windows Update", "El servicio wuauserv está iniciado");
		goal1.setTest(test);

		Challenge challenge = new Challenge("Servicios");
		challenge.setDescription("Listar Servicios");
		challenge.getGoals().add(goal1);
		challenge.start();

		// challenge.save(new File("challenges/sample.challenge"));

		// Challenge challenge = Challenge.load(new
		// File("challenges/sample.challenge"));
		// challenge.start();
	}

	public static void main(String[] args) throws Exception {
		test();
	}
}
