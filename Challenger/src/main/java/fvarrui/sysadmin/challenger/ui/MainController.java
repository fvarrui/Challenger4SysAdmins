package fvarrui.sysadmin.challenger.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXListView;

import fvarrui.sysadmin.challenger.common.ui.markdown.MarkdownView;
import fvarrui.sysadmin.challenger.model.Challenge;
import fvarrui.sysadmin.challenger.model.Goal;
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
	private MarkdownView challengeView, goalView;
	
	@FXML
	private JFXListView<Goal> goalsList;
	
	@FXML
	private BorderPane root;
	
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
		challengeView.markdownProperty().bind(nv.descriptionProperty());
		goalsList.itemsProperty().bind(nv.goalsProperty());
		
	}

	public BorderPane getRoot() {
		return root;
	}
	
}
