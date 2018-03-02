package fvarrui.sysadmin.challenger.test;

import javax.xml.bind.annotation.XmlType;

/**
 * Clase modelo representa un 'OrTest'
 * 
 * @author Fran Vargas
 * @version 1.0
 *
 */
@XmlType
public class OrTest extends CompoundTest {

	/**
	 * Constructor por defecto
	 */
	public OrTest() {
		this(null);
	}

	/**
	 * 
	 * @param name nombres de los test
	 * @param conjunto de test
	 */
	public OrTest(String name, Test... tests) {
		super(name, tests);
	}

	@Override
	public Boolean verify() {
		verified.set(getTests().stream().anyMatch(t -> t.verify()));
		return verified.get();
	}
}
