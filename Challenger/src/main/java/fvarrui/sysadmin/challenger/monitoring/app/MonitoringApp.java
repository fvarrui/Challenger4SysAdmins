package fvarrui.sysadmin.challenger.monitoring.app;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.controlsfx.control.Notifications;

import fvarrui.sysadmin.challenger.monitoring.ShellMonitor;
import fvarrui.sysadmin.challenger.monitoring.linux.LinuxShellMonitor;
import fvarrui.sysadmin.challenger.monitoring.windows.WindowsShellMonitor;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MonitoringApp extends Application {

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	private ShellMonitor monitor;

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
        	monitor = new WindowsShellMonitor();
        } else if (SystemUtils.IS_OS_LINUX) {
        	monitor = new LinuxShellMonitor();
        } else {
        	System.err.println("Sistema operativo no soportado");
        	Platform.exit();
        }
        
		monitor.addListener(data -> {
			String cmd = StringUtils.abbreviate("" + data.get(ShellMonitor.COMMAND), 50);
			String user = "" + data.get(ShellMonitor.USERNAME);
			LocalDateTime time = (LocalDateTime) data.get(ShellMonitor.TIMESTAMP);
			String shell = "" + data.get(ShellMonitor.SHELL);
			String pwd = "" + data.get(ShellMonitor.PWD);
			String oldPwd = "" + data.get(ShellMonitor.OLDPWD);
			
			String text = String.format(
					"Shell: %s\n" +
					"Hora: %s\n" +
					"Usuario: %s\n" +
					"PWD actual: %s\n" +
					"PWD anterior: %s\n" +
					"Comando: %s", 
					shell, time.format(formatter), user, pwd, oldPwd, cmd);
			
			System.out.println("---> " + cmd);
			Platform.runLater(() -> 
					Notifications
						.create()
						.title(monitor.getName())
						.text(text)
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
