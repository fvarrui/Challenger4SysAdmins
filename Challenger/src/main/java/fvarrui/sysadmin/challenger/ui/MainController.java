package fvarrui.sysadmin.challenger.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.lang.SystemUtils;

import fvarrui.sysadmin.challenger.monitoring.Monitoring;
import fvarrui.sysadmin.challenger.ui.challenge.ChallengeController;
import fvarrui.sysadmin.challenger.ui.load.LoadController;
import fvarrui.sysadmin.challenger.ui.settings.Settings;
import fvarrui.sysadmin.challenger.ui.settings.SettingsController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class MainController implements Initializable {

	// controllers

	private ObjectProperty<SettingsController> settingsController;
	private ChallengeController challengeController;
	private LoadController loadController;

	// model

	// vista mostrada actualmente
	private StringProperty view = new SimpleStringProperty(this, "view");

	// configuración de la aplicación
	private ObjectProperty<Settings> settings = new SimpleObjectProperty<>(this, "settings");

	// view

	@FXML
	private BorderPane root;

	// constructor

	public MainController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	// initialization

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// establece la configuración inicial de la aplicación
		settings.set(new Settings());
		settings.get().setEnableShellMonitoring(Monitoring.test());
		settings.get().setOs(String.format("%s (%s)", SystemUtils.OS_NAME, SystemUtils.OS_VERSION));

		// instancia el controlador de Configuración y lo enlaza con el objeto de
		// preferencias
		settingsController = new SimpleObjectProperty<>(this, "settingsController", new SettingsController());
		settingsController.get().settingsProperty().bind(settings);

		// instancia el controlador de Carga
		loadController = new LoadController();
		loadController.settingsControllerProperty().bind(settingsController); // lo enlaza con el controlador de Configuración
		loadController.viewProperty().bindBidirectional(view);

		// instancia el controlador de Retos y lo enlaza con el controlador de Configuración
		challengeController = new ChallengeController();
		challengeController.settingsControllerProperty().bind(settingsController);
		challengeController.viewProperty().bindBidirectional(view);
		challengeController.challengeProperty().bindBidirectional(loadController.challengeProperty());

		// establece un listener para cambiar la vista
		view.addListener((o, ov, nv) -> onViewChanged(ov, nv));
		
		view.set("load");

	}

	// event listeners

	private void onViewChanged(String ov, String nv) {
		if ("load".equals(nv)) {
			root.setCenter(loadController.getRoot());
		} else if ("challenge".equals(nv)) {
			root.setCenter(challengeController.getRoot());
		} else {
			root.setCenter(new Label("Error al establecer la vista: " + nv));
		}
	}

	// get root view

	public BorderPane getRoot() {
		return root;
	}

}
