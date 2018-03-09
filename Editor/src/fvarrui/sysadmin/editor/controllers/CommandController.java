package fvarrui.sysadmin.editor.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import fvarrui.sysadmin.challenger.command.ShellCommand;
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
	
	private ObjectProperty<ShellCommand> shellCommand = new SimpleObjectProperty<>(this, "shellCommand");
	
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

		shellCommand.addListener((o, oldComand, newComand) -> onComandChange(oldComand, newComand));

	}

	/**
	 * Listener en el modelo para bindear y desbindear los componenetes al modelo
	 * @param oldTest  modelo viejo de Comand
	 * @param newTest  modelo nuevo de Comand
	 */
	private void onComandChange(ShellCommand oldComand, ShellCommand newComand) {

		if (oldComand != null) {
			commandText.textProperty().unbindBidirectional(oldComand.commandProperty());
			shellComboBox.valueProperty().unbindBidirectional(oldComand.shellProperty());
		}
		if (newComand != null) {
			commandText.textProperty().bindBidirectional(newComand.commandProperty());
			shellComboBox.valueProperty().bindBidirectional(newComand.shellProperty());
		}

	}

	public BorderPane getView() {
		return view;
	}

	public final ObjectProperty<ShellCommand> shellCommandProperty() {
		return this.shellCommand;
	}
	

	public final ShellCommand getShellCommand() {
		return this.shellCommandProperty().get();
	}
	

	public final void setShellCommand(final ShellCommand shellCommand) {
		this.shellCommandProperty().set(shellCommand);
	}


}
