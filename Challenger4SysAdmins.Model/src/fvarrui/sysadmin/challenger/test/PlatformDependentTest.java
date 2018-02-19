//package fvarrui.sysadmin.challenger.test;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.xml.bind.annotation.XmlElement;
//import javax.xml.bind.annotation.XmlType;
//
//import fvarrui.sysadmin.challenger.test.os.OSUtils;
//import fvarrui.sysadmin.challenger.test.os.SystemType;
//
//@XmlType
//public class PlatformDependentTest extends Test {
//
//	@XmlElement
//	private Map<SystemType, Test> tests;
//	
//	public PlatformDependentTest() {
//		this(null);
//	}
//
//	public PlatformDependentTest(String name) {
//		super(name);
//		this.tests = new HashMap<>();
//	}
//
//	public Map<SystemType, Test> getTests() {
//		return tests;
//	}
//
//	@Override
//	public Boolean verify() {
//		Test test = tests.get(OSUtils.getSystemType());
//		if (test == null) throw new RuntimeException("No se ha encontrado test '" + getName() + "' para el sistema " + OSUtils.getSystemType());
//		return test.verify();
//	}
//
//}
