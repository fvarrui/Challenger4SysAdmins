package fvarrui.sysadmin.editor.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import fvarrui.sysadmin.challenger.Goal;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;

/**
 * @author Ricardo Vargas
 */

public class RightEditorController implements Initializable {

	
    @FXML
    private TitledPane view;
    
    @FXML
    private TableView<Goal> GoalsTableView;

    @FXML
    private TableColumn<Goal, String> nameCol,descriptionCol;


	public RightEditorController() throws IOException {
		
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fvarrui/sysadmin/editor/ui/views/RightEditorView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}
	
	public TitledPane getView() {
		return view;
	}
}
