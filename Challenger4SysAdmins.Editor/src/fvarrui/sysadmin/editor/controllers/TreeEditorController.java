package fvarrui.sysadmin.editor.controllers;

import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;

import fvarrui.sysadmin.challenger.Challenge;
import fvarrui.sysadmin.challenger.Goal;
import fvarrui.sysadmin.challenger.test.CommandTest;
import fvarrui.sysadmin.challenger.test.Test;
import fvarrui.sysadmin.editor.components.CustomTreeCell;
import fvarrui.sysadmin.editor.components.MenuBuilder;
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

/**
 * Logica de negocio para gestionar el arbol del retos
 * 
 * @author Ricardo Vargas
 * @version 1.0
 */

public class TreeEditorController implements Initializable {

	private ObjectProperty<TreeItem<Object>> selectedItem = new SimpleObjectProperty<>(this, "selectedItem");
	private ObjectProperty<Object> seleccionado = new SimpleObjectProperty<>(this, "seleccionado");
	private ObjectProperty<Challenge> challenge = new SimpleObjectProperty<>(this, "challenge");

	@FXML
	private TitledPane view;

	@FXML
	private TreeView<Object> treeView;

	@FXML
	private Button addButton, deleteButton;

	/**
	 * Constructor del controlador
	 * 
	 * @throws IOException
	 *             si no consigue cargar la vista.
	 * 
	 */
	public TreeEditorController() throws IOException {
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/fvarrui/sysadmin/editor/ui/views/TreeEditorView.fxml"));
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

			treeView.setContextMenu(MenuBuilder.getMenu(selectedItem.get()));
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

	/**
	 * Dispara el evento para añadir nuevos elementos al arbol.
	 * 
	 * @param event
	 *            del boton
	 * 
	 */
	@FXML
	void addButtonAction(ActionEvent event) {

		Object item = seleccionado.get();

		if (item instanceof Challenge) {
			Challenge challenge = (Challenge) item;
			challenge.getGoals().add(new Goal("Nuevo objetivo"));
		} else if (item instanceof Goal) {
			Goal goal = (Goal) item;
			goal.setTest(new CommandTest("Nuevo test de comando", null));
		}

	}

	/**
	 * Dispara el evento para eliminar elemento del arbol
	 * 
	 * @param event
	 *            del boton
	 */
	@FXML
	void deleteButtonAction(ActionEvent event) {

		Object item = seleccionado.get();

		if (item instanceof Goal) {
			Goal goal = (Goal) item;
			challenge.get().getGoals().remove(goal);

		}

		if (item instanceof Test) {

			TreeItem<Object> selected = (TreeItem<Object>) treeView.getSelectionModel().getSelectedItem();
			selectedItem.get().getParent().getChildren().remove(selected);

		}

	}

	/**
	 * 
	 * @return La vista cargada por el controlador
	 */
	public TitledPane getView() {
		return view;
	}

	/**
	 * 
	 * @return Property del reto
	 */
	public final ObjectProperty<Challenge> challengeProperty() {
		return this.challenge;
	}

	/**
	 * 
	 * @return El challenge
	 */
	public final Challenge getChallenge() {
		return this.challengeProperty().get();
	}

	/**
	 * 
	 * @param Nuevo
	 *            Challenge a establecer
	 */
	public final void setChallenge(final Challenge challenge) {
		this.challengeProperty().set(challenge);
	}

	/**
	 * 
	 * @return Property del objeto selecionado
	 */
	public final ObjectProperty<Object> seleccionadoProperty() {
		return this.seleccionado;
	}

	/**
	 * 
	 * @return Objeto selecionado
	 */
	public final Object getSeleccionado() {
		return this.seleccionadoProperty().get();
	}

	/**
	 * 
	 * @return Setea un nuevo selecionado
	 */
	public final void setSeleccionado(final Object seleccionado) {
		this.seleccionadoProperty().set(seleccionado);
	}

	public TreeView<Object> getTreeView() {
		return treeView;
	}
}
