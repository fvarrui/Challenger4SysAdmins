package fvarrui.sysadmin.challenger.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ChallengerApp extends Application {
	
	private static Stage primaryStage;
	private MainController mainController;

	@Override
	public void start(Stage primaryStage) throws Exception {
		ChallengerApp.primaryStage = primaryStage;
		
		mainController = new MainController();
		
		primaryStage.setTitle("Challenger");
		primaryStage.setScene(new Scene(mainController.getRoot(), 640, 480));
		primaryStage.getIcons().add(new Image(getClass().getResource("/images/logo-16x16.png").toExternalForm()));
		primaryStage.getIcons().add(new Image(getClass().getResource("/images/logo-24x24.png").toExternalForm()));
		primaryStage.getIcons().add(new Image(getClass().getResource("/images/logo-32x32.png").toExternalForm()));
		primaryStage.show();
		
	}
	
	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
