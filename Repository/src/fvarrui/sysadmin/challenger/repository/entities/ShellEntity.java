package fvarrui.sysadmin.challenger.repository.entities;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

import fvarrui.sysadmin.challenger.repository.items.ShellItem;

@javax.persistence.Entity
public class ShellEntity extends Entity {

	@Column(nullable = false)
	private String command;

	@OneToOne(optional = true, fetch = FetchType.LAZY)
	private CommandEntity versionCommand;

	public ShellEntity() {
		super();
	}

	public ShellEntity(ShellItem item) {
		this();
		fromItem(item);
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public CommandEntity getVersionCommand() {
		return versionCommand;
	}

	public void setVersionCommand(CommandEntity versionCommand) {
		this.versionCommand = versionCommand;
	}

	public ShellItem toItem(ShellItem item) {
		item.setId(getId());
		item.setName(getName());
		item.setCommand(getCommand());
		item.setDescription(getDescription());
		item.setVersionCommand(getVersionCommand().asItem(false));
		return item;
	}

	public ShellItem asItem() {
		return toItem(new ShellItem());
	}

	public ShellEntity fromItem(ShellItem item) {
		setId(item.getId());
		setName(item.getName());
		setCommand(item.getCommand());
		setDescription(item.getDescription());
		return this;
	}

}
