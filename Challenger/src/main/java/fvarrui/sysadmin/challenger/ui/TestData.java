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
		
		Goal objetivo1 = new Goal();
		objetivo1.setName("Crear el directorio 'media' en tu directorio de usuario.");
		objetivo1.setDescription("Crear el directorio 'media' en tu directorio de usuario.");
		objetivo1.setTest(test1);

		CommandTest test2 = new CommandTest();
		
		Goal objetivo2 = new Goal();
		objetivo2.setName("Crear el directorio 'peliculas' dentro de 'media'.");
		objetivo2.setDescription("Crear el directorio 'peliculas' dentro de 'media'.");
		objetivo2.setTest(test2);
		
		Challenge reto = new Challenge();
		reto.setName("Gestión de archivos y directorios desde PowerShell");
		reto.setDescription("El reto consiste en crear una serie de archivos y directorios desde el intérprete de comandos PowerShell");
		reto.getGoals().add(objetivo1);
		
		return reto;
	}

}
