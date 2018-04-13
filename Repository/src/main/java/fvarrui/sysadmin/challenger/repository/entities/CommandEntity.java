package fvarrui.sysadmin.challenger.repository.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import fvarrui.sysadmin.challenger.repository.items.CommandItem;

@javax.persistence.Entity
public class CommandEntity extends Entity {


	@Column(nullable = false)
	private String command;

	@ManyToOne(optional = true, cascade = { CascadeType.DETACH }, fetch = FetchType.LAZY)
	private ShellEntity shell;

	public CommandEntity() {
		super();
	}

	public CommandEntity(CommandItem item) {
		this();
		fromItem(item);
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public ShellEntity getShell() {
		return shell;
	}

	public void setShell(ShellEntity shell) {
		this.shell = shell;
	}

	public CommandItem toItem(CommandItem item, boolean includeShell) {
		item.setId(getId());
		item.setName(getName());
		item.setDescription(getDescription());
		item.setCommand(getCommand());
		if (getShell() != null && includeShell) {
			item.setShell(getShell().asItem());
		}
		return item;
	}

	public CommandItem asItem() {
		return asItem(true);
	}
	
	public CommandItem asItem(boolean includeShell) {
		return toItem(new CommandItem(), includeShell);
	}

	public CommandEntity fromItem(CommandItem item) {
		setId(item.getId());
		setName(item.getName());
		setDescription(item.getDescription());
		setCommand(item.getCommand());
		return this;
	}

}
