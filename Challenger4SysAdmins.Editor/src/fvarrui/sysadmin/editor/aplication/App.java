package fvarrui.sysadmin.editor.aplication;

import fvarrui.sysadmin.editor.controller.RootController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

	private Stage stage;
	private Scene escena;
	private RootController rootController;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		stage = primaryStage;
		stage.setTitle("Challenger");
		rootController=new RootController();
		stage.getIcons().add(new Image(getClass().getResource("/fvarrui/sysadmin/editor/ui/resources/cmd.png").toExternalForm()));
		escena=new Scene(rootController.getView());
		stage.setScene(escena);
		stage.show();
		
		
		
		
		
	}
	
	public static void main(String[] args) {
		
		launch(args);
	}

}
