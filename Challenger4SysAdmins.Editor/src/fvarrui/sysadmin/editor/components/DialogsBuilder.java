package fvarrui.sysadmin.editor.components;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Clase Auxiliar para generar cuadros de dialogo.
 * @author Ricardo Vargas
 *
 */
public class DialogsBuilder {

	
	/**
	 * 
	 * @param type tipo de la alerta.
	 * @param stage stage principal.
	 * @param title titulo del dialogo.
	 * @param header cabecera del dialogo.
	 * @param content contenido del dialogo.
	 * @return se pudo mostrar el dialog?
	 */
	public static boolean getDialog(AlertType type, Stage stage, String title, String header, String content) {

		Alert confirm = new Alert(AlertType.INFORMATION);
		confirm.initOwner(stage);
		confirm.initModality(Modality.WINDOW_MODAL);
		confirm.setTitle(title);
		confirm.setHeaderText(header);
		confirm.setContentText(content);
	
		return confirm.showAndWait().get().equals(ButtonType.OK);

	}
}
