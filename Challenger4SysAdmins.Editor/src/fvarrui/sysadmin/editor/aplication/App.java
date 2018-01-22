package fvarrui.sysadmin.editor.aplication;

import fvarrui.sysadmin.editor.controller.RootEditorController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * @author Ricardo Vargas
 */

public class App extends Application {

	
	private static Stage primaryStage;
	private RootEditorController rootEditorController;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		App.primaryStage = primaryStage;
		rootEditorController=new RootEditorController();
		
		App.primaryStage.setTitle("Challenger Editor");
		App.primaryStage.setScene(new Scene(rootEditorController.getRootPane()));
		App.primaryStage.getIcons().add(new Image(getClass().getResource("/fvarrui/sysadmin/editor/ui/resources/appIcon.png").toExternalForm()));
		App.primaryStage.show();
		
	}

	public static void main(String[] args) {
		
		launch(args);
	}
}
