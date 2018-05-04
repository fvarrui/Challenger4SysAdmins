package fvarrui.sysadmin.challenger.ui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;

import fvarrui.sysadmin.challenger.common.ui.markdown.MarkdownView;
import fvarrui.sysadmin.challenger.model.Challenge;
import fvarrui.sysadmin.challenger.model.Goal;
import fvarrui.sysadmin.challenger.monitoring.Monitoring;
import fvarrui.sysadmin.challenger.ui.settings.Settings;
import fvarrui.sysadmin.challenger.ui.settings.SettingsController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class MainController implements Initializable {

	// controllers

	private SettingsController settingsController;

	// model

	private ObjectProperty<Settings> settings = new SimpleObjectProperty<>(this, "settings");

	private ObjectProperty<Goal> selectedGoal = new SimpleObjectProperty<>(this, "selectedGoal");
	private ObjectProperty<Challenge> challenge = new SimpleObjectProperty<>(this, "challenge");

	// view

	@FXML
	private Label titleLabel;

	@FXML
	private MarkdownView challengeView, goalView;

	@FXML
	private JFXListView<Goal> goalsList;

	@FXML
	private JFXButton openButton, settingsButton;

	@FXML
	private BorderPane root;

	public MainController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		

		settingsController = new SettingsController();
		settingsController.settingsProperty().bind(settings);
		
		settings.set(new Settings());
		settings.get().setEnableShellMonitoring(Monitoring.test());
		
		
		settings.get().enableShellMonitoringProperty().addListener((o, ov, nv) -> {
			if (nv) {
				Monitoring.enable();
			} else {
				Monitoring.disable();
			}
		});

		selectedGoal.bind(goalsList.getSelectionModel().selectedItemProperty());
		selectedGoal.addListener((o, ov, nv) -> onGoalSelectionChanged(ov, nv));

		challenge.addListener((o, ov, nv) -> onChallengeChanged(ov, nv));
		challenge.set(TestData.getChallenge());

	}

	private void onGoalSelectionChanged(Goal ov, Goal nv) {
		if (ov != null) {
			goalView.markdownProperty().unbind();
		}
		if (nv != null) {
			goalView.markdownProperty().bind(nv.descriptionProperty());
		}
	}

	private void onChallengeChanged(Challenge ov, Challenge nv) {
		if (ov != null) {
			titleLabel.textProperty().unbind();
			challengeView.markdownProperty().unbind();
			goalsList.itemsProperty().unbind();
		}
		if (nv != null) {
			titleLabel.textProperty().bind(nv.nameProperty());
			challengeView.markdownProperty().bind(nv.descriptionProperty());
			goalsList.itemsProperty().bind(nv.goalsProperty());
			goalsList.getSelectionModel().selectFirst();
		}
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
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@FXML
	private void onSettingsClicked(MouseEvent e) {
		settingsController.showPopOver(settingsButton);
	}

	public BorderPane getRoot() {
		return root;
	}

}
