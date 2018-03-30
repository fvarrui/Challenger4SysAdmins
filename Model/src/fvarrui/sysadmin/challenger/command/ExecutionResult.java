package fvarrui.sysadmin.challenger.command;

import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDateTime;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Resultado de la ejecución de un comando del sistema.
 * 
 * @author Fran Vargas
 * @version 1.0
 *
 */

public class ExecutionResult {

	private IntegerProperty returnValue;
	private ObjectProperty<InputStream> outputStream;
	private ObjectProperty<InputStream> errorStream;
	private StringProperty output;
	private StringProperty error;
	private StringProperty executedCommand;
	private StringProperty params;
	private ObjectProperty<LocalDateTime> executionTime;
	private ObjectProperty<Duration> duration;

	public ExecutionResult() {
		this.returnValue = new SimpleIntegerProperty(this, "returnValue", 0);
		this.outputStream = new SimpleObjectProperty<>(this, "outputStream");
		this.errorStream = new SimpleObjectProperty<>(this, "errorStream");
		this.output = new SimpleStringProperty(this, "output");
		this.error = new SimpleStringProperty(this, "error");
		this.executedCommand = new SimpleStringProperty(this, "executedCommand");
		this.params = new SimpleStringProperty(this, "params");
		this.executionTime = new SimpleObjectProperty<>(this, "executionTime");
		this.duration = new SimpleObjectProperty<>(this, "duration");
	}

	public final IntegerProperty returnValueProperty() {
		return this.returnValue;
	}

	public final int getReturnValue() {
		return this.returnValueProperty().get();
	}

	public final void setReturnValue(final int returnValue) {
		this.returnValueProperty().set(returnValue);
	}

	public final StringProperty executedCommandProperty() {
		return this.executedCommand;
	}

	public final String getExecutedCommand() {
		return this.executedCommandProperty().get();
	}

	public final void setExecutedCommand(final String executedCommand) {
		this.executedCommandProperty().set(executedCommand);
	}

	public final StringProperty paramsProperty() {
		return this.params;
	}

	public final String getParams() {
		return this.paramsProperty().get();
	}

	public final void setParams(final String params) {
		this.paramsProperty().set(params);
	}

	public final ObjectProperty<LocalDateTime> executionTimeProperty() {
		return this.executionTime;
	}

	public final LocalDateTime getExecutionTime() {
		return this.executionTimeProperty().get();
	}

	public final void setExecutionTime(final LocalDateTime executionTime) {
		this.executionTimeProperty().set(executionTime);
	}

	public final ObjectProperty<Duration> durationProperty() {
		return this.duration;
	}

	public final Duration getDuration() {
		return this.durationProperty().get();
	}

	public final void setDuration(final Duration duration) {
		this.durationProperty().set(duration);
	}

	public final ObjectProperty<InputStream> outputStreamProperty() {
		return this.outputStream;
	}

	public final InputStream getOutputStream() {
		return this.outputStreamProperty().get();
	}

	public final void setOutputStream(final InputStream outputStream) {
		this.outputStreamProperty().set(outputStream);
	}

	public final ObjectProperty<InputStream> errorStreamProperty() {
		return this.errorStream;
	}

	public final InputStream getErrorStream() {
		return this.errorStreamProperty().get();
	}

	public final void setErrorStream(final InputStream errorStream) {
		this.errorStreamProperty().set(errorStream);
	}

	public final StringProperty outputProperty() {
		return this.output;
	}

	public final String getOutput() {
		return this.outputProperty().get();
	}

	public final void setOutput(final String output) {
		this.outputProperty().set(output);
	}

	public final StringProperty errorProperty() {
		return this.error;
	}

	public final String getError() {
		return this.errorProperty().get();
	}

	public final void setError(final String error) {
		this.errorProperty().set(error);
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("ExecutedCommand : " + getExecutedCommand() + "\n");
		buffer.append("Params          : " + getParams() + "\n");
		buffer.append("ExecutionTime   : " + getExecutionTime() + "\n");
		buffer.append("Duration        : " + getDuration().toMillis() + "ms\n");
		buffer.append("ReturnValue     : " + getReturnValue() + "\n");
		buffer.append("Output:\n" + getOutput() + "\n");
		buffer.append("Error:\n" + getError());
		return buffer.toString();
	}

}
