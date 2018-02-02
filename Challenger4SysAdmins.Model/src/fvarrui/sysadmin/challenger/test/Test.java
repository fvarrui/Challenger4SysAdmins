//package fvarrui.sysadmin.challenger.test;
//
//import javax.xml.bind.annotation.XmlAttribute;
//import javax.xml.bind.annotation.XmlElement;
//import javax.xml.bind.annotation.XmlID;
//import javax.xml.bind.annotation.XmlSeeAlso;
//import javax.xml.bind.annotation.XmlTransient;
//import javax.xml.bind.annotation.XmlType;
//
//import org.apache.commons.lang.StringUtils;
//
//@XmlType
//@XmlSeeAlso(value = { CommandTest.class, CompoundTest.class, PlatformDependentTest.class})
//public abstract class Test {
//	private String name;	
//	private String description;
//	
//	@XmlTransient
//	protected boolean verified;
//
//	public Test() {
//		this(null);
//	}
//
//	public Test(String name) {
//		this.name = name;
//		this.verified = false;
//	}
//
//	@XmlElement
//	public String getDescription() {
//		return description;
//	}
//
//	public void setDescription(String description) {
//		this.description = description;
//	}
//
//	@XmlID
//	@XmlAttribute
//	public String getName() {
//		return name;
//	}
//
//	public boolean isVerified() {
//		return verified;
//	}
//
//	public String toString(int spaces) {
//		return StringUtils.repeat("-", spaces) + " (" + (verified ? "+" : "-") + ") [" + getClass().getSimpleName() + "] " + (getName() != null ? getName() : getDescription());		
//	}
//	
//	@Override
//	public String toString() {
//		return toString(11);
//	}
//	
//	public abstract Boolean verify();
//
//}
