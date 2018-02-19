//package fvarrui.sysadmin.challenger.test;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import javax.xml.bind.annotation.XmlElement;
//import javax.xml.bind.annotation.XmlElementWrapper;
//import javax.xml.bind.annotation.XmlSeeAlso;
//import javax.xml.bind.annotation.XmlType;
//
//@XmlType
//@XmlSeeAlso(value = { AndTest.class, OrTest.class, NotTest.class })
//public abstract class CompoundTest extends Test {
//	
//	@XmlElement(name = "test")
//	@XmlElementWrapper(name = "tests")
//	private List<Test> tests; 
//
//	public CompoundTest() {
//		super();
//		this.tests = new ArrayList<>();
//	}
//	
//	public CompoundTest(String name, Test ... tests) {
//		super(name);
//		this.tests = Arrays.asList(tests);
//	}
//	
//	public List<Test> getTests() {
//		return tests;
//	}
//	
//	public String toString(int spaces) {
//		return super.toString(spaces) + "\n" + tests.stream().map(g -> g.toString(spaces + 4)).collect(Collectors.joining("\n"));
//	}
//
//	@Override
//	public String toString() {
//		return toString(4);
//	}
//	
//}
