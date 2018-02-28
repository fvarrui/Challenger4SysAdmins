package fvarrui.sysadmin.editor.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import fvarrui.sysadmin.challenger.Challenge;
import fvarrui.sysadmin.challenger.Goal;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

public class RootController implements Initializable {

	@FXML
	private BorderPane view;
	
	private TreeViewController treeViewController;
	private GoalController goalController;
	
     private ObjectProperty<Challenge> challenge=new SimpleObjectProperty<>(this,"challenge");

	// ------------------------------//

	

	public RootController() throws IOException {

		treeViewController=new TreeViewController();
		goalController=new GoalController();


		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fvarrui/sysadmin/editor/ui/views/RootView.fxml"));
		loader.setController(this);
		loader.load();

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		
		view.setLeft(treeViewController.getView());
		
		view.setCenter(goalController.getView());
		
		//listener en el selected property para cambiar el pane en funcion de el objeto que este selecionado
		
		challenge.addListener((o, ov, nv) -> onChallengeChanged(o, ov, nv));
		challenge.set(new Challenge());
		goalController.challengeProperty().bind(challenge);
		

	}




	private void onChallengeChanged(Observable o, Challenge ov, Challenge nv) {
		
		//BindingsMenu
	}

	public BorderPane getView() {
		return view;
	}

}
