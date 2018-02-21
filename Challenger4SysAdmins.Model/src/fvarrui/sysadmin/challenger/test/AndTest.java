package fvarrui.sysadmin.challenger.test;

import javax.xml.bind.annotation.XmlType;

@XmlType
public class AndTest extends CompoundTest {

	public AndTest() {
		super(null);
	}
	
	public AndTest(String name, Test ... tests) {
		super(name, tests);
	}

	@Override
	public Boolean verify() {
		verified.set(getTests().stream().allMatch(t -> t.verify()));
		return verified.get();
	}

}
