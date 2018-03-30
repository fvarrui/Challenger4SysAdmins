package fvarrui.sysadmin.challenger.command;

import java.io.InputStream;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import javafx.beans.property.StringProperty;

/**
 * Clase modelo representa un comando de Shell.
 * 
 * @author Fran Vargas
 * @version 1.0
 *
 */
@XmlType
@XmlSeeAlso(value = { BASHCommand.class, DOSCommand.class, PSCommand.class })
public class ShellCommand extends Command {

	private Command shell;

	/**
	 * Constructor por defecto
	 */
	public ShellCommand() {
		this(null, null);
	}

	/**
	 * Constructor del comando con Shell
	 * @param shell
	 *            Orden que corresponde con la Shell utilizada para ejecutar el
	 *            comando. Debe contener el indicador de formato %s, que se remplazará
	 *            por el comando
	 * @param command
	 *            Comando que se ejecutará sobre la Shell. Corresponde con %s en la Shell.
	 */
	public ShellCommand(String shell, String command) {
		super(command);
		this.shell = new Command(shell);
	}

	@Override
	public ExecutionResult execute(boolean waitFor, String... params) {
		return shell.execute(waitFor, prepareCommand(params));
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
