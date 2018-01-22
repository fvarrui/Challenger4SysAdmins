package fvarrui.sysadmin.editor.datamodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Ricardo Vargas
 */

public class TopEditorModel {

	private StringProperty name;
	private StringProperty description;

	public TopEditorModel() {

		name = new SimpleStringProperty(this, "name");
		description = new SimpleStringProperty(this, "description");
	}

	public final StringProperty nameProperty() {
		return this.name;
	}

	public final String getName() {
		return this.nameProperty().get();
	}

	public final void setName(final String name) {
		this.nameProperty().set(name);
	}

	public final StringProperty descriptionProperty() {
		return this.description;
	}

	public final String getDescription() {
		return this.descriptionProperty().get();
	}

	public final void setDescription(final String description) {
		this.descriptionProperty().set(description);
	}

}
