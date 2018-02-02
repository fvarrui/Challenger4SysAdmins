package fvarrui.sysadmin.challenger.test;

import javax.xml.bind.annotation.XmlType;

@XmlType
public class AndTestFX extends CommandTestFX {

//	public AndTestFX() {
//		super(null);
//	}
	
	public AndTestFX(String name, TestFX ... tests) {
//		super(name, tests);
	}

	@Override
	public Boolean verify() {
//		return verified = getTests().stream().allMatch(t -> t.verify());
		return null;
	}

}
