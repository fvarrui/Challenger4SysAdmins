package fvarrui.sysadmin.challenger.command;

import javax.xml.bind.annotation.XmlType;

/**
 * Clase modelo representa un comando de Power Shell.
 * @author Fran Vargas
 * @version 1.0
 *
 */
@XmlType
public class PSCommand extends ShellCommand {

	private static final String PS = "powershell \"%s\"";

	/**
	 * Constructor por defecto
	 */
	public PSCommand() {
		this(null);
	}

	/**
	 * 
	 * @param command nombrel del comando
	 */
	public PSCommand(String command) {
		super(PS, command);
	}

}
