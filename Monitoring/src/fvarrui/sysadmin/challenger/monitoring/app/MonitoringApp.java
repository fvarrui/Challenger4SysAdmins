package fvarrui.sysadmin.challenger.monitoring.app;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang.SystemUtils;
import org.controlsfx.control.Notifications;

import fvarrui.sysadmin.challenger.monitoring.BASHMonitor;
import fvarrui.sysadmin.challenger.monitoring.Monitor;
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
	private Monitor monitor;

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

        if (SystemUtils.IS_OS_WINDOWS) {
        	monitor = new PSMonitor();
        } else if (SystemUtils.IS_OS_LINUX) {
        	monitor = new BASHMonitor();        	
        } else {
        	System.err.println("Sistema operativo no soportado");
        	Platform.exit();
        }
        
		monitor.addListener(data -> {
			String cmd = (String) data.get(PSMonitor.COMMAND);
			String user = (String) data.get(PSMonitor.USERNAME);
			LocalDateTime time = (LocalDateTime) data.get(PSMonitor.TIMESTAMP);
			
			System.out.println("---> " + cmd);
			Platform.runLater(() -> 
					Notifications
						.create()
						.title("PowerShell")
						.text(
							"Hora: " + time.format(formatter) + "\n" +
							"Usuario: " + user + "\n" +
							"Comando: " + (cmd.length() > 50 ? cmd.substring(0, 50) + "..." : cmd)
						)
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
