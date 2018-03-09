package fvarrui.sysadmin.editor.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import fvarrui.sysadmin.challenger.Challenge;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

/**
 * Controlador para gestionar el reto.
 * 
 * @author Fran Vargas,Ricardo Vargas.
 * @version 1.0
 *
 */

public class ChallengeController implements Initializable {

	private ObjectProperty<Challenge> challenge = new SimpleObjectProperty<>(this, "challenge");

	@FXML
	private BorderPane view;

	@FXML
	private TextArea descriptionTextArea;

	@FXML
	private TextField nameText;

	/**
	 * Constructor de los retos
	 * 
	 * @throws IOException si no pudo cargar la vista
	 *             
	 */
	public ChallengeController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fvarrui/sysadmin/editor/ui/views/ChallengeView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		challenge.addListener((o, ov, nv) -> onChallengeChange(ov, nv));

	}

	/**
	 * Listener en el modelo para bindear y desbindear los componenetes al modelo
	 * 
	 * @param oldGoal modelo viejo
	 *          
	 * @param newGoal modelo nuevo
	 *           
	 */
	private void onChallengeChange(Challenge oldChallenge, Challenge newChallenge) {
		if (oldChallenge != null) {
			nameText.textProperty().unbindBidirectional(oldChallenge.nameProperty());
			descriptionTextArea.textProperty().unbindBidirectional(oldChallenge.descriptionProperty());
		}
		if (newChallenge != null) {
			nameText.textProperty().bindBidirectional(newChallenge.nameProperty());
			descriptionTextArea.textProperty().bindBidirectional(newChallenge.descriptionProperty());
		}
	}

	/**
	 * 
	 * @return La vista cargada por el controlador
	 */
	public BorderPane getView() {
		return view;
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

}
