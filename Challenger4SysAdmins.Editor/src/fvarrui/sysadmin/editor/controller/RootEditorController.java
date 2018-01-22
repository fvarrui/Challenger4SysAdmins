package fvarrui.sysadmin.editor.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import fvarrui.sysadmin.editor.datamodel.ChallengerEditorModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

/**
 * @author Ricardo Vargas
 */

public class RootEditorController implements Initializable {

	
    @FXML
    private BorderPane rootPane;
    
    
    //Model
    private ObjectProperty<ChallengerEditorModel> rootModel=new SimpleObjectProperty<>(this,"rootModel",new ChallengerEditorModel());
    
    
    //Controllers
	private TopEditorController topEditorController;
	private CenterEditorController centerEditorController;
	private RightEditorController rightEditorController;
	private BottomEditorController bottomEditorController;
	
	public RootEditorController() throws IOException {
		
		topEditorController=new TopEditorController();
		centerEditorController=new CenterEditorController();
		rightEditorController=new RightEditorController();
		bottomEditorController=new BottomEditorController();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fvarrui/sysadmin/editor/ui/views/RootView.fxml"));
		loader.setController(this);
		loader.load();
		
	
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		rootPane.setTop(topEditorController.getView());
		rootPane.setCenter(centerEditorController.getView());
		rootPane.setRight(rightEditorController.getView());
		rootPane.setBottom(bottomEditorController.getView());
		

	   topEditorController.topEditorModelProperty().bind(rootModel.get().topEditorModelProperty());
	   

		
	}
	
	public BorderPane getRootPane() {
		return rootPane;
	}
	
}
