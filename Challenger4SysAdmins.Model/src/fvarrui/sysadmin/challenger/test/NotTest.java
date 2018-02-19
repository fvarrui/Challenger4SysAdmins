//package fvarrui.sysadmin.challenger.test;
//
//import javax.xml.bind.annotation.XmlType;
//
//@XmlType
//public class NotTest extends CompoundTest {
//	
//	public NotTest() {
//		this(null);
//	}
//	
//	public NotTest(String name, Test test) {
//		super(name, test);
//	}
//	
//	public NotTest(Test test) {
//		this("Negación", test);
//	}
//	
//	@Override
//	public Boolean verify() {
//		return verified = !getTests().get(0).verify();
//	}
//	
//}
