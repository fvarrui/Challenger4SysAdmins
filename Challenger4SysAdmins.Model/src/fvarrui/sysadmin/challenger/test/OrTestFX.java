package fvarrui.sysadmin.challenger.test;

import javax.xml.bind.annotation.XmlType;

@XmlType
public class OrTestFX extends CommandTestFX {

	public OrTestFX() {
		this(null);
	}

	public OrTestFX(String name, TestFX ... tests) {
//		super(name, tests);
	}

	@Override
	public Boolean verify() {
//		return verified = getTests().stream().anyMatch(t -> t.verify());
		return null;
	}
}
