package fvarrui.sysadmin.editor.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
<<<<<<< HEAD
=======
import java.util.Optional;
>>>>>>> 2ffaae0cfc64077a7cd02f55dc72a1cfbc9f2f88
import java.util.ResourceBundle;

import fvarrui.sysadmin.challenger.Challenge;
import fvarrui.sysadmin.challenger.Goal;
<<<<<<< HEAD
import fvarrui.sysadmin.challenger.command.ShellCommand;
=======
>>>>>>> 2ffaae0cfc64077a7cd02f55dc72a1cfbc9f2f88
import fvarrui.sysadmin.challenger.test.Test;
import fvarrui.sysadmin.editor.application.EditorApp;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
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

	private TreeEditorController treeEditorController;
	private GoalController goalController;
	private TestController testController;
	private ChallengeController challengeController;
<<<<<<< HEAD
	private ComandController comandController;
=======
>>>>>>> 2ffaae0cfc64077a7cd02f55dc72a1cfbc9f2f88

	private ObjectProperty<Challenge> challenge = new SimpleObjectProperty<>(this, "challenge");
	private ObjectProperty<Object> seleccionado = new SimpleObjectProperty<>(this, "seleccionado");

	@FXML
	private BorderPane view;

	@FXML
	private BorderPane emptyView;
<<<<<<< HEAD

	@FXML
	private Pane aboutView;

	@FXML
	private Hyperlink ricardoLink;

	@FXML
	private Hyperlink franLink;
=======
>>>>>>> 2ffaae0cfc64077a7cd02f55dc72a1cfbc9f2f88

	/**
	 * Constructor del controlador raiz
	 * 
	 * @throws IOException
	 *             En caso de no poder cargar la vista.
	 */
	public RootController() throws IOException {
		comandController = new ComandController();
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

		view.setLeft(treeEditorController.getView());
		view.setCenter(emptyView);

		seleccionado.addListener((o, ov, nv) -> onSeleccionadoChanged(o, ov, nv));

		challenge.bindBidirectional(treeEditorController.challengeProperty());
		seleccionado.bind(treeEditorController.seleccionadoProperty());

		challenge.set(new Challenge("Nuevo reto"));

	}

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

		view.setCenter(emptyView);
<<<<<<< HEAD

=======
		
>>>>>>> 2ffaae0cfc64077a7cd02f55dc72a1cfbc9f2f88
		if (nv instanceof Goal) {
			Goal goal = (Goal) nv;
			goalController.goalProperty().bind(new SimpleObjectProperty<Goal>(goal));
			view.setCenter(goalController.getView());

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
<<<<<<< HEAD
		}

		if (nv instanceof Challenge) {
			Challenge challenge = (Challenge) nv;
			challengeController.challengeProperty().bind(new SimpleObjectProperty<>(challenge));
			view.setCenter(challengeController.getView());
		}
		if (ov instanceof Challenge) {
			challengeController.challengeProperty().unbind();
		}

		if (nv instanceof ShellCommand) {
			ShellCommand comand = (ShellCommand) nv;
			comandController.shellCommandProperty().bind(new SimpleObjectProperty<>(comand));
			view.setCenter(comandController.getView());
		}
		if (ov instanceof ShellCommand) {
			comandController.shellCommandProperty().unbind();
=======
>>>>>>> 2ffaae0cfc64077a7cd02f55dc72a1cfbc9f2f88
		}

		if (nv instanceof Challenge) {
			Challenge challenge = (Challenge) nv;
			challengeController.challengeProperty().bind(new SimpleObjectProperty<>(challenge));
			view.setCenter(challengeController.getView());
		}
		if (ov instanceof Challenge) {
			challengeController.challengeProperty().unbind();
		}
		
	}

	/**
	 * Dispara el evento 'About' de la barra de menu
	 * 
	 * @param evento
	 *            del boton
	 * @throws IOException
	 */
	@FXML
	void aboutButtonAction(ActionEvent event) throws IOException {

	

		aboutView = FXMLLoader.load(getClass().getResource("/fvarrui/sysadmin/editor/ui/views/AboutView.fxml"));

		Stage stage = new Stage();
		stage.setResizable(false);
		stage.setTitle("About");
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.getIcons().addAll(EditorApp.getPrimaryStage().getIcons());
		stage.initOwner(EditorApp.getPrimaryStage());
		stage.setScene(new Scene(aboutView));
		stage.show();
		
		
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

		Alert confirmacion = new Alert(AlertType.WARNING);
		confirmacion.initOwner(EditorApp.getPrimaryStage());
		confirmacion.setTitle("Nuevo Challenge");
		confirmacion.initModality(Modality.APPLICATION_MODAL);
		confirmacion.setHeaderText("Se dispone a crear un nuevo challenge.\nSi tiene información sin guardar se perderá para siempre.");
		confirmacion.setContentText("¿Seguro que desea continuar?");
		confirmacion.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
		if (confirmacion.showAndWait().get().equals(ButtonType.YES)) {
		
			challenge.set(new Challenge());
		}
	}

	@FXML
	void openButtonAction(ActionEvent event) {

		
		try {
			// abre el diálogo para abrir un fichero
			FileChooser abrirDialog = new FileChooser();
			abrirDialog.setInitialDirectory(new File("."));
			abrirDialog.getExtensionFilters().add(new ExtensionFilter("Historial de comandos (*.challenge)", "*.challenge"));
			File fichero = abrirDialog.showOpenDialog(EditorApp.getPrimaryStage());
			

			if (fichero != null) {
				
				challenge.set(Challenge.load(fichero));
				
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		
			Alert error = new Alert(AlertType.ERROR);
			error.initOwner(EditorApp.getPrimaryStage());
			error.setTitle("Abrir Challenge");
			error.initModality(Modality.APPLICATION_MODAL);
			error.setHeaderText("Error al abrir un challenge.");
			error.setContentText(e1.getMessage());
			error.showAndWait();
		}
	}

	@FXML
	void saveButtonAction(ActionEvent event) {

		try {
			// abre el diálogo para guardar un fichero
			FileChooser guardarDialog = new FileChooser();
			guardarDialog.setInitialDirectory(new File("."));
			guardarDialog.getExtensionFilters().add(new ExtensionFilter("Challenge (*.challenge)", "*.challenge"));
			File fichero = guardarDialog.showSaveDialog(EditorApp.getPrimaryStage());
			// comprueba si se seleccionó un fichero en el diálogo (File) o se canceló (null)			
			if (fichero != null) {
				// se guarda el historial en el fichero indicado
				challenge.get().save(fichero);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			// muestra un diálogo con el error			
			Alert error = new Alert(AlertType.ERROR);
			error.initOwner(EditorApp.getPrimaryStage());
			error.initModality(Modality.APPLICATION_MODAL);
			error.setTitle("Guardar challenge");
			error.setHeaderText("Error al guardar un challenge.");
			error.setContentText(e1.getMessage());
			error.showAndWait();
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
