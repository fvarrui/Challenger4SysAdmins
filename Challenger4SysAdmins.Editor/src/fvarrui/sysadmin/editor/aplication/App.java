package fvarrui.sysadmin.editor.aplication;

import fvarrui.sysadmin.editor.controller.RootController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

	private static Stage primaryStage;
	private Scene escena;
	private RootController rootController;

	@Override
	public void start(Stage primaryStage) throws Exception {

		App.primaryStage = primaryStage;
		primaryStage.setTitle("Challenger");
		rootController = new RootController();
		primaryStage.getIcons().add(new Image(
				getClass().getResource("/fvarrui/sysadmin/editor/ui/resources/mainx128.png").toExternalForm()));
		escena = new Scene(rootController.getView());
		primaryStage.setScene(escena);
		primaryStage.show();

	}

	public static void main(String[] args) {

		launch(args);
	}

	public static Stage getPrimaryStage() {
		return primaryStage;
	}
}
