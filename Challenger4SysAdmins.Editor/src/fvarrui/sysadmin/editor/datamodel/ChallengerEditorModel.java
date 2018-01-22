package fvarrui.sysadmin.editor.datamodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * @author Ricardo Vargas
 */

public class ChallengerEditorModel {

	private ObjectProperty<CenterEditorModel> centerEditorModel;
	private ObjectProperty<RightEditorModel> rightEditorModel;
	private ObjectProperty<TopEditorModel> topEditorModel;

	public ChallengerEditorModel() {

		centerEditorModel = new SimpleObjectProperty<>(this, "centerEditorModel",new CenterEditorModel());
		rightEditorModel = new SimpleObjectProperty<>(this, "rightEditorModel",new RightEditorModel());
		topEditorModel = new SimpleObjectProperty<>(this, "topEditorModel",new TopEditorModel());

	}

	public final ObjectProperty<CenterEditorModel> centerEditorModelProperty() {
		return this.centerEditorModel;
	}

	public final CenterEditorModel getCenterEditorModel() {
		return this.centerEditorModelProperty().get();
	}

	public final void setCenterEditorModel(final CenterEditorModel centerEditorModel) {
		this.centerEditorModelProperty().set(centerEditorModel);
	}

	public final ObjectProperty<RightEditorModel> rightEditorModelProperty() {
		return this.rightEditorModel;
	}

	public final RightEditorModel getRightEditorModel() {
		return this.rightEditorModelProperty().get();
	}

	public final void setRightEditorModel(final RightEditorModel rightEditorModel) {
		this.rightEditorModelProperty().set(rightEditorModel);
	}

	public final ObjectProperty<TopEditorModel> topEditorModelProperty() {
		return this.topEditorModel;
	}

	public final TopEditorModel getTopEditorModel() {
		return this.topEditorModelProperty().get();
	}

	public final void setTopEditorModel(final TopEditorModel topEditorModel) {
		this.topEditorModelProperty().set(topEditorModel);
	}

}
