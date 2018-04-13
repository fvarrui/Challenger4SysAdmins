package fvarrui.sysadmin.challenger.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChallengerApp extends Application {
	
	private MainController mainController;

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		mainController = new MainController();
		
		primaryStage.setTitle("Challenger");
		primaryStage.setScene(new Scene(mainController.getView(), 640, 480));
		primaryStage.show();
		
	}

	public static void main(String[] args) {
		launch(args);
	}

}
