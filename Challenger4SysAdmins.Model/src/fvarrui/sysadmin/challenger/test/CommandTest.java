package fvarrui.sysadmin.challenger.test;

import java.util.Arrays;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import fvarrui.sysadmin.challenger.command.Command;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@XmlType
public class CommandTest extends Test {

	private static final int SUCCESS_EXIT_CODE = 0;

	private ObjectProperty<Command> command;

	private ListProperty<String> params;

	public CommandTest() {
		this(null, null);
	}

	public CommandTest(String name, Command command, String... params) {
		super(name);
		this.command = new SimpleObjectProperty<Command>(this, "command", command);
		this.params = new SimpleListProperty<>(this, "params", FXCollections.observableList(Arrays.asList(params)));
	}

	@Override
	public Boolean verify() {
		verified.set(getCommand().execute(getParams()).getReturnValue() == SUCCESS_EXIT_CODE);
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

	public final ListProperty<String> paramsProperty() {
		return this.params;
	}

	@XmlElement
	public final ObservableList<String> getParams() {
		return this.paramsProperty().get();
	}

	public final void setParams(final ObservableList<String> params) {
		this.paramsProperty().set(params);
	}

}
