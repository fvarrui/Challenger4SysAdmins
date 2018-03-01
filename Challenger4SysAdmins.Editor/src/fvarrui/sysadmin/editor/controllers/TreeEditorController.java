package fvarrui.sysadmin.editor.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import fvarrui.sysadmin.challenger.Challenge;
import fvarrui.sysadmin.challenger.Goal;
import fvarrui.sysadmin.challenger.test.CommandTest;
import fvarrui.sysadmin.editor.components.CustomTreeCell;
import fvarrui.sysadmin.editor.components.TreeItemFactory;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.util.Callback;

public class TreeEditorController implements Initializable {

	// model

	private ObjectProperty<TreeItem<Object>> selectedItem = new SimpleObjectProperty<>(this, "selectedItem");
	private ObjectProperty<Object> seleccionado = new SimpleObjectProperty<>(this, "seleccionado");
	private ObjectProperty<Challenge> challenge = new SimpleObjectProperty<>(this, "challenge");

	// view

	@FXML
	private TitledPane view;

	@FXML
	private TreeView<Object> treeView;

	@FXML
	private Button addButton, deleteButton;

	public TreeEditorController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fvarrui/sysadmin/editor/ui/views/TreeEditorView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		treeView.setCellFactory(new Callback<TreeView<Object>, TreeCell<Object>>() {
			public TreeCell<Object> call(TreeView<Object> param) {
				return new CustomTreeCell();
			}
		});
		
		challenge.addListener((o, ov, nv) -> {
			if (nv != null)
				treeView.setRoot(TreeItemFactory.createChallengeTreeItem(nv));
			else
				treeView.setRoot(null);
		});

		selectedItem.addListener((o, ov, nv) -> {
			if (nv != null) {
				seleccionado.set(nv.getValue());
			} else {
				seleccionado.set(null);
			}
		});

		selectedItem.bind(treeView.getSelectionModel().selectedItemProperty());
		
		addButton.disableProperty().bind(seleccionado.isNull());
		deleteButton.disableProperty().bind(seleccionado.isNull());

	}



	@FXML
	void addButtonAction(ActionEvent event) throws IOException {
		
		Object item = seleccionado.get();
		
		if (item instanceof Challenge) {
			Challenge challenge = (Challenge) item;
			challenge.getGoals().add(new Goal("Nuevo objetivo"));			
		}
		else if (item instanceof Goal) {
			Goal goal = (Goal) item;
			goal.setTest(new CommandTest("Nuevo test de comando", null));			
		}

		
	}

	@FXML
	void deleteButtonAction(ActionEvent event) {

	}

	public TitledPane getView() {
		return view;
	}

	public final ObjectProperty<Challenge> challengeProperty() {
		return this.challenge;
	}

	public final Challenge getChallenge() {
		return this.challengeProperty().get();
	}

	public final void setChallenge(final Challenge challenge) {
		this.challengeProperty().set(challenge);
	}

	public final ObjectProperty<Object> seleccionadoProperty() {
		return this.seleccionado;
	}

	public final Object getSeleccionado() {
		return this.seleccionadoProperty().get();
	}

	public final void setSeleccionado(final Object seleccionado) {
		this.seleccionadoProperty().set(seleccionado);
	}

}
