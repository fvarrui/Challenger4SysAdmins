package fvarrui.sysadmin.challenger.test;

import javax.xml.bind.annotation.XmlElement;

import fvarrui.sysadmin.challenger.test.os.OSUtils;
import fvarrui.sysadmin.challenger.test.os.SystemType;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class PlatformDependentTestFX extends TestFX {

	@XmlElement
	private MapProperty<SystemType, TestFX> tests;

	public PlatformDependentTestFX() {
		this(null);
	}

	public PlatformDependentTestFX(String name) {
		super(name);
		this.tests = new SimpleMapProperty<>(this, "tests", FXCollections.observableHashMap());
	}

	public MapProperty<SystemType, TestFX> testsProperty() {
		return this.tests;
	}

	public ObservableMap<SystemType, TestFX> getTests() {
		return this.testsProperty().get();
	}

	@Override
	public Boolean verify() {

		TestFX test = tests.get(OSUtils.getSystemType());
		if (test == null)
			throw new RuntimeException(
					"No se ha encontrado test '" + getName() + "' para el sistema " + OSUtils.getSystemType());
		return test.verify();
	}
}
