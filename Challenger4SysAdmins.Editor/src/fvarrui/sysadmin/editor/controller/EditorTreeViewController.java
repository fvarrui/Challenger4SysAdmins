package fvarrui.sysadmin.editor.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EditorTreeViewController implements Initializable {

	@FXML
	private TitledPane view;

	@FXML
	private TreeView<String> treeView;

	@FXML
	private ImageView img;

	private RootController rootController;

	public EditorTreeViewController(RootController rootController) throws IOException {

		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/fvarrui/sysadmin/editor/ui/views/EditorTreeView.fxml"));
		loader.setController(this);
		loader.load();

		this.rootController = rootController;

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		TreeItem<String> root = new TreeItem<>("Challenge", new ImageView(new Image(
				getClass().getResource("/fvarrui/sysadmin/editor/ui/resources/treeIcon.png").toExternalForm())));
		root.setExpanded(true);

		TreeItem<String> goalA = new TreeItem<>("goalA");
		TreeItem<String> comandTest = new TreeItem<>("comandTest");
		goalA.getChildren().add(comandTest);

		TreeItem<String> goalB = new TreeItem<>("goalB");
		TreeItem<String> NotTest = new TreeItem<>("NotTest");
		goalB.getChildren().add(NotTest);

		root.getChildren().addAll(goalA, goalB);
		treeView.setRoot(root);

		treeView.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> onSelectionChanged(o, nv));

	}

	private void onSelectionChanged(Observable o, TreeItem<String> nv) {

		if (nv.getParent() != null) {
			if (nv.isLeaf()) {

				rootController.getView().setCenter(rootController.getTestViewController().getView());
			} else {
				rootController.getView().setCenter(rootController.getGoalsViewController().getView());
			}
		}

	}

	public TitledPane getView() {
		return view;
	}
}
