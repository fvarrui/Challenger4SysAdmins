package fvarrui.sysadmin.editor.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import fvarrui.sysadmin.challenger.Challenge;
import fvarrui.sysadmin.challenger.Goal;
import fvarrui.sysadmin.challenger.command.ShellCommand;
import fvarrui.sysadmin.challenger.test.Test;
import fvarrui.sysadmin.editor.application.EditorApp;
import fvarrui.sysadmin.editor.components.DialogBuilder;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Gestiona la logica de negocio principal como contenedor raiz de los
 * subcontroladores
 * 
 * @author Fran Vargas,Ricardo Vargas
 * @version 1.0
 *
 */
public class RootController implements Initializable {

	// sub-controllers
	
	private TreeEditorController treeEditorController;
	private GoalController goalController;
	private TestController testController;
	private ChallengeController challengeController;
	private CommandController comandController;

	// model
	
	private ObjectProperty<Challenge> challenge = new SimpleObjectProperty<>(this, "challenge");
	private ObjectProperty<Object> seleccionado = new SimpleObjectProperty<>(this, "seleccionado");

	// view
	
	private FileChooser fileDialog;
	
	@FXML
	private BorderPane view;

	@FXML
	private BorderPane leftPane, centerPane;

	@FXML
	private BorderPane emptyView;

	/**
	 * Constructor del controlador raiz
	 * 
	 * @throws IOException En caso de no poder cargar la vista.
	 *            
	 */
	public RootController() throws IOException {
		comandController = new CommandController();
		treeEditorController = new TreeEditorController();
		goalController = new GoalController();
		testController = new TestController();
		challengeController = new ChallengeController();

		emptyView = FXMLLoader.load(getClass().getResource("/fvarrui/sysadmin/editor/ui/views/EmptyView.fxml"));

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fvarrui/sysadmin/editor/ui/views/RootView.fxml"));
		loader.setController(this);
		loader.load();

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		leftPane.setCenter(treeEditorController.getView());
		centerPane.setCenter(emptyView);

		seleccionado.addListener((o, ov, nv) -> onSeleccionadoChanged(o, ov, nv));

		challenge.bindBidirectional(treeEditorController.challengeProperty());
		seleccionado.bind(treeEditorController.seleccionadoProperty());

		challenge.set(new Challenge("Nuevo reto"));
		
		fileDialog = new FileChooser();
		fileDialog.setInitialDirectory(new File("."));
		fileDialog.getExtensionFilters().add(new ExtensionFilter("Challenge (*.challenge)", "*.challenge"));
		fileDialog.getExtensionFilters().add(new ExtensionFilter("All files (*.challenge)", "*.challenge"));

	}

	/**
	 * Listener para ser notificado de cambios y bindear el modelo.
	 * @param o valor observable
	 * @param ov viejo goal
	 * @param nv goal nuevo
	 * @throws IOException si no puede cargar la vista
	 */
	private void onSeleccionadoChanged(ObservableValue<? extends Object> o, Object ov, Object nv) {

		centerPane.setCenter(emptyView);

		// desbindea del panel el elemento anterior
		if (ov instanceof Challenge) {
			challengeController.challengeProperty().unbind();
		} else if (ov instanceof Goal) {
			goalController.goalProperty().unbind();
		} else if (ov instanceof Test) {
			testController.testProperty().unbind();
		} else if (ov instanceof ShellCommand) {
			comandController.shellCommandProperty().unbind();
		}

		// bindea el elemento al panel adecuado y lo muestra
		if (nv instanceof Challenge) {
			Challenge challenge = (Challenge) nv;
			challengeController.challengeProperty().bind(new SimpleObjectProperty<>(challenge));
			centerPane.setCenter(challengeController.getView());
		} else if (nv instanceof Goal) {
			Goal goal = (Goal) nv;
			goalController.goalProperty().bind(new SimpleObjectProperty<Goal>(goal));
			centerPane.setCenter(goalController.getView());
		} else if (nv instanceof Test) {
			Test test = (Test) nv;
			testController.testProperty().bind(new SimpleObjectProperty<>(test));
			centerPane.setCenter(testController.getView());
		} else if (nv instanceof ShellCommand) {
			ShellCommand comand = (ShellCommand) nv;
			comandController.shellCommandProperty().bind(new SimpleObjectProperty<>(comand));
			centerPane.setCenter(comandController.getView());
		}

	}

	/**
	 * Dispara el evento 'About' de la barra de menu
	 * 
	 * @param evento del boton
	 *         
	 * @throws IOException
	 */
	@FXML
	void aboutButtonAction(ActionEvent event) throws IOException {

	
		Stage stage = new Stage();
		stage.setResizable(false);
		stage.setTitle("About");
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.getIcons().addAll(EditorApp.getPrimaryStage().getIcons());
		stage.initOwner(EditorApp.getPrimaryStage());
		stage.setScene(new Scene(new AboutController().getView()));
		stage.showAndWait();
		
	}

	/**
	 * Dispara el evento 'Exit' y cierra la aplicacion
	 * 
	 * @param evento  del boton
	 *           
	 */
	@FXML
	void exitButtonAction(ActionEvent event) {

		EditorApp.getPrimaryStage().close();

	}

	/**
	 * Dispara el evento 'New' y cierra la aplicacion
	 * 
	 * @param evento  del boton
	 *           
	 */
	@FXML
	void newButtonAction(ActionEvent event) {
		if (DialogBuilder.confirm("New Challenge", "You are going to create a new challenge.\nUnsaved information will be discarded.", "Are you sure?")) {
			challenge.set(new Challenge());
		}
	}

	/**
	 * Dispara el evento 'Open' y cierra la aplicacion
	 * 
	 * @param evento  del boton
	 *           
	 */
	@FXML
	void openButtonAction(ActionEvent event) {
		try {
			File fichero = fileDialog.showOpenDialog(EditorApp.getPrimaryStage());
			if (fichero != null) {
				challenge.set(Challenge.load(fichero));
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			DialogBuilder.error("Open Challenge", "Error ocurred while opening challenge", e1);
		}
	}

	/**
	 * Dispara el evento 'Guardar' y cierra la aplicacion
	 * 
	 * @param evento  del boton
	 *           
	 */
	@FXML
	void saveButtonAction(ActionEvent event) {
		try {
			File fichero = fileDialog.showSaveDialog(EditorApp.getPrimaryStage());
			if (fichero != null) {
				challenge.get().save(fichero);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			DialogBuilder.error("Save Challenge", "Error ocurred while saving challenge", e1);
		}
	}

	/**
	 * 
	 * @return la vista cargada por el controlador
	 */
	public BorderPane getView() {
		return view;
	}

}
