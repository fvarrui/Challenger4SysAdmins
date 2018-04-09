package fvarrui.sysadmin.challenger.model.command;

import javax.xml.bind.annotation.XmlType;

/**
 * Clase Modelo que representa un comando bash
 * 
 * @author Fran Vargas
 * @version 1.0
 * 
 */
@XmlType
public class DOSCommand extends Command {

	private static final String CMD = "cmd";

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
		super(CMD, "/c", command);
	}

}
