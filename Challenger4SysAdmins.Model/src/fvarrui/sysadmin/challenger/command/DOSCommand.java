package fvarrui.sysadmin.challenger.command;

import javax.xml.bind.annotation.XmlType;

@XmlType
public class DOSCommand extends ShellCommand {

	private static final String CMD = "cmd /c %s";

	public DOSCommand() {
		this(null);
	}

	public DOSCommand(String command) {
		super(CMD, command);
	}

}
