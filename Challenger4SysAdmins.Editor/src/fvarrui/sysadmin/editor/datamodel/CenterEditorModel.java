package fvarrui.sysadmin.editor.datamodel;

import fvarrui.sysadmin.challenger.Goal;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Ricardo Vargas
 */

public class CenterEditorModel {

	private ListProperty<Goal> goals;
	private ObjectProperty<Goal> selectedGoal;

	public CenterEditorModel() {

		goals = new SimpleListProperty<>(this, "goals", FXCollections.observableArrayList());
		selectedGoal = new SimpleObjectProperty<>(this, "goals");
	}

	public final ListProperty<Goal> goalsProperty() {
		return this.goals;
	}

	public final ObservableList<Goal> getGoals() {
		return this.goalsProperty().get();
	}

	public final void setGoals(final ObservableList<Goal> goals) {
		this.goalsProperty().set(goals);
	}

	public final ObjectProperty<Goal> selectedGoalProperty() {
		return this.selectedGoal;
	}

	public final Goal getSelectedGoal() {
		return this.selectedGoalProperty().get();
	}

	public final void setSelectedGoal(final Goal selectedGoal) {
		this.selectedGoalProperty().set(selectedGoal);
	}

}
