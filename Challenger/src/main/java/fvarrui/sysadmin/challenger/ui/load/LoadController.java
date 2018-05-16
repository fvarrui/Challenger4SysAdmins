package fvarrui.sysadmin.challenger.ui.load;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import fvarrui.sysadmin.challenger.model.Challenge;
import fvarrui.sysadmin.challenger.ui.ChallengerApp;
import fvarrui.sysadmin.challenger.ui.settings.SettingsController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class LoadController implements Initializable {

	// controllers

	private ObjectProperty<SettingsController> settingsController = new SimpleObjectProperty<>(this, "settingsController");

	// model

	private StringProperty view = new SimpleStringProperty(this, "view");
	private ObjectProperty<Challenge> challenge = new SimpleObjectProperty<>(this, "challenge");

	// view

	@FXML
	private JFXButton openButton, settingsButton;

	@FXML
	private AnchorPane root;

	// constructor

	public LoadController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoadView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	// initialization

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	// event listeners

	@FXML
	private void onSettingsAction(ActionEvent e) {
		// se muestra el popup con las opciones
		getSettingsController().showPopOver(settingsButton);
	}

	@FXML
	private void onOpenAction(ActionEvent e) {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Cargar un reto");
		chooser.getExtensionFilters().add(new ExtensionFilter("Reto (*.challenge)", "*.challenge"));
		chooser.getExtensionFilters().add(new ExtensionFilter("Todos los ficheros (*.*)", "*.*"));
		chooser.setInitialDirectory(new File("."));
		File file = chooser.showOpenDialog(ChallengerApp.getPrimaryStage());
		if (file != null) {
			try {
				challenge.set(Challenge.load(file));
				view.set("challenge");
			} catch (Exception e1) {
				e1.printStackTrace();
				ChallengerApp.error("Error", "No se ha podido abrir el reto", "Ha ocurrido un error al cargar el reto desde el fichero " + file.getName() + ":\n" + e1.getMessage());
			}
		}
	}

	// get root view

	public AnchorPane getRoot() {
		return root;
	}

	// properties

	public final ObjectProperty<SettingsController> settingsControllerProperty() {
		return this.settingsController;
	}

	public final SettingsController getSettingsController() {
		return this.settingsControllerProperty().get();
	}

	public final void setSettingsController(final SettingsController settingsController) {
		this.settingsControllerProperty().set(settingsController);
	}

	public final ObjectProperty<Challenge> challengeProperty() {
		return this.challenge;
	}

	public final Challenge getChallenge() {
		return this.challengeProperty().get();
	}

	public final void setChallenge(final Challenge challenge) {
		this.challengeProperty().set(challenge);
	}

	public final StringProperty viewProperty() {
		return this.view;
	}

	public final String getView() {
		return this.viewProperty().get();
	}

	public final void setView(final String view) {
		this.viewProperty().set(view);
	}

}
