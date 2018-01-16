package fvarrui.sysadmin.challenger;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.StringUtils;

import fvarrui.sysadmin.challenger.test.Test;

@XmlType
public class Goal {
	
	private String name;	
	private String description;
	private Test test;
	
	@XmlTransient
	private boolean achieved;

	public Goal() {
		this(null);
	}

	public Goal(String name) {
		this(name, null);
	}

	public Goal(String name, String description) {
		super();
		this.name = name;
		this.description = description;
		this.achieved = false;
	}
	
	@XmlID
	@XmlAttribute
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlElement
	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}
	
	public boolean isAchieved() {
		return achieved = test.verify();
	}
	
	@Override
	public String toString() {
		return StringUtils.repeat("-", 7) + " (" + (achieved ? "+" : "-") + ") [goal] " + getDescription() + "\n" + test.toString(11);
	}

}
