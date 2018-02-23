package fvarrui.sysadmin.challenger.command;

import java.time.LocalDateTime;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ExecutionResult {
	private IntegerProperty returnValue;
	private StringProperty output;
	private StringProperty error;
	private StringProperty executedCommand;
	private ObjectProperty<LocalDateTime> executionTime;

	public ExecutionResult() {
		this.returnValue = new SimpleIntegerProperty(this, "returnValue");
		this.output = new SimpleStringProperty(this, "output");
		this.error = new SimpleStringProperty(this, "error");
		this.executedCommand = new SimpleStringProperty(this, "executedCommand");
		this.executionTime = new SimpleObjectProperty<>(this, "executionTime");
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

	public final StringProperty executedCommandProperty() {
		return this.executedCommand;
	}

	public final String getExecutedCommand() {
		return this.executedCommandProperty().get();
	}

	public final void setExecutedCommand(final String executedCommand) {
		this.executedCommandProperty().set(executedCommand);
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
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("ExecutionTime   : " + getExecutionTime() + "\n");
		buffer.append("ExecutedCommand : " + getExecutedCommand() + "\n");
		buffer.append("ReturnValue     : " + getReturnValue() + "\n");
		buffer.append("Output          : " + getOutput() + "\n");
		buffer.append("Error           : " + getError());
		return buffer.toString();
	}

}
