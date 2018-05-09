package fvarrui.sysadmin.challenger.test;

import fvarrui.sysadmin.challenger.monitoring.Monitor;
import fvarrui.sysadmin.challenger.monitoring.ShellMonitor;
import fvarrui.sysadmin.challenger.monitoring.windows.WindowsShellMonitor;

public class Main {

	private static void test() throws Exception {
		
		System.out.println("iniciando test");

		Monitor l = new WindowsShellMonitor();
		l.setDaemon(false);
		l.addListener((monitor, data) -> {
			System.out.println(String.format("%s shell=%s username=%s pwd=%s oldpwd=%s command=%s", 
					data.get(ShellMonitor.TIMESTAMP).toString(),
					data.get(ShellMonitor.SHELL),
					data.get(ShellMonitor.USERNAME), 
					data.get(ShellMonitor.PWD),
					data.get(ShellMonitor.OLDPWD),
					data.get(ShellMonitor.COMMAND))
				);
		});
		l.start();

		System.out.println("finalizando test");
		
//		 Thread.sleep(60000L);
//		 l.requestStop();

	}

	public static void main(String[] args) throws Exception {
		test();
	}
	
}
