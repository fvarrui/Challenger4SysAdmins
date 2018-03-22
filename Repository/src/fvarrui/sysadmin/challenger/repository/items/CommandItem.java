package fvarrui.sysadmin.challenger.repository.items;

public class CommandItem {
	private Long id;
	private String name;
	private String description;
	private String command;
	private ShellItem shell;

	public CommandItem() {
		this(null);
	}

	public CommandItem(String command) {
		super();
		this.command = command;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public ShellItem getShell() {
		return shell;
	}

	public void setShell(ShellItem shell) {
		this.shell = shell;
	}

	@Override
	public String toString() {
		return getName();
	}

}
