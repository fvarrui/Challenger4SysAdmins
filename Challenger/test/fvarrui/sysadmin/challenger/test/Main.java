package fvarrui.sysadmin.challenger.test;

import fvarrui.sysadmin.challenger.monitoring.Monitor;
import fvarrui.sysadmin.challenger.monitoring.ShellMonitor;
import fvarrui.sysadmin.challenger.monitoring.linux.BASHPromptMonitor;

public class Main {

	private static void test() throws Exception {

		Monitor l = new BASHPromptMonitor();
		l.addListener((data) -> {
			System.out.println(String.format("%s (%s): %s", data.get(ShellMonitor.TIMESTAMP),
					data.get(ShellMonitor.USERNAME), data.get(ShellMonitor.COMMAND)));
		});
		l.start();

		// Thread.sleep(15000L);
		// l.requestStop();

	}

	public static void main(String[] args) throws Exception {
		test();
	}
}
