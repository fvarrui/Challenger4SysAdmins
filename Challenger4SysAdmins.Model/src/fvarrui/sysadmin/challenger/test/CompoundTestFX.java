package fvarrui.sysadmin.challenger.test;

import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@XmlType
@XmlSeeAlso(value = { AndTestFX.class, OrTestFX.class, NotTestFX.class })
public abstract class CompoundTestFX extends TestFX {

	@XmlElement(name = "test")
	@XmlElementWrapper(name = "tests")
	private ListProperty<TestFX> tests;

	public CompoundTestFX() {
		super();
		this.tests = new SimpleListProperty<>(this, "tests", FXCollections.observableArrayList());
	}

	public CompoundTestFX(String name, TestFX... tests) {
		// super(name);
		// this.tests = Arrays.asList(tests);
	}

	public String toString(int spaces) {
		return super.toString(spaces) + "\n"
				+ tests.stream().map(g -> g.toString(spaces + 4)).collect(Collectors.joining("\n"));
	}

	@Override
	public String toString() {
		return toString(4);
	}

	public ListProperty<TestFX> testsProperty() {
		return this.tests;
	}

	public ObservableList<TestFX> getTests() {
		return this.testsProperty().get();
	}

}
