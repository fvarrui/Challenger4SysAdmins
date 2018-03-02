package fvarrui.sysadmin.editor.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import fvarrui.sysadmin.challenger.Goal;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

<<<<<<< HEAD
public class GoalController implements Initializable {

	// model

	private ObjectProperty<Goal> goal = new SimpleObjectProperty<>(this, "goal");

	// view
=======


/**
 * Logica de negocio para gestionar los objetivos del reto.
 * @author Fran Vargas,Ricardo Vargas.
 * @version 1.0
 *
 */

public class GoalController implements Initializable {

	private ObjectProperty<Goal> goal = new SimpleObjectProperty<>(this, "goal");
>>>>>>> 7afdb606eb5ff55c3880a5f4dbdef57dacedc15a

	@FXML
	private BorderPane view;

	@FXML
	private TextArea descriptionTextArea;

	@FXML
	private TextField nameText;

<<<<<<< HEAD
=======
	
	/**
	 * Constructor de los retos
	 * @throws IOException si no pudo cargar la vista
	 */
>>>>>>> 7afdb606eb5ff55c3880a5f4dbdef57dacedc15a
	public GoalController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fvarrui/sysadmin/editor/ui/views/GoalView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		goal.addListener((o, ov, nv) -> onGoalChange(ov, nv));

	}

<<<<<<< HEAD
=======
	/**
	 * Listener en el modelo para bindear y desbindear los componenetes al modelo
	 * @param oldGoal modelo viejo
	 * @param newGoal modelo nuevo
	 */
>>>>>>> 7afdb606eb5ff55c3880a5f4dbdef57dacedc15a
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

<<<<<<< HEAD
=======
	/**
	 * 
	 * @return La vista cargada por el controlador
	 */
>>>>>>> 7afdb606eb5ff55c3880a5f4dbdef57dacedc15a
	public BorderPane getView() {
		return view;
	}

<<<<<<< HEAD
=======
	/**
	 * 
	 * @return Property del goal
	 */
>>>>>>> 7afdb606eb5ff55c3880a5f4dbdef57dacedc15a
	public final ObjectProperty<Goal> goalProperty() {
		return this.goal;
	}

<<<<<<< HEAD
=======
	/**
	 * 
	 * @return  Objeto Goal encapsulado
	 */
>>>>>>> 7afdb606eb5ff55c3880a5f4dbdef57dacedc15a
	public final Goal getGoal() {
		return this.goalProperty().get();
	}

<<<<<<< HEAD
=======
	/**
	 * 
	 * @return Setea un nuevo objeto goal
	 */
>>>>>>> 7afdb606eb5ff55c3880a5f4dbdef57dacedc15a
	public final void setGoal(final Goal goal) {
		this.goalProperty().set(goal);
	}

}
