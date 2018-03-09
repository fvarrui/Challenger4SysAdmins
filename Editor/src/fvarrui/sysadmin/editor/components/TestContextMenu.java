package fvarrui.sysadmin.editor.components;

import java.io.IOException;

import fvarrui.sysadmin.challenger.test.AndTest;
import fvarrui.sysadmin.challenger.test.CommandTest;
import fvarrui.sysadmin.challenger.test.NotTest;
import fvarrui.sysadmin.challenger.test.OrTest;
import fvarrui.sysadmin.challenger.test.Test;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.stage.WindowEvent;


/**
 * Logica de negocio que gestiona el menu contextual
 * @author Fran Vargas
 * @version 1.0
 */
public class TestContextMenu extends ContextMenu {

	// model
	
	private ObjectProperty<Test> test = new SimpleObjectProperty<>(this, "test");

	// view

	@FXML
	private MenuItem andTestMenuItem;

	@FXML
	private MenuItem orTestMenuItem;

	@FXML
	private MenuItem notTestMenuItem;

	@FXML
	private MenuItem commandTest;

	public TestContextMenu() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("TestContextMenuView.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		setOnShown(e -> onShown(e));
	}
	
	
	private void onShown(WindowEvent e) {
		test.set(null);
	}

	@FXML
	void onAndTestAction(ActionEvent event) {
		test.set(new AndTest("AndTest"));
	}

	@FXML
	void onCommandTestAction(ActionEvent event) {
		test.set(new CommandTest("CommandTest", null));
	}

	@FXML
	void onNotTestAction(ActionEvent event) {
		test.set(new NotTest("NotTest", null));
	}

	@FXML
	void onOrTestAction(ActionEvent event) {
		test.set(new OrTest("OrTest"));
	}

	public final ObjectProperty<Test> testProperty() {
		return this.test;
	}

	public final Test getTest() {
		return this.testProperty().get();
	}

	public final void setTest(final Test test) {
		this.testProperty().set(test);
	}

}
