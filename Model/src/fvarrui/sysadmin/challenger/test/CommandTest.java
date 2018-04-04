package fvarrui.sysadmin.challenger.test;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import fvarrui.sysadmin.challenger.command.Command;
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
	private MapProperty<String, Object> substitutionMap;

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

	public CommandTest(String name, Command command, Map<String, Object> substitutionMap) {
		super(name);
		this.command = new SimpleObjectProperty<Command>(this, "command", command);
		this.substitutionMap = new SimpleMapProperty<>(this, "substitutionMap",
				FXCollections.observableMap(substitutionMap));
	}

	@Override
	public Boolean verify() {
		verified.set(getCommand().execute(substitutionMap).getExitValue() == SUCCESS_EXIT_CODE);
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

	public final MapProperty<String, Object> substitutionMapProperty() {
		return this.substitutionMap;
	}

	@XmlElement
	public final ObservableMap<String, Object> getSubstitutionMap() {
		return this.substitutionMapProperty().get();
	}

	public final void setSubstitutionMap(final ObservableMap<String, Object> substitutionMap) {
		this.substitutionMapProperty().set(substitutionMap);
	}

}
