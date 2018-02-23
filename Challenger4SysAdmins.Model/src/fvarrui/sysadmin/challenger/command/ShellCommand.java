package fvarrui.sysadmin.challenger.command;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import javafx.beans.property.StringProperty;

@XmlType
@XmlSeeAlso(value = { BASHCommand.class, DOSCommand.class, PSCommand.class })
public class ShellCommand extends Command {

	private Command shell;

	public ShellCommand() {
		this(null, null);
	}

	public ShellCommand(String shell, String command) {
		super(command);
		this.shell = new Command(shell);
	}

	@Override
	public ExecutionResult execute(String... params) {
		return shell.execute(prepareCommand(params));
	}
	
	@XmlAttribute
	public String getShell() {
		return shell.getCommand();
	}
	
	public void setShell(String shell) {
		this.shell.setCommand(shell);
	}
	
	public StringProperty shellProperty() {
		return this.shell.commandProperty();
	}

}
