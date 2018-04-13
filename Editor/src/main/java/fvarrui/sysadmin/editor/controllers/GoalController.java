package fvarrui.sysadmin.editor.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import fvarrui.sysadmin.challenger.model.Goal;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;



/**
 * Controlador para la gestión de los objetivos del reto.
 * 
 * @author Fran Vargas, Ricardo Vargas
 * @version 1.0
 *
 */

public class GoalController implements Initializable {

	// model
	
	private ObjectProperty<Goal> goal = new SimpleObjectProperty<>(this, "goal");

	// view
	
	@FXML
	private BorderPane view;

	@FXML
	private TextArea descriptionTextArea;

	@FXML
	private TextField nameText;

	
	/**
	 * Constructor por defecto del controlador
	 * @throws IOException Si no pudo cargar el FXML la vista
	 */
	public GoalController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fvarrui/sysadmin/editor/ui/views/GoalView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		goal.addListener((o, ov, nv) -> onGoalChange(ov, nv));

	}

	/**
	 * Listener en el modelo para bindear y desbindear los componenetes al modelo
	 * @param oldGoal Objetivo viejo
	 * @param newGoal Objetivo nuevo
	 */
	private void onGoalChange(Goal oldGoal, Goal newGoal) {
		if (oldGoal != null) {
			nameText.textProperty().unbindBidirectional(oldGoal.nameProperty());
			descriptionTextArea.textProperty().unbindBidirectional(oldGoal.descriptionProperty());
		}
		if (newGoal != null) {
			nameText.textProperty().bindBidirectional(newGoal.nameProperty());
			descriptionTextArea.textProperty().bindBidirectional(newGoal.descriptionProperty());
		}
	}

	/**
	 * @return Raíz de la vista cargada por el controlador
	 */
	public BorderPane getView() {
		return view;
	}


	public final ObjectProperty<Goal> goalProperty() {
		return this.goal;
	}


	public final Goal getGoal() {
		return this.goalProperty().get();
	}

	
	public final void setGoal(final Goal goal) {
		this.goalProperty().set(goal);
	}

}
