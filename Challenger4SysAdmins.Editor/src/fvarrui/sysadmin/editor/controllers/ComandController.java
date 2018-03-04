package fvarrui.sysadmin.editor.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import fvarrui.sysadmin.challenger.command.Command;
import fvarrui.sysadmin.challenger.command.ShellCommand;
import fvarrui.sysadmin.editor.comandtype.ComandType;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

/**
 * Logica de negocio para gestionar un comando
 * 
 * @author Ricardo Vargas
 * @version 1.0
 *
 */
public class ComandController implements Initializable {

	private ObjectProperty<ShellCommand> shellCommand = new SimpleObjectProperty<>(this, "shellCommand");
	

	@FXML
	private BorderPane view;

	@FXML
	private TextField nameText;

    @FXML
    private ComboBox<ComandType> shellComboBox;

	/**
	 * Constructor de los comandos
	 * 
	 * @throws IOException
	 *             si no pudo cargar la vista
	 */
	public ComandController() throws IOException {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fvarrui/sysadmin/editor/ui/views/ComandView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		shellCommand.addListener((o, oldComand, newComand) -> onComandChange(oldComand, newComand));
		shellComboBox.setItems(FXCollections.observableArrayList(ComandType.values()));
		shellComboBox.getSelectionModel().selectFirst();
	}

	/**
	 * Listener en el modelo para bindear y desbindear los componenetes al modelo
	 * 
	 * @param oldTest
	 *            modelo viejo de Comand
	 * @param newTest
	 *            modelo nuevo de Comand
	 */
	private void onComandChange(Command oldComand, Command newComand) {

		if (oldComand != null) {
			nameText.textProperty().unbindBidirectional(oldComand.commandProperty());
			
		}
		if (newComand != null) {
			nameText.textProperty().bindBidirectional(newComand.commandProperty());
			
			
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
