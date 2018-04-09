package fvarrui.sysadmin.challenger.model.test;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import fvarrui.sysadmin.challenger.model.command.Command;
import javafx.beans.property.MapProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

/**
 * Clase modelo representa un test de tipo 'CommandTest'.
 * 
 * @author Fran Vargas
 * @version 1.0
 *
 */
@XmlType
public class CommandTest extends Test {

	private static final int SUCCESS_EXIT_CODE = 0;

	private ObjectProperty<Command> command;
	private MapProperty<String, Object> data;

	/**
	 * Constructor por defecto
	 */
	public CommandTest() {
		this(null, null, new HashMap<>());
	}

	public CommandTest(String name) {
		this(name, null, new HashMap<>());
	}

	public CommandTest(String name, Command command) {
		this(name, command, new HashMap<>());
	}

	public CommandTest(String name, Command command, Map<String, Object> data) {
		super(name);
		this.command = new SimpleObjectProperty<Command>(this, "command", command);
		this.data = new SimpleMapProperty<>(this, "data", FXCollections.observableMap(data));
	}

	@Override
	public Boolean verify() {
		verified.set(getCommand().execute(data).getExitValue() == SUCCESS_EXIT_CODE);
		return isVerified();
	}

	public final ObjectProperty<Command> commandProperty() {
		return this.command;
	}

	@XmlElement
	public final Command getCommand() {
		return this.commandProperty().get();
	}

	public final void setCommand(final Command command) {
		this.commandProperty().set(command);
	}

	public final MapProperty<String, Object> dataProperty() {
		return this.data;
	}

	@XmlElement
	public final ObservableMap<String, Object> getData() {
		return this.dataProperty().get();
	}

	public final void setData(final ObservableMap<String, Object> data) {
		this.dataProperty().set(data);
	}

}
