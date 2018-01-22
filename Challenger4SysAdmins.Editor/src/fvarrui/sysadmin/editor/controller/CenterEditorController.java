package fvarrui.sysadmin.editor.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TitledPane;

/**
 * @author Ricardo Vargas
 */

public class CenterEditorController implements Initializable {

	@FXML
	private TitledPane view;

	public CenterEditorController() throws IOException {

		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/fvarrui/sysadmin/editor/ui/views/CenterEditorView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	public TitledPane getView() {
		return view;
	}

}
