package fvarrui.sysadmin.challenger.test;

import javax.xml.bind.annotation.XmlType;

import fvarrui.sysadmin.challenger.command.CommandFX;
import javafx.beans.property.ObjectProperty;

@XmlType
public class CommandTestFX extends TestFX {

	private static final int SUCCESS_EXIT_CODE = 0;
	
	private CommandFX command;
	
	//or ListProperty?
	private ObjectProperty<String[]>params;
	//private String[] params;
	
	public CommandTestFX() {
		this(null, null);
	}

	public CommandTestFX(String name, CommandFX command, String... params) {
		super(name);
		this.command = command;
		//this.params = params; ??¿
	}

	@Override
	public Boolean verify() {
		//return verified = (command.execute(params) == SUCCESS_EXIT_CODE);
		return null;
	}
	
	

}
