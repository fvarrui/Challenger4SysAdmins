package fvarrui.sysadmin.challenger.ui;

import java.io.File;

import fvarrui.sysadmin.challenger.model.Challenge;
import fvarrui.sysadmin.challenger.model.Goal;
import fvarrui.sysadmin.challenger.model.command.Command;
import fvarrui.sysadmin.challenger.model.command.PSCommand;
import fvarrui.sysadmin.challenger.model.test.CommandTest;
import fvarrui.sysadmin.challenger.model.test.ShellTest;

public class TestData {
	
	public static Challenge getTest1() {
		
		Command existeDirectorio = new PSCommand("if (Test-Path ${PATH}) { exit 0; } else { exit 1; }"); 
		
		CommandTest test1 = new CommandTest("Comprobar si existe directorio media");
		test1.setCommand(existeDirectorio);
		test1.getData().put("PATH", System.getProperty("user.home") + "\\media");
		
		Goal objetivo1 = new Goal();
		objetivo1.setName("Crear el directorio 'media'");
		objetivo1.setDescription("## Crear directorio 'media' en tu directorio de usuario\n\n- Esto es una prueba\n- De MarkDown");
		objetivo1.setTest(test1);

		CommandTest test2 = new CommandTest("Comprobar si existe directorio media\\peliculas");
		test2.setCommand(existeDirectorio);
		test2.getData().put("PATH", System.getProperty("user.home") + "\\media\\peliculas");
		
		Goal objetivo2 = new Goal();
		objetivo2.setName("Crear el directorio 'peliculas'");
		objetivo2.setDescription("## Crear directorio 'peliculas' dentro de 'media'\n\n- Esto es una prueba\n- De MarkDown");
		objetivo2.setTest(test2);

		CommandTest test3 = new CommandTest("Comprobar si existe directorio media\\series");
		test3.setCommand(existeDirectorio);
		test3.getData().put("PATH", System.getProperty("user.home") + "\\media\\series");
		
		Goal objetivo3 = new Goal();
		objetivo3.setName("Crear el directorio 'series'");
		objetivo3.setDescription("## Crear directorio 'series' dentro de 'media'\n\n- Esto es una prueba\n- De MarkDown");
		objetivo3.setTest(test3);

		Challenge reto = new Challenge();
		reto.setName("Gestión de archivos y directorios desde PowerShell");
		reto.setDescription("# El reto\n\nEl reto consiste en **crear una serie de archivos y directorios** desde el intérprete de comandos PowerShell. :smile:");
		reto.getGoals().addAll(objetivo1, objetivo2, objetivo3);
		
		return reto;
	}
	
	public static Challenge getTest2() {
		
		Goal objetivo1 = new Goal();
		objetivo1.setName("Mostrar la fecha");
		objetivo1.setDescription("Mostrar la fecha en el símbolo del sistema.");
		objetivo1.setTest(new ShellTest("Ejecutar el comando 'date' en el Símbolo del sistema", "cmd", "date /t"));

		Goal objetivo2 = new Goal();
		objetivo2.setName("Mostrar archivos y directorios");
		objetivo2.setDescription("Listar los archivos y directorios del directorio actual");
		objetivo2.setTest(new ShellTest("Ejecutar el comando 'dir' en el Símbolo del sistema", "cmd", "dir"));
		
		Challenge reto = new Challenge();
		reto.setName("Primeros comandos en el Símbolo del sistema");
		reto.setDescription("# Mis primeros comandos\n\nEl reto consiste en ejecutar una serie de comandos desde el Símbolo del sistema. Para abrir "
				+ "el símbolos del sistema pulsa la combinación de teclas `WIN+R`, escribe 'cmd' y pulsa ENTER. :smile:");
		reto.getGoals().addAll(objetivo1, objetivo2);
		
		return reto;
	}
	
	public static void main(String[] args) throws Exception {
		getTest1().save(new File("test.challenge"));
	}

}
