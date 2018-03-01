package fvarrui.sysadmin.challenger.command;

import javax.xml.bind.annotation.XmlType;


@XmlType
public class BASHCommand extends ShellCommand {
	
	private static final String BASH = "bash -c %s";

	public BASHCommand() {
		this(null);
	}

	public BASHCommand(String command) {
		super(BASH, command);
	}	
	
}
