package fvarrui.sysadmin.editor.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import fvarrui.sysadmin.challenger.Challenge;
import fvarrui.sysadmin.challenger.Goal;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

public class RootController implements Initializable {

	// subcontroladores
	
	private TreeEditorController treeEditorController;
	private GoalController goalController;

	// model

	private ObjectProperty<Challenge> challenge = new SimpleObjectProperty<>(this, "challenge");
	private ObjectProperty<Object> seleccionado = new SimpleObjectProperty<>(this, "seleccionado");

	// view

	@FXML
	private BorderPane view;

	public RootController() throws IOException {
		treeEditorController = new TreeEditorController();
		goalController = new GoalController();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fvarrui/sysadmin/editor/ui/views/RootView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		view.setLeft(treeEditorController.getView());

//		view.setCenter(goalController.getView());

		// listener en el selected property para cambiar el pane en funcion de el objeto que este selecionado
		challenge.addListener((o, ov, nv) -> onChallengeChanged(o, ov, nv));
		seleccionado.addListener((o, ov, nv) -> onSeleccionadoChanged(o, ov, nv));
		
		challenge.bindBidirectional(treeEditorController.challengeProperty());
		seleccionado.bind(treeEditorController.seleccionadoProperty());
		
		challenge.set(new Challenge("Nuevo reto"));

	}

	private void  onSeleccionadoChanged(ObservableValue<? extends Object> o, Object ov, Object nv) {
		view.setCenter(null);						
		if (nv instanceof Goal) {
			Goal goal = (Goal) nv;
			goalController.goalProperty().bind(new SimpleObjectProperty<Goal>(goal));
			view.setCenter(goalController.getView());
		} 
		if (ov instanceof Goal) {
			goalController.goalProperty().unbind();
		}
	}

	private void onChallengeChanged(Observable o, Challenge ov, Challenge nv) {
	}

	public BorderPane getView() {
		return view;
	}

}
