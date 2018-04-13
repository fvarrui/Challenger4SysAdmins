import fvarrui.sysadmin.challenger.repository.RepositoryService;
import fvarrui.sysadmin.challenger.repository.dao.Dao;
import fvarrui.sysadmin.challenger.repository.items.CommandItem;

public class Test {

	public static void main(String[] args) {
		RepositoryService repo = new RepositoryService();
		
//		repo.removeShell("BASH");
		
//		ShellItem powershell = new ShellItem();
//		powershell.setName("PowerShell");
//		powershell.setDescription("Windows PowerShell");
//		powershell.setCommand("powershell \"%s\"");
//		powershell.setVersionCommandName("ps.version");
//		repo.addShell(powershell);
		
		CommandItem psVersion = new CommandItem();
		psVersion.setCommand("($PSVersionTable.PSVersion).ToString()");
		psVersion.setDescription("");
		psVersion.setName("ps.version");
		psVersion.setShell(repo.findShellByName("PowerShell"));
		repo.addCommand(psVersion);
		
//		CommandItem psVersion = repo.findCommandByName("ps.version");
//		System.out.println(psVersion.getCommand());
		
//		powershell.setVersion(psVersion);
//		repo.updateShell(powershell);

//		ShellItem shell2 = new ShellItem();
//		shell2.setName("BASH");
//		shell2.setDescription("Bourne Again SHell");
//		shell2.setCommand("bash -c \"%s\"");
//		shell2.setVersion(new CommandItem("echo \\\"$BASH_VERSION\\\""));
//		repo.addShell(shell2);
//
//		ShellItem shell3 = new ShellItem();
//		shell3.setName("CMD");
//		shell3.setCommand("cmd /c %s");
//		shell3.setDescription("Windows Command Prompt");
//		shell3.setVersion(new CommandItem("powershell [System.Diagnostics.FileVersionInfo]::GetVersionInfo(\\\"$env:windir\\system32\\cmd.exe\\\").FileVersion"));
//		repo.addShell(shell3);
		
//		ShellItem shell2 = new ShellItem();
//		shell2.setName("BASHITO");
//		shell2.setCommand("bash -c %s");
//		repo.updateShell("BASH", shell2);
		
//		ShellItem shell = repo.findShellByName("BASH");
//		System.out.println(shell.getId());
//		System.out.println(shell.getName());
//		System.out.println(shell.getCommand());
//		System.out.println(shell.getDescription());
//		System.out.println(shell.getVersionCommandName());
//
//		List<ShellItem> shells = repo.getShells();
//		for (ShellItem shell : shells) {
//			System.out.println(shell.getId());
//			System.out.println(shell.getName());
//			System.out.println(shell.getCommand());
//			System.out.println(shell.getDescription());
//			System.out.println(shell.getVersionCommandName());
//		}
		
//		ShellItem shell = new ShellItem();
//		shell.setId(1L);
//		repo.removeShell(shell);
		
//		ShellItem shell = repo.findShellByName("BASH");
//		shell.setName("Bashuko");
//		shell.setDescription("bournito");
//		shell.setCommand("comandito");
//		repo.updateShell(shell);
		
		Dao.close();
	}

}
