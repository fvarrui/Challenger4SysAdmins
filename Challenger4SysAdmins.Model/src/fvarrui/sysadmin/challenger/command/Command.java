package fvarrui.sysadmin.challenger.command;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.io.IOUtils;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@XmlType
@XmlSeeAlso(value = { BASHCommand.class, DOSCommand.class, PSCommand.class })
public class Command {

	private StringProperty command;
	private IntegerProperty returnValue;
	private StringProperty output;
	private StringProperty error;
	private StringProperty lastExecutedCommand;
	private ObjectProperty<LocalDateTime> lastExecutionTime;

	public Command(String command) {
		this.command = new SimpleStringProperty(this, "command", command);
		this.returnValue = new SimpleIntegerProperty(this, "returnValue");
		this.output = new SimpleStringProperty(this, "output");
		this.error = new SimpleStringProperty(this, "error");
		this.lastExecutedCommand = new SimpleStringProperty(this, "lastExecutedCommand");
		this.lastExecutionTime = new SimpleObjectProperty<>(this, "lastExecutionTime");

	}

	public Command() {
		this(null);
	}

	public StringProperty commandProperty() {
		return this.command;
	}

	@XmlAttribute
	public String getCommand() {
		return this.commandProperty().get();
	}

	public void setCommand(final String command) {
		this.commandProperty().set(command);
	}

	public IntegerProperty returnValueProperty() {
		return this.returnValue;
	}

	@XmlTransient
	public int getReturnValue() {
		return this.returnValueProperty().get();
	}

	public void setReturnValue(final int returnValue) {
		this.returnValueProperty().set(returnValue);
	}

	public StringProperty outputProperty() {
		return this.output;
	}

	@XmlTransient
	public String getOutput() {
		return this.outputProperty().get();
	}

	public void setOutput(final String output) {
		this.outputProperty().set(output);
	}

	public StringProperty errorProperty() {
		return this.error;
	}

	@XmlTransient
	public String getError() {
		return this.errorProperty().get();
	}

	public void setError(final String error) {
		this.errorProperty().set(error);
	}

	public StringProperty lastExecutedCommandProperty() {
		return this.lastExecutedCommand;
	}

	@XmlTransient
	public String getLastExecutedCommand() {
		return this.lastExecutedCommandProperty().get();
	}

	public void setLastExecutedCommand(final String lastExecutedCommand) {
		this.lastExecutedCommandProperty().set(lastExecutedCommand);
	}

	public ObjectProperty<LocalDateTime> lastExecutionTimeProperty() {
		return this.lastExecutionTime;
	}

	@XmlTransient
	public LocalDateTime getLastExecutionTime() {
		return this.lastExecutionTimeProperty().get();
	}

	public void setLastExecutionTime(final LocalDateTime lastExecutionTime) {
		this.lastExecutionTimeProperty().set(lastExecutionTime);
	}

	public int execute(List<String> params) {
		return execute(params.toArray(new String[params.size()]));
	}

	public int execute(String ... params) {
		 try {
			 setLastExecutionTime(LocalDateTime.now());
			 setLastExecutedCommand(String.format(getCommand(), (Object[]) params));
			 String [] splittedCommand = lastExecutedCommand.get().split("[ ]+");
			 ProcessBuilder pb = new ProcessBuilder(splittedCommand);
			 Process p = pb.start();
			 p.getOutputStream().close();
			 setOutput(IOUtils.toString(p.getInputStream(), Charset.defaultCharset()));
			 setError(IOUtils.toString(p.getErrorStream(), Charset.defaultCharset()));
			 setReturnValue(p.exitValue());
		 } catch (Exception e) {
			 setReturnValue(-1);
			 e.printStackTrace();
		 }
		 return getReturnValue();
	 }

}
