package fvarrui.sysadmin.editor.controllers;

import java.io.IOException;
import java.net.URL;
<<<<<<< HEAD
=======
import java.util.Optional;
>>>>>>> 7afdb606eb5ff55c3880a5f4dbdef57dacedc15a
import java.util.ResourceBundle;

import fvarrui.sysadmin.challenger.Challenge;
import fvarrui.sysadmin.challenger.Goal;
<<<<<<< HEAD
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
=======
import fvarrui.sysadmin.challenger.test.Test;
import fvarrui.sysadmin.editor.application.EditorApp;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;

/**
 * Gestiona la logica de negocio principal como contenedor raiz de los
 * subcontroladores
 * 
 * @author Fran Vargas,Ricardo Vargas
 * @version 1.0
 *
 */
public class RootController implements Initializable {

	private TreeEditorController treeEditorController;
	private GoalController goalController;
	private TestController testController;
>>>>>>> 7afdb606eb5ff55c3880a5f4dbdef57dacedc15a

	private ObjectProperty<Challenge> challenge = new SimpleObjectProperty<>(this, "challenge");
	private ObjectProperty<Object> seleccionado = new SimpleObjectProperty<>(this, "seleccionado");

<<<<<<< HEAD
	// view

	@FXML
	private BorderPane view;

	public RootController() throws IOException {
		treeEditorController = new TreeEditorController();
		goalController = new GoalController();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fvarrui/sysadmin/editor/ui/views/RootView.fxml"));
		loader.setController(this);
		loader.load();
=======
	@FXML
	private BorderPane view;

	@FXML
	private BorderPane hand;

	/**
	 * Constructor del controlador raiz
	 * 
	 * @throws IOException
	 *             En caso de no poder cargar la vista.
	 */
	public RootController() throws IOException {
		treeEditorController = new TreeEditorController();
		goalController = new GoalController();
		testController = new TestController();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fvarrui/sysadmin/editor/ui/views/RootView.fxml"));
		loader.setController(this);
		loader.load();

		hand = FXMLLoader.load(getClass().getResource("/fvarrui/sysadmin/editor/ui/views/DefaultView.fxml"));
>>>>>>> 7afdb606eb5ff55c3880a5f4dbdef57dacedc15a
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		view.setLeft(treeEditorController.getView());

<<<<<<< HEAD
//		view.setCenter(goalController.getView());

		// listener en el selected property para cambiar el pane en funcion de el objeto que este selecionado
		challenge.addListener((o, ov, nv) -> onChallengeChanged(o, ov, nv));
		seleccionado.addListener((o, ov, nv) -> onSeleccionadoChanged(o, ov, nv));
		
		challenge.bindBidirectional(treeEditorController.challengeProperty());
		seleccionado.bind(treeEditorController.seleccionadoProperty());
		
=======
		seleccionado.addListener((o, ov, nv) -> onSeleccionadoChanged(o, ov, nv));

		challenge.bindBidirectional(treeEditorController.challengeProperty());
		seleccionado.bind(treeEditorController.seleccionadoProperty());

>>>>>>> 7afdb606eb5ff55c3880a5f4dbdef57dacedc15a
		challenge.set(new Challenge("Nuevo reto"));

	}

<<<<<<< HEAD
	private void  onSeleccionadoChanged(ObservableValue<? extends Object> o, Object ov, Object nv) {
		view.setCenter(null);						
=======
	/**
	 * Listener para ser notificado de cambios y bindear el modelo.
	 * 
	 * @param o
	 *            valor observable
	 * @param ov
	 *            viejo goal
	 * @param nv
	 *            goal nuevo
	 * @throws IOException
	 */
	private void onSeleccionadoChanged(ObservableValue<? extends Object> o, Object ov, Object nv) {

		view.setCenter(hand);
>>>>>>> 7afdb606eb5ff55c3880a5f4dbdef57dacedc15a
		if (nv instanceof Goal) {
			Goal goal = (Goal) nv;
			goalController.goalProperty().bind(new SimpleObjectProperty<Goal>(goal));
			view.setCenter(goalController.getView());
<<<<<<< HEAD
		} 
		if (ov instanceof Goal) {
			goalController.goalProperty().unbind();
		}
	}

	private void onChallengeChanged(Observable o, Challenge ov, Challenge nv) {
	}

=======

		}
		if (ov instanceof Goal) {
			goalController.goalProperty().unbind();
		}

		if (nv instanceof Test) {
			Test test = (Test) nv;
			testController.testProperty().bind(new SimpleObjectProperty<>(test));
			view.setCenter(testController.getView());
		}

		if (ov instanceof Test) {

			testController.testProperty().unbind();

		}

	}

	/**
	 * Dispara el evento 'About' de la barra de menu
	 * 
	 * @param evento
	 *            del boton
	 */
	@FXML
	void aboutButtonAction(ActionEvent event) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Challenger4SysAdmins");
		alert.setHeaderText("2-DAM Modulo DAD");
		alert.initModality(Modality.WINDOW_MODAL);
		alert.initOwner(EditorApp.getPrimaryStage());
		alert.setContentText("Desarollado por Fran Vargas & Ricardo Vargas.");
		alert.showAndWait();
	}

	/**
	 * Dispara el evento 'Exit' y cierra la aplicacion
	 * 
	 * @param evento
	 *            del boton
	 */
	@FXML
	void exitButtonAction(ActionEvent event) {
		
		EditorApp.getPrimaryStage().close();

	}

	@FXML
	void newButtonAction(ActionEvent event) {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Nuevo Challenge");
		alert.setHeaderText(
				"Se va a crear un nuevo challenge FXML.\nLos cambios que haya realizado en el modelo actual se perderán.");
		alert.setContentText("¿Desea continuar?");
		alert.initModality(Modality.WINDOW_MODAL);
		alert.initOwner(EditorApp.getPrimaryStage());

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			// ... user chose OK
		} else {
			// ... user chose CANCEL or closed the dialog
		}

	}

	@FXML
	void openButtonAction(ActionEvent event) {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Abrir challenge");
		alert.setHeaderText(
				"Se va a abrir un challenge FXML desde fichero.\nLos cambios que haya realizado en el modelo actual se perderán.");
		alert.setContentText("¿Desea continuar?");
		alert.initModality(Modality.WINDOW_MODAL);
		alert.initOwner(EditorApp.getPrimaryStage());

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			// ... user chose OK
		} else {
			// ... user chose CANCEL or closed the dialog
		}
	}

	@FXML
	void saveButtonAction(ActionEvent event) {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Guardar challenge");
		alert.setHeaderText(
				"Se va a guardar el challenge a fichero.\nLos cambios que haya realizado en el modelo actual se guardaran.");
		alert.setContentText("¿Desea continuar?");
		alert.initModality(Modality.WINDOW_MODAL);
		alert.initOwner(EditorApp.getPrimaryStage());

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			// ... user chose OK
		} else {
			// ... user chose CANCEL or closed the dialog
		}
	}

	/**
	 * 
	 * @return la vista cargada por el controlador
	 */
>>>>>>> 7afdb606eb5ff55c3880a5f4dbdef57dacedc15a
	public BorderPane getView() {
		return view;
	}

}
