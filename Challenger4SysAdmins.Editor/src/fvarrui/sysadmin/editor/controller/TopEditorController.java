package fvarrui.sysadmin.editor.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import fvarrui.sysadmin.editor.datamodel.TopEditorModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

/**
 * @author Ricardo Vargas
 */

public class TopEditorController implements Initializable {

	@FXML
	private BorderPane view;

	@FXML
	private TextField nameChallengeText;

	@FXML
	private TextArea descriptionChallengeText;

	private ObjectProperty<TopEditorModel> topEditorModel = new SimpleObjectProperty<>(this, "topEditorModel");

	public TopEditorController() throws IOException {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fvarrui/sysadmin/editor/ui/views/TopEditorView.fxml"));
		loader.setController(this);
		loader.load();
		
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		topEditorModel.set(new TopEditorModel());
		
		nameChallengeText.textProperty().bindBidirectional(topEditorModel.get().nameProperty());

		
	}
	
	
	
	public BorderPane getView() {
		return view;
	}

	public final ObjectProperty<TopEditorModel> topEditorModelProperty() {
		return this.topEditorModel;
	}
	

	public final TopEditorModel getTopEditorModel() {
		return this.topEditorModelProperty().get();
	}
	

	public final void setTopEditorModel(final TopEditorModel topEditorModel) {
		this.topEditorModelProperty().set(topEditorModel);
	}

}
