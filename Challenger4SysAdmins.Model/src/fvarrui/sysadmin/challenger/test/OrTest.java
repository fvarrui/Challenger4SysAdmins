package fvarrui.sysadmin.challenger.test;

import javax.xml.bind.annotation.XmlType;

@XmlType
public class OrTest extends CompoundTest {

	public OrTest() {
		this(null);
	}

	public OrTest(String name, Test ... tests) {
		super(name, tests);
	}

	@Override
	public Boolean verify() {
		verified.set(getTests().stream().anyMatch(t -> t.verify()));
		return verified.get();
	}
}
