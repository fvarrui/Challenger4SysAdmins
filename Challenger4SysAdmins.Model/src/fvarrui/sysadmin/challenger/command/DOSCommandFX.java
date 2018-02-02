package fvarrui.sysadmin.challenger.command;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@XmlType
public class DOSCommandFX extends CommandFX {

	private static final String DOS="cmd /c ";
	
	private StringProperty dosCommand;

	public DOSCommandFX() {
		this(null);
	}

	public DOSCommandFX(String command) {
		super(command);
		dosCommand=new SimpleStringProperty(this,"dosCommand",command);
		commandProperty().bind(Bindings.concat(DOS).concat(dosCommand));
	}

	public StringProperty dosCommandProperty() {
		return this.dosCommand;
	}
	
	@XmlAttribute
	public String getDosCommand() {
		return this.dosCommandProperty().get();
	}
	

	public void setDosCommand(final String dosCommand) {
		this.dosCommandProperty().set(dosCommand);
	}

	public StringProperty commandProperty() {
		return this.dosCommand;
	}
	@XmlAttribute
	public String getCommand() {
		return this.dosCommandProperty().get();
	}

	public void setCommand(final String dosCommand) {
		this.dosCommandProperty().set(dosCommand);
	}
	
	

}
