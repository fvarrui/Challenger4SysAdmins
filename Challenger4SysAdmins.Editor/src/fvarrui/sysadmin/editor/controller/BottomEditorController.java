package fvarrui.sysadmin.editor.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;

/**
 * @author Ricardo Vargas
 */

public class BottomEditorController implements Initializable {
	
	
    @FXML
    private HBox view;

	public BottomEditorController() throws IOException {

		FXMLLoader loader = new FXMLLoader(	getClass().getResource("/fvarrui/sysadmin/editor/ui/views/BottomEditorView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
	
	public HBox getView() {
		return view;
	}
}
