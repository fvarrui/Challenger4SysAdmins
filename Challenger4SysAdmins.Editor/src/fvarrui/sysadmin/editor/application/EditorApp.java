package fvarrui.sysadmin.editor.application;

import fvarrui.sysadmin.editor.controllers.RootController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Clase principal que lanza la aplicacion.
 * @author Ricardo Vargas
 * @version 1.0
 *
 */

public class EditorApp extends Application {

	private static Stage primaryStage;

	private RootController rootController;

	@Override
	public void start(Stage primaryStage) throws Exception {
		EditorApp.primaryStage = primaryStage;

		rootController = new RootController();

		primaryStage.setTitle("Challenge Editor");
		primaryStage.getIcons().add(new Image(getClass().getResource("/fvarrui/sysadmin/editor/ui/resources/logo-32x32.png").toExternalForm()));
		primaryStage.setScene(new Scene(rootController.getView(), 1000, 500));
		primaryStage.show();

	}

	/**
	 * punto de entrada de la aplicacion
	 * @param args argumentos de la aplicacion
	 */
	public static void main(String[] args) {
		launch(args);
	}

	
	/**
	 * Metodo para retornar el Stage principal d ela aplicacion.
	 * @return Devuelve ventana principal
	 */
	public static Stage getPrimaryStage() {
		return primaryStage;
	}
}
