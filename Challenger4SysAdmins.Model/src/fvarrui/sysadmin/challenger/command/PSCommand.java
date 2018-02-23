package fvarrui.sysadmin.challenger.command;

import javax.xml.bind.annotation.XmlType;

@XmlType
public class PSCommand extends ShellCommand {

	private static final String PS = "PowerShell & { %s }";

	public PSCommand() {
		this(null);
	}

	public PSCommand(String command) {
		super(PS, command);
	}

}
