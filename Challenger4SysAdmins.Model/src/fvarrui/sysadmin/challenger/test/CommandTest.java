package fvarrui.sysadmin.challenger.test;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import fvarrui.sysadmin.challenger.command.Command;

@XmlType
public class CommandTest extends Test {
	private static final int SUCCESS_EXIT_CODE = 0;

	private Command command;
	private String[] params;

	public CommandTest() {
		this(null, null);
	}

	public CommandTest(String name, Command command, String... params) {
		super(name);
		this.command = command;
		this.params = params;
	}

	@XmlElement
	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}

	@XmlElement
	public String[] getParams() {
		return params;
	}

	public void setParams(String[] params) {
		this.params = params;
	}

	@Override
	public Boolean verify() {
		return verified = (command.execute(params) == SUCCESS_EXIT_CODE);
	}

}
