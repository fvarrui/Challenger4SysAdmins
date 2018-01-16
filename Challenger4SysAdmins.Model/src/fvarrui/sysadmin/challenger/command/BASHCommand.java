package fvarrui.sysadmin.challenger.command;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class BASHCommand extends Command {
	private static final String BASH = "/bin/bash -c ";
	
	private String bashCommand;
	
	public BASHCommand() {
		super();
	}

	public BASHCommand(String command) {
		this();
		setCommand(command);
	}
	
	@XmlAttribute
	public String getBASHCommand() {
		return bashCommand;
	}
	
	@Override
	public void setCommand(String command) {
		super.setCommand(BASH + command);
		this.bashCommand = command;		
	}
	
	@Override
	public String getCommand() {
		return bashCommand;
	}
	
}
