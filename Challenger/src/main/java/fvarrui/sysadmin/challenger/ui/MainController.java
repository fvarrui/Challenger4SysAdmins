package fvarrui.sysadmin.challenger.ui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.lang.SystemUtils;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;

import fvarrui.sysadmin.challenger.common.ui.markdown.MarkdownView;
import fvarrui.sysadmin.challenger.model.Challenge;
import fvarrui.sysadmin.challenger.model.Goal;
import fvarrui.sysadmin.challenger.model.test.ExecutedCommand;
import fvarrui.sysadmin.challenger.monitoring.Monitoring;
import fvarrui.sysadmin.challenger.ui.settings.Settings;
import fvarrui.sysadmin.challenger.ui.settings.SettingsController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener.Change;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class MainController implements Initializable {
	
//	private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

	// controllers

	private SettingsController settingsController;

	// model

	private ChallengeTask task;
	
	private BooleanProperty running = new SimpleBooleanProperty(this, "running", false);
	private BooleanProperty paused = new SimpleBooleanProperty(this, "paused", false);
	
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
	private JFXButton startButton, pauseButton, restartButton, openButton, settingsButton;

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
		settings.get().setOs(String.format("%s (%s)", SystemUtils.OS_NAME, SystemUtils.OS_VERSION));
		
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
		challenge.set(TestData.getTest2()); // TODO prbar distintos test codigicados
		
		startButton.disableProperty().bind(challenge.isNull().or(running));
		pauseButton.disableProperty().bind(challenge.isNull().or(running.not()));
		restartButton.disableProperty().bind(challenge.isNull());
	
		Monitoring.getExecutedCommands().addListener((Change<? extends ExecutedCommand> c) -> {
			while (c.next()) {
				for (ExecutedCommand cmd : c.getAddedSubList()) {
					System.out.println("--------------------------> " + cmd.getCommand());
				}
			}			
		});
		
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
			if (task != null && task.isRunning()) {
				task.stop();
			}
			task = null;
		}
		if (nv != null) {
			titleLabel.textProperty().bind(nv.nameProperty());
			challengeView.markdownProperty().bind(nv.descriptionProperty());
			goalsList.itemsProperty().bind(nv.goalsProperty());
			goalsList.getSelectionModel().selectFirst();
		}
	}

	@FXML
	private void onStartAction(ActionEvent e) {
		
		// si se "pausó" el reto, no se retea antes de volver a iniciarlo
		if (paused.get()) {
			challenge.get().reset();
		}
		
		// se crea una tarea para ejecutar el reto en segundo plano
		task = new ChallengeTask(challenge.get());
		
		// se pone un listener al estado de la tarea para cambiar la propiedad "running" y controlar el estado de los botones
		task.stateProperty().addListener((o, ov, nv) -> {
			running.set(nv.equals(State.RUNNING));
		});
		
		task.setOnSucceeded(v -> {
			Boolean result = (Boolean) v.getSource().getValue();

			if (result) {
				
				challenge.get().reset();
				
				Alert mensaje = new Alert(AlertType.INFORMATION);
				mensaje.initOwner(ChallengerApp.getPrimaryStage());
				mensaje.setTitle(ChallengerApp.getPrimaryStage().getTitle());
				mensaje.setHeaderText("¡Reto completado!");
				mensaje.setContentText(String.format("Enhorabuena, has completado el reto '%s'.", challenge.get().getName()));
				mensaje.showAndWait();
				
			}
			
		});
		
		// se deshabilita la pausa
		paused.set(false);
		
		// se lanza la tarea 
		Thread thread = new Thread(task);
		thread.setDaemon(true); // como demonio, para que finalice la aplicación si éste el único hilo que queda vivo
		thread.start();

		Monitoring.start();
	}

	@FXML
	private void onPauseAction(ActionEvent e) {
		System.out.println("Pausando reto");
		paused.set(true);
		task.stop();
		
		Monitoring.stop();
	}

	@FXML
	private void onRestartAction(ActionEvent e) {
		System.out.println("Reiniciando reto");
		challenge.get().reset();
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
		// se muestar el popup con las opciones
		settingsController.showPopOver(settingsButton);
	}

	public BorderPane getRoot() {
		return root;
	}

}
