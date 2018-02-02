package fvarrui.sysadmin.editor.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;

public class TestViewController implements Initializable {

    @FXML
    private BorderPane view;

	public TestViewController() throws IOException {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fvarrui/sysadmin/editor/ui/views/TestView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public BorderPane getView() {
		return view;
	}
}
