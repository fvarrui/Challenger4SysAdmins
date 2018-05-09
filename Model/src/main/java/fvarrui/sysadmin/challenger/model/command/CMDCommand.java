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
public class CMDCommand extends Command {

	private static final String CMD = "cmd";

	/**
	 * Constructor por defecto
	 */
	public CMDCommand() {
		this(null);
	}

	/**
	 * 
	 * @param command comando
	 */
	public CMDCommand(String command) {
		super(CMD, "/d", "/c", command);
	}

}
