package fvarrui.sysadmin.editor.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import fvarrui.sysadmin.challenger.command.Command;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

/**
 * Controlador para gestionar un comando
 * 
 * @author Ricardo Vargas
 * @version 1.0
 *
 */
public class CommandController implements Initializable {

	// model
	
	private ObjectProperty<Command> command = new SimpleObjectProperty<>(this, "command");
	
	// view

	@FXML
	private BorderPane view;

	@FXML
	private TextField commandText;

    @FXML
    private ComboBox<String> shellComboBox;

	/**
	 * Constructor por defecto del controlador
	 * @throws IOException  si no pudo cargar la vista
	 */
	public CommandController() throws IOException {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fvarrui/sysadmin/editor/ui/views/CommandView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		command.addListener((o, oldCommand, newCommand) -> onCommandChange(oldCommand, newCommand));
		
	}

	/**
	 * Listener en el modelo para bindear y desbindear los componenetes al modelo
	 * @param oldTest  modelo viejo de Comand
	 * @param newTest  modelo nuevo de Comand
	 */
	private void onCommandChange(Command oldCommand, Command newCommand) {

		if (oldCommand != null) {
			commandText.textProperty().unbindBidirectional(oldCommand.executableProperty());
		}
		if (newCommand != null) {
			commandText.textProperty().bindBidirectional(newCommand.executableProperty());
		}

	}

	public BorderPane getView() {
		return view;
	}

	public final ObjectProperty<Command> commandProperty() {
		return this.command;
	}
	

	public final Command getCommand() {
		return this.commandProperty().get();
	}
	

	public final void setCommand(final Command command) {
		this.commandProperty().set(command);
	}


}
