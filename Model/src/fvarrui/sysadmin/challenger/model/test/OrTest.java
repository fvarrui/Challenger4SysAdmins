package fvarrui.sysadmin.challenger.model.test;

import javax.xml.bind.annotation.XmlType;

/**
 * Clase modelo representa un 'OrTest'
 * 
 * @author Fran Vargas
 * @version 1.0
 *
 */
@XmlType
public class OrTest extends TestGroup {

	/**
	 * Constructor por defecto
	 */
	public OrTest() {
		this(null);
	}


	public OrTest(String name, Test... tests) {
		super(name, tests);
	}

	@Override
	public Boolean verify() {
		verified.set(getTests().stream().anyMatch(t -> t.verify()));
		return verified.get();
	}
}
