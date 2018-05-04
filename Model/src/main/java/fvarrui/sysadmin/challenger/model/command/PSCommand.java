package fvarrui.sysadmin.challenger.model.command;

import javax.xml.bind.annotation.XmlType;

/**
 * Clase modelo representa un comando de PowerShell.
 * @author Fran Vargas
 * @version 1.0
 *
 */
@XmlType
public class PSCommand extends Command {

	private static final String PS = "powershell";

	public PSCommand() {
		this(null);
	}

	public PSCommand(String cmdlet) {
		super(PS, "-NoProfile", "-WindowStyle", "Hidden", "-Command", cmdlet);
	}

}
