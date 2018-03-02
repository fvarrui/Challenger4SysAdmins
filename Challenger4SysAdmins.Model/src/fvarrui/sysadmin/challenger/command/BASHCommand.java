package fvarrui.sysadmin.challenger.command;

import javax.xml.bind.annotation.XmlType;

/**
 * Clase Modelo que representa un comando bash
 * @author Fran Vargas
 * @version 1.0
 * 
 */
@XmlType
public class BASHCommand extends ShellCommand {
	
	private static final String BASH = "bash -c %s";
<<<<<<< HEAD

=======
	
	/**
	 * Constructor por defecto
	 */
>>>>>>> 7afdb606eb5ff55c3880a5f4dbdef57dacedc15a
	public BASHCommand() {
		this(null);
	}

	/**
	 * Constructor
	 * @param command nombre del comando Bash
	 */
	public BASHCommand(String command) {
		super(BASH, command);
	}	
	
}
