package fvarrui.sysadmin.challenger.test;

import javax.xml.bind.annotation.XmlElement;

import fvarrui.sysadmin.challenger.test.os.OSUtils;
import fvarrui.sysadmin.challenger.test.os.SystemType;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class PlatformDependentTest extends Test {

	@XmlElement
	private MapProperty<SystemType, Test> tests;

	public PlatformDependentTest() {
		this(null);
	}

	public PlatformDependentTest(String name) {
		super(name);
		this.tests = new SimpleMapProperty<>(this, "tests", FXCollections.observableHashMap());
	}

	public MapProperty<SystemType, Test> testsProperty() {
		return this.tests;
	}

	public ObservableMap<SystemType, Test> getTests() {
		return this.testsProperty().get();
	}

	@Override
	public Boolean verify() {
		Test test = tests.get(OSUtils.getSystemType());
		if (test == null) {
			throw new RuntimeException("No se ha encontrado test '" + getName() + "' para el sistema " + OSUtils.getSystemType());
		}
		return test.verify();
	}
}
