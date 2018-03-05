package fvarrui.sysadmin.challenger.test;

import javax.xml.bind.annotation.XmlType;

/**
 * Clase modelo representa un test de tipo 'and'.
 * 
 * @author Fran Vargas
 * @version 1.0
 *
 */
@XmlType
public class AndTest extends CompoundTest {

	/**
	 * Constructor por defecto
	 */
	public AndTest() {
		super(null);
	}


	public AndTest(String name, Test... tests) {
		super(name, tests);
	}

	@Override
	public Boolean verify() {
		verified.set(getTests().stream().allMatch(t -> t.verify()));
		return verified.get();
	}

}
