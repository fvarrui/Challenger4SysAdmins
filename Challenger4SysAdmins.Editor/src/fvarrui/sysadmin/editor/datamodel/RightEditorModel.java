package fvarrui.sysadmin.editor.datamodel;

import fvarrui.sysadmin.challenger.test.Test;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Ricardo Vargas
 */

public class RightEditorModel {

	private ListProperty<Test> tests;

	public RightEditorModel() {

		tests = new SimpleListProperty<>(this, "tests", FXCollections.observableArrayList());
	}

	public final ListProperty<Test> testsProperty() {
		return this.tests;
	}

	public final ObservableList<Test> getTests() {
		return this.testsProperty().get();
	}

	public final void setTests(final ObservableList<Test> tests) {
		this.testsProperty().set(tests);
	}

}
