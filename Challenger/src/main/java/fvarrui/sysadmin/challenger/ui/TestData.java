package fvarrui.sysadmin.challenger.ui;

import fvarrui.sysadmin.challenger.model.Challenge;
import fvarrui.sysadmin.challenger.model.Goal;
import fvarrui.sysadmin.challenger.model.command.Command;
import fvarrui.sysadmin.challenger.model.command.PSCommand;
import fvarrui.sysadmin.challenger.model.test.CommandTest;

public class TestData {
	
	public static Challenge getChallenge() {
		
		Command existeDirectorio = new PSCommand("if (Get-ChildItem ${PATH} -ne null) {return 1; } else { return 0; }"); 
		
		CommandTest test1 = new CommandTest();
		test1.setCommand(existeDirectorio);
		test1.getData().put("PATH", System.getProperty("user.home") + "\\media");
		
		Goal objetivo1 = new Goal();
		objetivo1.setName("Crear el directorio 'media'");
		objetivo1.setDescription("# Crear directorio 'media' en tu directorio de usuario\n\n- Esto es una prueba\n\n- De MarkDown");
		objetivo1.setTest(test1);

		CommandTest test2 = new CommandTest();
		test2.setCommand(existeDirectorio);
		test2.getData().put("PATH", System.getProperty("user.home") + "\\media\\peliculas");
		
		Goal objetivo2 = new Goal();
		objetivo2.setName("Crear el directorio 'peliculas'");
		objetivo2.setDescription("# Crear directorio 'peliculas' dentro de 'media'\n\n- Esto es una prueba\n\n- De MarkDown");
		objetivo2.setTest(test2);
		
		Challenge reto = new Challenge();
		reto.setName("Gestión de archivos y directorios desde PowerShell");
		reto.setDescription("# El reto\n\nEl reto consiste en **crear una serie de archivos y directorios** desde el intérprete de comandos PowerShell.");
		reto.getGoals().addAll(objetivo1, objetivo2);
		
		return reto;
	}

}
