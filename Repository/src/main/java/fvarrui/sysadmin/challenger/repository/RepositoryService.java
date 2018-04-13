package fvarrui.sysadmin.challenger.repository;

import java.util.List;
import java.util.stream.Collectors;

import fvarrui.sysadmin.challenger.repository.dao.CommandDao;
import fvarrui.sysadmin.challenger.repository.dao.ShellDao;
import fvarrui.sysadmin.challenger.repository.entities.CommandEntity;
import fvarrui.sysadmin.challenger.repository.entities.ShellEntity;
import fvarrui.sysadmin.challenger.repository.items.CommandItem;
import fvarrui.sysadmin.challenger.repository.items.ShellItem;

public class RepositoryService {
	
	private ShellDao shellDao = new ShellDao();
	private CommandDao commandDao = new CommandDao(); 
	
	// ======================================
	// commands
	// ======================================

	public List<CommandItem> getCommands() {
		System.out.println("getting all commands");
		return commandDao.getAll().stream().map(entity -> entity.asItem()).collect(Collectors.toList());
	}

	public CommandItem findCommandByName(String name) {
		System.out.println("looking for command with name " + name);
		return commandDao.findByName(name).asItem();
	}
	
	public void updateCommand(CommandItem item) {
		System.out.println("updating command " + item.getName());
		CommandEntity entity = commandDao.findById(item.getId());
		entity.fromItem(item);
		ShellEntity shell = null;
		if (item.getShell() != null && item.getShell().getId() != null) {
			shell = shellDao.findById(item.getShell().getId());
		} 
		entity.setShell(shell);
		commandDao.save(entity);
	}
	
	public void addCommand(CommandItem item) {
		System.out.println("adding command " + item.getName());
		CommandEntity entity = new CommandEntity(item);
		ShellEntity shell = null;
		if (item.getShell() != null && item.getShell().getId() != null) {
			shell = shellDao.findById(item.getShell().getId());
		}
		entity.setShell(shell);
		commandDao.save(entity);
	}
	
	public void removeCommand(CommandItem item) {
		CommandEntity entity = commandDao.findById(item.getId());
		if (entity != null) {
			System.out.println("removing command with name " + item.getName());
			commandDao.remove(entity);
		} else {
			System.out.println("couldn't remove command with id " + item.getId() + " because it doesn't exist");
		}
	}

	// ======================================
	// shells
	// ======================================
	
	public List<ShellItem> getShells() {
		System.out.println("getting all shells");
		return shellDao.getAll().stream().map(entity -> entity.asItem()).collect(Collectors.toList());
	}

	public ShellItem findShellByName(String name) {
		System.out.println("looking for shell with name " + name);
		return shellDao.findByName(name).asItem();
	}
	
	public void updateShell(ShellItem item) {
		System.out.println("updating shell " + item.getName());
		ShellEntity entity = shellDao.findById(item.getId());
		entity.fromItem(item);
		CommandEntity command = null;
		if (item.getVersionCommand() != null && item.getVersionCommand().getId() != null) {
			command = commandDao.findById(item.getVersionCommand().getId());
		} 
		entity.setVersionCommand(command);
		shellDao.save(entity);
	}
	
	public void addShell(ShellItem item) {
		System.out.println("adding shell " + item.getName());
		ShellEntity entity = new ShellEntity(item);		
		CommandEntity command = null;
		if (item.getVersionCommand() != null && item.getVersionCommand().getId() != null) {
			command = commandDao.findById(item.getVersionCommand().getId());
		} 
		entity.setVersionCommand(command);
		shellDao.save(entity);
		item.setId(entity.getId());
	}
	
	public void removeShell(ShellItem item) {
		ShellEntity entity = shellDao.findById(item.getId());
		if (entity != null) {
			System.out.println("removing shell with name " + item.getName());
			shellDao.remove(entity);
		} else {
			System.out.println("couldn't remove shell with id " + item.getId() + " because it doesn't exist");
		}
	}

}
