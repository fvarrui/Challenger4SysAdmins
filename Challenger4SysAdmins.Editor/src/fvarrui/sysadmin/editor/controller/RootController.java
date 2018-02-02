package fvarrui.sysadmin.editor.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

public class RootController implements Initializable {

	@FXML
	private BorderPane view;


	private EditorTreeViewController editorTreeViewController;
	private TestViewController testViewController;
	private GoalsViewController goalsViewController; 


	public RootController() throws IOException {


		editorTreeViewController=new EditorTreeViewController(this);
		testViewController=new TestViewController();
		goalsViewController=new GoalsViewController();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fvarrui/sysadmin/editor/ui/views/RootView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		view.setLeft(editorTreeViewController.getView());
//		view.setCenter(testViewController.getView());
//		view.setCenter(goalsViewController.getView());
		
	}

	public BorderPane getView() {
		return view;
	}

	public EditorTreeViewController getEditorTreeViewController() {
		return editorTreeViewController;
	}

	public TestViewController getTestViewController() {
		return testViewController;
	}

	public GoalsViewController getGoalsViewController() {
		return goalsViewController;
	}
}
