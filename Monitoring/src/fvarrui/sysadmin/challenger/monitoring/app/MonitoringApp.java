package fvarrui.sysadmin.challenger.monitoring.app;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import org.controlsfx.control.Notifications;

import fvarrui.sysadmin.challenger.monitoring.PSMonitor;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MonitoringApp extends Application {

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	private PSMonitor monitor = new PSMonitor();

	@Override
	public void start(Stage primaryStage) throws Exception {
		
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: TRANSPARENT");
        
        Scene scene = new Scene(root, 0, 0);
        scene.setFill(Color.TRANSPARENT);
        
		primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.setWidth(0);
        primaryStage.setHeight(0);
        primaryStage.toBack();
        primaryStage.show();

		monitor.addListener((cmd,time) -> {
			System.out.println("---> " + cmd);
			Platform.runLater(() -> 
					Notifications
						.create()
						.title("PowerShell")
						.text("Hora: " + time.format(formatter) + "\nComando: " + cmd)
						.showInformation()
				);
		});
		monitor.start();
	}
	
	@Override
	public void stop() throws Exception {
		monitor.requestStop();
		super.stop();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
