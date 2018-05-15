package fvarrui.sysadmin.challenger.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.StringUtils;

import fvarrui.sysadmin.challenger.model.test.ExecutedCommand;
import fvarrui.sysadmin.challenger.model.test.ShellTest;
import fvarrui.sysadmin.challenger.model.test.Test;
import fvarrui.sysadmin.challenger.model.test.TestGroup;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;


/**
 * Clase Modelo que representa un objetivo
 * @author Fran Vargas,Ricardo Vargas
 * @version 1.0
 * 
 */
@XmlType
public class Goal {
	
	private ListChangeListener<ExecutedCommand> listener;
	private List<Test> shellTests;
	private ListProperty<ExecutedCommand> executedCommands;

	private StringProperty name;
	private StringProperty description;
	private ObjectProperty<Test> test;

	@XmlTransient
	private ReadOnlyBooleanWrapper achieved;

	/**
	 * Constructor por defecto
	 */
	public Goal() {
		this(null);
	}

	/**
	 * Constructor
	 * @param name nombre del objetivo
	 */
	public Goal(String name) {
		this(name, null);
	}


	/**
	 * 
	 * @param name nombre del objetivo
	 * @param description del objetivo
	 */
	public Goal(String name, String description) {
		super();
		this.name = new SimpleStringProperty(this, "name", name);
		this.description = new SimpleStringProperty(this, "description", description);
		this.test = new SimpleObjectProperty<>(this, "test");
		this.achieved = new ReadOnlyBooleanWrapper(this, "achieved", false);
		
		this.test.addListener((o, ov, nv) -> {
			if (nv != null) {
				this.achieved.bind(nv.verifiedProperty());
			} else {
				this.achieved.unbind();
			}
		});
	}

	public final StringProperty nameProperty() {
		return this.name;
	}

	@XmlID
	@XmlAttribute
	public final String getName() {
		return this.nameProperty().get();
	}

	public final void setName(final String name) {
		this.nameProperty().set(name);
	}

	public final StringProperty descriptionProperty() {
		return this.description;
	}

	@XmlElement
	public final String getDescription() {
		return this.descriptionProperty().get();
	}

	public final void setDescription(final String description) {
		this.descriptionProperty().set(description);
	}

	public final ObjectProperty<Test> testProperty() {
		return this.test;
	}

	@XmlElement
	public final Test getTest() {
		return this.testProperty().get();
	}

	public final void setTest(final Test test) {
		this.testProperty().set(test);
	}

	public final ReadOnlyBooleanProperty achievedProperty() {
		return this.achieved.getReadOnlyProperty();
	}
	
	public final boolean isAchieved() {
		return this.achievedProperty().get();
	}
	
	public void reset() {
		if (getTest() != null) getTest().reset();
	}
	
	/**
	 * metodo que comprueba que se haya completado un objetivo
	 * @return si el objetivo se a alcanzado
	 */
	public boolean verify() {
		if (!isAchieved()) {
			getTest().verify();
		}
		return isAchieved();
	}
	
	public String toString(int spaces) {
		return StringUtils.repeat("-", spaces) + " (" + (achieved.get() ? "+" : "-") + ") [goal] " + getName() + ((getTest() != null ) ? "\n" + getTest().toString(11) : "");
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
	private List<Test> getTestByClass(Class<? extends Test> clazz) {
		List<Test> tests = new ArrayList<>();
		if (clazz.isInstance(getTest())) {
			tests.add(getTest());
		}
		else if (getTest() instanceof TestGroup) {
			TestGroup group = (TestGroup) getTest();
			tests.addAll(group.getTestByClass(clazz));
		}
		return tests;
	}

	public void subscribeTo(ListProperty<ExecutedCommand> executedCommands) {
		this.executedCommands = executedCommands;
		this.shellTests = getTestByClass(ShellTest.class);
		this.listener = (Change<? extends ExecutedCommand> c) -> {
			while (c.next()) {
				for (ExecutedCommand cmd : c.getAddedSubList()) {
					notifyAllTests(cmd);
				}
			}
		};
		this.executedCommands.addListener(listener);
	}
	
	private void notifyAllTests(ExecutedCommand cmd) {
		Platform.runLater(() -> this.shellTests.stream().forEach(t -> ((ShellTest) t).commandExecuted(cmd)));
	}

	public void removeSubscription() {
		executedCommands.removeListener(listener);
		executedCommands = null;
	}

}
