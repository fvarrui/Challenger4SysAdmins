package fvarrui.sysadmin.challenger.command;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.io.IOUtils;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Clase Modelo que representa un comando
 * 
 * @author Fran Vargas
 * @version 1.0
 * 
 */
@XmlType
@XmlSeeAlso(value = { ShellCommand.class })
public class Command {

	private StringProperty command;
	private ReadOnlyObjectWrapper<ExecutionResult> result;

	/**
	 * Constructor
	 * 
	 * @param command
	 *            nombre del comando
	 */
	public Command(String command) {
		this.command = new SimpleStringProperty(this, "command", command);
		this.result = new ReadOnlyObjectWrapper<>(this, "result");
	}

	/**
	 * Constructor por defecto
	 */
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

	public final ReadOnlyObjectProperty<ExecutionResult> resultProperty() {
		return this.result.getReadOnlyProperty();
	}

	@XmlTransient
	public final ExecutionResult getResult() {
		return this.resultProperty().get();
	}

	public ExecutionResult execute(List<String> params) {
		return execute(params.toArray(new String[params.size()]));
	}

	protected String prepareCommand(String... params) {
		return String.format(getCommand(), (Object[]) params);
	}

	/**
	 * 
	 * @param Un
	 *            conjunto de parametros
	 * @return los valores de salida de la ejecucion
	 */
	public ExecutionResult execute(String... params) {
		ExecutionResult result = new ExecutionResult();
		try {

			LocalDateTime before = LocalDateTime.now();

			result.setExecutionTime(before);
			result.setExecutedCommand(prepareCommand(params));

			String[] splittedCommand = result.getExecutedCommand().split("[ ]+");
			ProcessBuilder pb = new ProcessBuilder(splittedCommand);
			Process p = pb.start();
			p.getOutputStream().close();

			LocalDateTime after = LocalDateTime.now();

			result.setDuration(Duration.between(before, after));
			result.setOutput(IOUtils.toString(p.getInputStream(), Charset.defaultCharset()));
			result.setError(IOUtils.toString(p.getErrorStream(), Charset.defaultCharset()));
			result.setReturnValue(p.exitValue());
		} catch (IOException e) {
			result.setError(e.getMessage());
			result.setReturnValue(-1);
			e.printStackTrace();
		} catch (Exception e) {
			result.setError(e.getMessage());
			result.setReturnValue(-1);
			e.printStackTrace();
		}
		this.result.set(result);
		return result;
	}
	
	@Override
	public String toString() {
		return getCommand();
	}

}
