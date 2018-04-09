package fvarrui.sysadmin.editor.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import fvarrui.sysadmin.challenger.model.Challenge;
import fvarrui.sysadmin.challenger.model.Goal;
import fvarrui.sysadmin.challenger.model.command.Command;
import fvarrui.sysadmin.challenger.model.test.CommandTest;
import fvarrui.sysadmin.challenger.model.test.NotTest;
import fvarrui.sysadmin.challenger.model.test.Test;
import fvarrui.sysadmin.challenger.model.test.TestGroup;
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
		Object parent = seleccionado.get();
		if (parent instanceof Challenge) {
			addGoal((Challenge) parent);
		} else if (parent instanceof Goal) {
			addTest((Goal) parent);			
		} else if (parent instanceof TestGroup) {
			addTest((TestGroup) parent);
		} else if (parent instanceof NotTest) {
			addTest((NotTest) parent);
		} else if (parent instanceof CommandTest) {
			addCommand((CommandTest) parent);
		}
	}

	private void addCommand(CommandTest parent) {
		Command command = new Command();
		parent.setCommand(command);
		System.out.println("comando " + command.getExecutable() + " añadido al test de comando " + parent.getName());
	}

	private void addTest(NotTest parent) {
		TestContextMenu menu = new TestContextMenu();
		menu.show(addButton, Side.BOTTOM, 0, 0);
		menu.setOnHidden(e -> {
			Test childTest = menu.getTest();
			if (childTest != null) {
				parent.setTest(childTest);
				System.out.println("test " + childTest.getName() + " añadido al test de negación " + parent.getName());
			}
		});
	}

	private void addTest(TestGroup test) {
		TestContextMenu menu = new TestContextMenu();
		menu.show(addButton, Side.BOTTOM, 0, 0);
		menu.setOnHidden(e -> {
			Test childTest = menu.getTest();
			if (childTest != null) {
				test.getTests().add(childTest);
				System.out.println("test " + childTest.getName() + " añadido al test compuesto " + test.getName());
			}
		});
	}

	private void addTest(Goal goal) {
		TestContextMenu menu = new TestContextMenu();
		menu.show(addButton, Side.BOTTOM, 0, 0);
		menu.setOnHidden(e -> {
			Test test = menu.getTest();
			if (test != null) {
				goal.setTest(test);
				System.out.println("test " + test.getName() + " añadido al objetivo " + goal.getName());
			}
		});
	}

	private void addGoal(Challenge challenge) {
		Goal goal = new Goal("Nuevo objetivo");
		challenge.getGoals().add(goal);
		System.out.println("objetivo " + goal.getName() + " añadido al reto " + challenge.getName());
	}

	/**
	 * Dispara el evento para eliminar elemento del arbol
	 * 
	 * @param event del boton
	 *            
	 */
	@FXML
	void deleteButtonAction(ActionEvent event) {
		removeSelected();
	}

	private void removeSelected() {
		Object item = seleccionado.get();
		if (item instanceof Goal) {
			removeGoal((Goal) item);
		} else if (item instanceof Test) {
			removeTest((Test) item); 			
		} else if (item instanceof Command) {
			removeCommand((Command) item);
		}
	}

	private void removeCommand(Command command) {
		CommandTest parent = (CommandTest) selectedItem.get().getParent().getValue();
		parent.setCommand(null);
		System.out.println("comando " + command.getExecutable() + " eliminado del test " + parent.getName());
	}

	private void removeTest(Test test) {
		Object parent = selectedItem.get().getParent().getValue();
		if (parent instanceof Goal) {
			Goal goal = (Goal) parent;
			goal.setTest(null);
			System.out.println("test " + test.getName() + " eliminado del objetivo " + goal.getName());
		} else if (parent instanceof TestGroup) {
			TestGroup compoundTest = (TestGroup) parent;
			compoundTest.getTests().remove(test);
			System.out.println("test " + test.getName() + " eliminado del test " + compoundTest.getName());
		} else if (parent instanceof NotTest) {
			NotTest notTest = (NotTest) parent;
			notTest.setTest(null);
			System.out.println("test " + test.getName() + " eliminado del test de negación " + notTest.getName());
		}
	}

	private void removeGoal(Goal goal) {
		challenge.get().getGoals().remove(goal);
		System.out.println("objetivo " + goal.getName() + " eliminado del reto " + challenge.getName());
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
