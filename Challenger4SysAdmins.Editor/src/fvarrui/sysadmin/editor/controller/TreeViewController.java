package fvarrui.sysadmin.editor.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import fvarrui.sysadmin.challenger.Challenge;
import fvarrui.sysadmin.challenger.Goal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TreeViewController implements Initializable {

	@FXML
	private TitledPane view;

	@FXML
	private TreeView<String> treeView;

	@FXML
	private Button addButton;

	@FXML
	private Button deleteButton;
	
	private TreeItem<String> rootItem;
	


	public TreeViewController() throws IOException {

		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/fvarrui/sysadmin/editor/ui/views/EditorTreeView.fxml"));
		loader.setController(this);
		loader.load();
		
		
		 Challenge root=new Challenge();
		 root.setName("Challenge");

		 rootItem = new TreeItem<String>
		(root.getName(),new ImageView(new Image(getClass()
		.getResourceAsStream("/fvarrui/sysadmin/editor/ui/resources/root.png"))));
		rootItem.setExpanded(true);
		treeView.setRoot(rootItem);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	@FXML
	void addButtonAction(ActionEvent event) throws IOException {
           GoalController controller=new GoalController();
		
             Goal g=controller.GetGoal();
          
              System.out.println(g.getName()+"ricki string");
           
		
		 TreeItem<String> nuevo;
	}

	@FXML
	void deleteButtonAction(ActionEvent event) {

	}

	public TitledPane getView() {
		return view;
	}
}
