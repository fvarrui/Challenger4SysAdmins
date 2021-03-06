package fvarrui.sysadmin.challenger.ui.settings;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;
import org.controlsfx.control.PopOver.ArrowLocation;

import com.jfoenix.controls.JFXToggleButton;

import fvarrui.sysadmin.challenger.monitoring.Monitoring;
import fvarrui.sysadmin.challenger.ui.ChallengerApp;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class SettingsController implements Initializable {

	// model

	private ObjectProperty<Settings> settings = new SimpleObjectProperty<>(this, "settings");

	// view
	private PopOver popOver;

	@FXML
	private BorderPane root;

	@FXML
	private Label osLabel;
	
	@FXML
	private JFXToggleButton enableShellMonitoringToggleButton;

	public SettingsController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SettingsView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		popOver = new PopOver(root);
		popOver.setTitle("Configuración");
		popOver.setArrowLocation(ArrowLocation.TOP_RIGHT);
		popOver.setCloseButtonEnabled(true);
		popOver.setHeaderAlwaysVisible(true);
		popOver.setDetachable(false);
		popOver.setCornerRadius(2);
		popOver.setAutoHide(true);
		popOver.setAutoFix(false);
		popOver.setFadeInDuration(Duration.millis(1));
		popOver.setFadeOutDuration(Duration.millis(1));

		settings.addListener((o, ov, nv) -> onSettingsChanged(ov, nv));
		
		enableShellMonitoringToggleButton.setOnAction(e -> onEnableShellMonitoringAction());

	}

	private void onEnableShellMonitoringAction() {
		boolean value = enableShellMonitoringToggleButton.selectedProperty().get();
		if (value) {
			boolean result = Monitoring.enable();
			if (!result) {
				enableShellMonitoringToggleButton.selectedProperty().set(!value);				
				ChallengerApp.error("Challenger", "Habilitar monitorización", "No fue posible habilitar la monitorización de intérpretes de comandos.");
			}
		} else {
			boolean result = Monitoring.disable();
			if (!result) {
				enableShellMonitoringToggleButton.selectedProperty().set(!value);				
				ChallengerApp.error("Challenger", "Deshabilitar monitorización", "No fue posible deshabilitar la monitorización de intérpretes de comandos.");
			}
		}
	}

	private void onSettingsChanged(Settings oldSettings, Settings newSettings) {
		if (oldSettings != null) {
			enableShellMonitoringToggleButton.selectedProperty().unbindBidirectional(newSettings.enableShellMonitoringProperty());
			osLabel.textProperty().unbind();
		}
		if (newSettings != null) {
			enableShellMonitoringToggleButton.selectedProperty().bindBidirectional(newSettings.enableShellMonitoringProperty());
			osLabel.textProperty().bind(newSettings.osProperty());
		} 
	}

	public void showPopOver(Node owner) {
		if (!popOver.isShowing())
			popOver.show(owner);
		else
			popOver.hide();
	}

	public final ObjectProperty<Settings> settingsProperty() {
		return this.settings;
	}

	public final Settings getSettings() {
		return this.settingsProperty().get();
	}

	public final void setSettings(final Settings settings) {
		this.settingsProperty().set(settings);
	}

}
