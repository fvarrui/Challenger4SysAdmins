package fvarrui.sysadmin.challenger.repository.items;

public class ShellItem extends Item {

	private String command;
	private CommandItem versionCommand;

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public CommandItem getVersionCommand() {
		return versionCommand;
	}

	public void setVersionCommand(CommandItem versionCommand) {
		this.versionCommand = versionCommand;
	}

}
