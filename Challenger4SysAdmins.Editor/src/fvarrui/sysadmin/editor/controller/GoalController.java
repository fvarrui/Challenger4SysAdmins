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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class GoalController implements Initializable {

	@FXML
	private BorderPane view;

	@FXML
	private TextArea descriptionTextArea;

	@FXML
	private TextField nameText;

    private Goal actual,devuelto;
    
    private ObjectProperty<Challenge> challenge=new SimpleObjectProperty<>(this,"challenge");
  


    public GoalController() throws IOException {

    	actual=new Goal();
  
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/fvarrui/sysadmin/editor/ui/views/GoalsDetailView.fxml"));
		loader.setController(this);
		loader.load();

		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		nameText.textProperty().bindBidirectional(actual.nameProperty());
		descriptionTextArea.textProperty().bindBidirectional(actual.descriptionProperty());
	
		
	}



	public Goal GetGoal() {

	
		devuelto=new Goal();
		devuelto.setName(actual.getName());
		devuelto.setDescription(actual.getDescription());
	 System.out.println(devuelto.getName()+"----------Null------");
	 return devuelto;
	
	}

	public BorderPane getView() {
		return view;
	}

	public ObjectProperty<Challenge> challengeProperty() {
		return this.challenge;
	}
	

	public Challenge getChallenge() {
		return this.challengeProperty().get();
	}
	

	public void setChallenge(final Challenge challenge) {
		this.challengeProperty().set(challenge);
	}
	

	

}
