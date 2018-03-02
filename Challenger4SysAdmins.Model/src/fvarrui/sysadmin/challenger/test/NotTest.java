package fvarrui.sysadmin.challenger.test;

import javax.xml.bind.annotation.XmlType;


/**
 * Clase modelo representa un 'NotTest'
 * @author Fran Vargas
 * @version 1.0
 *
 */
@XmlType
public class NotTest extends CompoundTest {

	/**
	 * Constructor por defecto
	 */
	public NotTest() {
		this(null);
	}
	/**
	 * 
	 * @param name nombre del test
	 * @param test 
	 */
	public NotTest(String name, Test test) {
		super(name, test);
	}
	
	public NotTest(Test test) {
		this("Negación", test);
	}
	
	@Override
	public Boolean verify() {
		verified.set(!getTests().get(0).verify());
		return verified.get();
	}
	

}
