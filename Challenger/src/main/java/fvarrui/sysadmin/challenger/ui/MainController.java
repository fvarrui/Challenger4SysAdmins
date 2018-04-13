package fvarrui.sysadmin.challenger.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import fvarrui.sysadmin.challenger.model.Challenge;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

public class MainController implements Initializable {
	
	// model
	
	private ObjectProperty<Challenge> challenge = new SimpleObjectProperty<>(this, "challenge");
	
	// view
	
	@FXML
	private BorderPane view;
	
	public MainController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		challenge.addListener((o, ov, nv) -> onChallengeChanged(ov, nv));
		challenge.set(TestData.getChallenge());
	}

	private void onChallengeChanged(Challenge ov, Challenge nv) {
	}

	public BorderPane getView() {
		return view;
	}
	
}
