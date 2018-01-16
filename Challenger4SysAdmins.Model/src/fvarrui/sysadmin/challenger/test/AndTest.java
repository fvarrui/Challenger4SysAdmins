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
		return verified = getTests().stream().allMatch(t -> t.verify());
	}

}
