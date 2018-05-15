package fvarrui.sysadmin.challenger.ui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.lang.SystemUtils;
import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXButton;

import fvarrui.sysadmin.challenger.common.utils.MarkdownUtils;
import fvarrui.sysadmin.challenger.model.Challenge;
import fvarrui.sysadmin.challenger.model.Goal;
import fvarrui.sysadmin.challenger.monitoring.Monitoring;
import fvarrui.sysadmin.challenger.ui.settings.Settings;
import fvarrui.sysadmin.challenger.ui.settings.SettingsController;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class MainController implements Initializable {
	
	// controllers

	private SettingsController settingsController;

	// model

	private ChallengeTask challengeTask;
	
	private StringProperty challengeDescription = new SimpleStringProperty(this, "challengeDescription");
	private StringProperty goalDescription = new SimpleStringProperty(this, "goalDescription");
	
	private BooleanProperty running = new SimpleBooleanProperty(this, "running", false);
	private BooleanProperty paused = new SimpleBooleanProperty(this, "paused", false);
	
	private ObjectProperty<Settings> settings = new SimpleObjectProperty<>(this, "settings");

	private ObjectProperty<Goal> selectedGoal = new SimpleObjectProperty<>(this, "selectedGoal");
	private ObjectProperty<Challenge> challenge = new SimpleObjectProperty<>(this, "challenge");
	
	
	// view

	@FXML
	private Label titleLabel;

	@FXML
	private WebView challengeView, goalView;

	@FXML
	private ListView<Goal> goalsList;
	
	@FXML
	private TabPane tabPane;
	
	@FXML
	private Tab challengeTab, goalsTab;

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
		
		selectedGoal.bind(goalsList.getSelectionModel().selectedItemProperty());
		selectedGoal.addListener((o, ov, nv) -> onGoalSelectionChanged(ov, nv));

		challenge.addListener((o, ov, nv) -> onChallengeChanged(ov, nv));
		
		startButton.disableProperty().bind(challenge.isNull().or(running));
		pauseButton.disableProperty().bind(challenge.isNull().or(running.not()));
		restartButton.disableProperty().bind(challenge.isNull());
		
		goalsList.setCellFactory((ListView<Goal> lv) -> new GoalListCell());
		
		challengeDescription.addListener((o, ov, nv) -> {
			if (nv != null) {
				System.out.println("nueva descripción de reto");
				Platform.runLater(() -> challengeView.getEngine().loadContent(MarkdownUtils.render(nv)));
			}
		});
		
		goalDescription.addListener((o, ov, nv) -> {
			if (nv != null) {
				System.out.println("nueva descripción de objetivo");
				Platform.runLater(() -> goalView.getEngine().loadContent(MarkdownUtils.render(nv)));
			}
		});
		
		challenge.set(TestData.getDefaultChallenge()); // TODO probar distintos test codificados
	}

	private void onGoalSelectionChanged(Goal ov, Goal nv) {
		if (ov != null) {
			System.out.println("desbindeando objetivo " + ov.getName());
			goalDescription.unbind();
		}
		if (nv != null) {
			System.out.println("bindeando objetivo " + nv.getName());
			goalDescription.bind(nv.descriptionProperty());
		}
	}

	private void onChallengeChanged(Challenge ov, Challenge nv) {
		if (ov != null) {
			System.out.println("desbindeando challenge " + ov.getName());
			challengeDescription.unbind();
			goalDescription.unbind();
			titleLabel.textProperty().unbind();
			goalsList.itemsProperty().unbind();
			if (challengeTask != null && challengeTask.isRunning()) {
				challengeTask.stop();
			}
			challengeTask = null;
		}
		if (nv != null) {
			System.out.println("bindeando challenge " + nv.getName());

			tabPane.getSelectionModel().select(challengeTab);
			
			challengeDescription.bind(nv.descriptionProperty());			
			titleLabel.textProperty().bind(nv.nameProperty());
			goalsList.itemsProperty().bind(nv.goalsProperty());
			goalsList.getSelectionModel().selectFirst();
			
			// poner listener a los achieved de todos los goals, para mostrar notificación y enfocar el siguiente objetivo
			for (int i = 0; i < nv.getGoals().size(); i++) {
				Goal currentGoal = nv.getGoals().get(i);
				final Goal nextGoal = (i < nv.getGoals().size() - 1) ? nv.getGoals().get(i + 1) : null;
				currentGoal.achievedProperty().addListener((ob, oldv, newv) -> {
					if (newv) onGoalAchieved(currentGoal, nextGoal);
				});
			}
			
		}
	}

	private void onGoalAchieved(Goal currentGoal, Goal nextGoal) {
		String message;
		if (nextGoal != null) {
			goalsList.getSelectionModel().select(nextGoal);
			message = "¡Buen trabajo! Has cumplido el objetivo '" + currentGoal.getName() + "'.\nVe al Challenger a consultar tu nuevo objetivo.";
		} else {
			goalsList.getSelectionModel().clearSelection();
			message = "¡Enhorabuena! Has cumplido el último objetivo y has completado el reto";
		}
		Platform.runLater(() -> {
			Notifications
				.create()
				.title("Objetivo completado")
				.text(message)
				.showInformation();
			tabPane.getSelectionModel().select(goalsTab);
			goalsList.requestFocus();
		});
	}

	@FXML
	private void onStartAction(ActionEvent e) {
		
		// si se "pausó" el reto, no se retea antes de volver a iniciarlo
		if (paused.get()) {
			System.out.println("Reanudando reto");
			challenge.get().reset();
		} else {
			System.out.println("Iniciando reto");
		}
		
		// cambia a la pestaña de objetivos
		tabPane.getSelectionModel().select(goalsTab);
		
		// se crea una tarea para ejecutar el reto en segundo plano
		challengeTask = new ChallengeTask(challenge.get());
		
		// se pone un listener al estado de la tarea para cambiar la propiedad "running" y controlar el estado de los botones
		challengeTask.stateProperty().addListener((o, ov, nv) -> {
			running.set(nv == State.RUNNING);
		});
		
		challengeTask.setOnSucceeded(v -> {
			Boolean result = (Boolean) v.getSource().getValue();

			if (result) {
				
				Alert mensaje = new Alert(AlertType.INFORMATION);
				mensaje.initOwner(ChallengerApp.getPrimaryStage());
				mensaje.setTitle(ChallengerApp.getPrimaryStage().getTitle());
				mensaje.setHeaderText("¡Reto completado!");
				mensaje.setContentText(String.format("Enhorabuena, has completado el reto '%s'.", challenge.get().getName()));
				mensaje.showAndWait();
				
				challenge.get().reset();
				
			}
			
		});
		
		// se deshabilita la pausa
		paused.set(false);
		
		// se lanza la tarea 
		Thread thread = new Thread(challengeTask);
		thread.setDaemon(true); // como demonio, para que finalice la aplicación si éste el único hilo que queda vivo
		thread.start();

		Monitoring.start();
	}

	@FXML
	private void onPauseAction(ActionEvent e) {
		System.out.println("Pausando reto");
		paused.set(true);
		challengeTask.stop();
		
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
		// se muestra el popup con las opciones
		settingsController.showPopOver(settingsButton);
	}

	public BorderPane getRoot() {
		return root;
	}

}
