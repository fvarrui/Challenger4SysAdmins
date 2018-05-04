package fvarrui.sysadmin.challenger.ui.settings;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;
import org.controlsfx.control.PopOver.ArrowLocation;

import com.jfoenix.controls.JFXToggleButton;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
		popOver.setAutoHide(false);
		popOver.setAutoFix(false);
		popOver.setFadeInDuration(Duration.millis(1));
		popOver.setFadeOutDuration(Duration.millis(1));

		settings.addListener((o, ov, nv) -> onSettingsChanged(ov, nv));

	}

	private void onSettingsChanged(Settings oldSettings, Settings newSettings) {
		if (oldSettings != null) {
			enableShellMonitoringToggleButton.selectedProperty().unbindBidirectional(newSettings.enableShellMonitoringProperty());
		}
		if (newSettings != null) {
			enableShellMonitoringToggleButton.selectedProperty().bindBidirectional(newSettings.enableShellMonitoringProperty());
		} 
	}

	public void showPopOver(Node owner) {
		popOver.show(owner);
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
