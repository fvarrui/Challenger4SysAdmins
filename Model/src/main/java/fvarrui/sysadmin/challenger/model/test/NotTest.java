package fvarrui.sysadmin.challenger.model.test;

import javax.xml.bind.annotation.XmlType;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Clase modelo representa un 'NotTest'
 * 
 * @author Fran Vargas
 * @version 1.0
 *
 */
@XmlType
public class NotTest extends Test {

	private ObjectProperty<Test> test;

	/**
	 * Constructor por defecto
	 */
	public NotTest() {
		this(null);
	}


	public NotTest(String name, Test test) {
		super(name);
		this.test = new SimpleObjectProperty<>(this, "test", test);
	}

	public NotTest(Test test) {
		this("Negación", test);
	}

	@Override
	public Boolean verify() {
		verified.set(!getTest().verify());
		return verified.get();
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

	public String toString(int spaces) {
		return super.toString(spaces) + "\n" + getTest().toString(spaces + 4);
	}
}
