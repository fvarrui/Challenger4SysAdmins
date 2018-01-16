package fvarrui.sysadmin.challenger.command;

import javax.xml.bind.annotation.XmlType;

@XmlType
public class DOSCommand extends Command {
	private static final String DOS = "cmd /c ";
	
	private String dosCommand;
	
	public DOSCommand() {
		super();
	}

	public DOSCommand(String command) {
		super();
		setCommand(command);
	}
	
	@Override
	public void setCommand(String command) {
		super.setCommand(DOS + command);
		this.dosCommand = command;		
	}
	
	@Override
	public String getCommand() {
		return dosCommand;
	}

}
