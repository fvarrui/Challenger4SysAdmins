package fvarrui.sysadmin.challenger.test;



import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.StringUtils;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@XmlType
@XmlSeeAlso(value = { CommandTest.class, CompoundTest.class, PlatformDependentTest.class })
public abstract class Test {

	private StringProperty name;
	private StringProperty description;

	@XmlTransient
	protected BooleanProperty verified;

	public Test() {
		this(null);
	}

	public Test(String name) {
		this.name = new SimpleStringProperty(this, "name", name);
		this.verified = new SimpleBooleanProperty(this, "verified", false);
	}

	public StringProperty nameProperty() {
		return this.name;
	}

	@XmlID
	@XmlAttribute
	public String getName() {
		return this.nameProperty().get();
	}

	public void setName(final String name) {
		this.nameProperty().set(name);
	}

	public StringProperty descriptionProperty() {
		return this.description;
	}

	@XmlElement
	public String getDescription() {
		return this.descriptionProperty().get();
	}

	public void setDescription(final String description) {
		this.descriptionProperty().set(description);
	}

	public BooleanProperty verifiedProperty() {
		return this.verified;
	}

	public boolean isVerified() {
		return this.verifiedProperty().get();
	}

	public void setVerified(final boolean verified) {
		this.verifiedProperty().set(verified);
	}

	public String toString(int spaces) {
		return StringUtils.repeat("-", spaces) + " (" + (verified.get() ? "+" : "-") + ") [" + getClass().getSimpleName() + "] " + (getName() != null ? getName() : getDescription());		
	}
	
	@Override
	public String toString() {
		return toString(11);
	}
	
	public abstract Boolean verify();
}
