package fvarrui.sysadmin.challenger.command;

import javax.xml.bind.annotation.XmlType;

/**
 * Clase Modelo que representa un comando bash
 * 
 * @author Fran Vargas
 * @version 1.0
 * 
 */
@XmlType
public class DOSCommand extends ShellCommand {

	private static final String CMD = "cmd /c %s";

	/**
	 * Constructor por defecto
	 */
	public DOSCommand() {
		this(null);
	}

	/**
	 * 
	 * @param command comando
	 */
	public DOSCommand(String command) {
		super(CMD, command);
	}

}
