package fvarrui.sysadmin.challenger.model.test;

import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * Clase modelo representa un 'CompoundTest',un conjunto de tests
 * @author Fran Vargas
 * @version 1.0
 *
 */
@XmlType
@XmlSeeAlso(value = { AndTest.class, OrTest.class })
public abstract class TestGroup extends Test {

	private ListProperty<Test> tests;

	/**
	 * Constructor por defecto
	 */
	public TestGroup() {
		this(null);
	}

	public TestGroup(String name, Test... tests) {
		 super(name);
		 this.tests = new SimpleListProperty<>(this, "tests", FXCollections.observableArrayList(tests));
	}

	public String toString(int spaces) {
		return super.toString(spaces) + "\n" + tests.stream().map(g -> g.toString(spaces + 4)).collect(Collectors.joining("\n"));
	}

	public ListProperty<Test> testsProperty() {
		return this.tests;
	}

	@XmlElement(name = "test")
	@XmlElementWrapper(name = "tests")
	public ObservableList<Test> getTests() {
		return this.testsProperty().get();
	}

}
