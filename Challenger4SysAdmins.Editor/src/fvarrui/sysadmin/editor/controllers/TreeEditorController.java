package fvarrui.sysadmin.editor.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import fvarrui.sysadmin.challenger.Challenge;
import fvarrui.sysadmin.challenger.Goal;
import fvarrui.sysadmin.challenger.command.Command;
import fvarrui.sysadmin.challenger.command.ShellCommand;
import fvarrui.sysadmin.challenger.test.CommandTest;
import fvarrui.sysadmin.challenger.test.CompoundTest;
import fvarrui.sysadmin.challenger.test.NotTest;
import fvarrui.sysadmin.challenger.test.Test;
import fvarrui.sysadmin.editor.components.TestContextMenu;
import fvarrui.sysadmin.editor.components.tree.ChallengeTreeItem;
import fvarrui.sysadmin.editor.components.tree.CustomTreeCell;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.util.Callback;

/**
 * Controlador que gestiona el árbol del retos
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
				treeView.setRoot(new ChallengeTreeItem(nv));
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

	/**
	 * Dispara el evento para añadir nuevos elementos al arbol.
	 * 
	 * @param event  del boton
	 *   
	 */
	@FXML
	void addButtonAction(ActionEvent event) {

		Object item = seleccionado.get();

		if (item instanceof Challenge) {
			
			Goal goal = new Goal("Nuevo objetivo");
			Challenge challenge = (Challenge) item;
			challenge.getGoals().add(new Goal("Nuevo objetivo"));
			System.out.println("objetivo " + goal.getName() + " añadido al reto " + challenge.getName());
			
		} else if (item instanceof Goal) {

			Goal goal = (Goal) item;
			TestContextMenu menu = new TestContextMenu();
			menu.show(addButton, Side.BOTTOM, 0, 0);
			menu.setOnHidden(e -> {
				Test test = menu.getTest();
				goal.setTest(test);
				System.out.println("test " + test.getName() + " añadido al objetivo " + goal.getName());
			});
			
		} else if (item instanceof CompoundTest) {

			CompoundTest test = (CompoundTest) item;
			TestContextMenu menu = new TestContextMenu();
			menu.show(addButton, Side.BOTTOM, 0, 0);
			menu.setOnHidden(e -> {
				Test childTest = menu.getTest();
				test.getTests().add(childTest);
				System.out.println("test " + childTest.getName() + " añadido al test compuesto " + test.getName());
			});

		} else if (item instanceof NotTest) {

			NotTest test = (NotTest) item;
			TestContextMenu menu = new TestContextMenu();
			menu.show(addButton, Side.BOTTOM, 0, 0);
			menu.setOnHidden(e -> {
				Test childTest = menu.getTest();
				test.setTest(childTest);
				System.out.println("test " + childTest.getName() + " añadido al test de negación " + test.getName());
			});
			
		} else if (item instanceof CommandTest) {

			Command command = new ShellCommand("shell", "command");
			CommandTest test = (CommandTest) item;
			test.setCommand(command);
			System.out.println("comando " + command.getCommand() + " añadido al test de comando " + test.getName());
			
		}

		
	}

	/**
	 * Dispara el evento para eliminar elemento del arbol
	 * 
	 * @param event del boton
	 *            
	 */
	@FXML
	void deleteButtonAction(ActionEvent event) {

		Object item = seleccionado.get();

		if (item instanceof Goal) {
			
			Goal goal = (Goal) item;
			challenge.get().getGoals().remove(goal);
			System.out.println("objetivo " + goal.getName() + " eliminado del reto " + challenge.getName());

		} else if (item instanceof Test) {

			Test test = (Test) item;
			Object parent = selectedItem.get().getParent().getValue();
			if (parent instanceof Goal) {
				Goal goal = (Goal) parent;
				goal.setTest(null);
				System.out.println("test " + test.getName() + " eliminado del objetivo " + goal.getName());
			} else if (parent instanceof CompoundTest) {
				CompoundTest compoundTest = (CompoundTest) parent;
				compoundTest.getTests().remove(item);
				System.out.println("test " + test.getName() + " eliminado del test " + compoundTest.getName());
			} else if (parent instanceof NotTest) {
				NotTest notTest = (NotTest) parent;
				notTest.setTest(null);
				System.out.println("test " + test.getName() + " eliminado del test de negación " + notTest.getName());
			} 
			
		} else if (item instanceof Command) {
			
			Command command = (Command) item;
			CommandTest parent = (CommandTest) selectedItem.get().getParent().getValue();
			parent.setCommand(null);
			System.out.println("comando " + command.getCommand() + " eliminado del test " + parent.getName());
			
		}

	}

	/**
	 * 
	 * @return La vista cargada por el controlador
	 */
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
