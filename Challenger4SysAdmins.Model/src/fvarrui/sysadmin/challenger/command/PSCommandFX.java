package fvarrui.sysadmin.challenger.command;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@XmlType
public class PSCommandFX extends CommandFX {

	private static final String PS = "PowerShell.exe ";

	private StringProperty psCommand;

	public PSCommandFX() {
		this(null);
	}

	public PSCommandFX(String command) {
		super(command);
		psCommand = new SimpleStringProperty(this, "psCommand", command);
		commandProperty().bind(Bindings.concat(PS).concat(psCommand));
	}

	public StringProperty psCommandProperty() {
		return this.psCommand;
	}

	@XmlAttribute
	public String getPsCommand() {
		return this.psCommandProperty().get();
	}

	public void setPsCommand(final String psCommand) {
		this.psCommandProperty().set(psCommand);
	}

	public StringProperty commandProperty() {
		return this.psCommand;
	}

	@XmlAttribute
	public String getCommand() {
		return this.psCommandProperty().get();
	}

	public void setCommand(final String psCommand) {
		this.psCommandProperty().set(psCommand);
	}

}
