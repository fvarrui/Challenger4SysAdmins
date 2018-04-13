package fvarrui.sysadmin.editor.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import fvarrui.sysadmin.challenger.model.test.Test;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;


/**
 * Logica de negocio para gestionar los Test de los retos
 * @author Ricardo Vargas
 * @version 1.0
 */
public class TestController implements Initializable {

	private ObjectProperty<Test> test = new SimpleObjectProperty<>(this, "test");

	@FXML
	private BorderPane view;

	@FXML
	private TextField testText;

	@FXML
	private TextArea descriptionText;

	
	/**
	 * Constructor de los test
	 * @throws IOException si no pudo cargar la vista
	 */
	public TestController() throws IOException {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fvarrui/sysadmin/editor/ui/views/TestView.fxml"));
		loader.setController(this);
		loader.load();

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		test.addListener((o, ov, nv) -> onTestChange(ov, nv));

	}

	/**
	 * Listener en el modelo para bindear y desbindear los componenetes al modelo
	 * @param oldTest modelo viejo de Test
	 * @param newTest modelo nuevo de Test
	 */
	private void onTestChange(Test oldTest, Test newTest) {
	
		if (oldTest != null) {
			testText.textProperty().unbindBidirectional(oldTest.nameProperty());
			descriptionText.textProperty().unbindBidirectional(oldTest.descriptionProperty());
		}
		if (newTest != null) {
			testText.textProperty().bindBidirectional(newTest.nameProperty());
			descriptionText.textProperty().bindBidirectional(newTest.descriptionProperty());
		}
	}


	public BorderPane getView() {
		return view;
	}


	public ObjectProperty<Test> testProperty() {
		return this.test;
	}
	


	public Test getTest() {
		return this.testProperty().get();
	}
	
	

	public void setTest(final Test test) {
		this.testProperty().set(test);
	}
	

}
