package fvarrui.sysadmin.challenger;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.StringUtils;

import fvarrui.sysadmin.challenger.test.Test;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@XmlType
public class Goal {
	
	private StringProperty name;
	private StringProperty description;
	private ObjectProperty<Test> test;
	
	@XmlTransient
	private BooleanProperty achieved;

	public Goal() {
		this(null);
	}
	
	public Goal(String name) {
		this(name, null);
	}
	
	public Goal(String name, String description) {
		super();
		this.name = new SimpleStringProperty(this,"name",name);
		this.description = new SimpleStringProperty(this,"description",description);
		this.test = new SimpleObjectProperty<>(this,"test");
		this.achieved =new SimpleBooleanProperty(this,"achieved",false);
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
	

	public final BooleanProperty achievedProperty() {
		return this.achieved;
	}
	

	public final boolean isAchieved() {
		return this.achievedProperty().get();
	}
	

	public final void setAchieved(final boolean achieved) {
		this.achievedProperty().set(achieved);
	}
	
	@Override
	public String toString() {
		return StringUtils.repeat("-", 7) + " (" + (achieved.get() ? "+" : "-") + ") [goal] " + getDescription() + "\n" + getTest().toString(11);
	}
	
	
}
