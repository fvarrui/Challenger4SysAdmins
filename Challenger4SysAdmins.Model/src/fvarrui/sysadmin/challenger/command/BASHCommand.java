package fvarrui.sysadmin.challenger.command;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


@XmlType
public class BASHCommand extends Command {
	private static final String BASH = "/bin/bash -c ";

	private StringProperty bashCommand;

	public BASHCommand() {
		this(null);
	}

	public BASHCommand(String command) {
		super(command);
		bashCommand = new SimpleStringProperty(this, "bashCommand", command);
		commandProperty().bind(Bindings.concat(BASH).concat(bashCommand));
	}

	public StringProperty bashCommandProperty() {
		return this.bashCommand;
	}

	@XmlAttribute
	public String getBashCommand() {
		return this.bashCommandProperty().get();
	}

	public void setBashCommand(final String bashCommand) {
		this.bashCommandProperty().set(bashCommand);
	}
	
	public StringProperty commandProperty() {
		return this.bashCommand;
	}
	@XmlAttribute
	public String getCommand() {
		return this.bashCommandProperty().get();
	}

	public void setCommand(final String bashCommand) {
		this.bashCommandProperty().set(bashCommand);
	}

}
