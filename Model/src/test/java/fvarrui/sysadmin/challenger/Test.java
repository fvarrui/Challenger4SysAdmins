package fvarrui.sysadmin.challenger;

import java.util.HashMap;
import java.util.Map;

import fvarrui.sysadmin.challenger.model.command.Command;
import fvarrui.sysadmin.challenger.model.command.PSCommand;

public class Test {

	public static void main(String[] args) {
		Map<String, Object> params = new HashMap<>();
		System.getProperties().forEach((k,v) -> params.put(k.toString(), v));
//		params.putAll(System.getenv());
		params.put("NAME", "notepad");
//		params.put("PsModulePath", "C:\\Windows");

//		Command c = new PSCommand("Get-ChildItem ${java.home}");
//		Command c = new PSCommand("$PSVersionTable");
		Command c = new PSCommand(
				"Get-LocalUser -Name fran | Format-Table" + "\n" +
				"Get-Service -Name chancleta" + "\n" +
				"if (Get-Process -Name ${NAME}) {" + "\n" + 
					"exit 0" + "\n" +
				"} else { " + "\n" +
					"exit 1 " + "\n" +
				"}");
		
		System.out.println(c.execute(params));
		
//		System.out.println("propiedades y variables de entorno");
//		System.out.println("----------------------------------");
//		params.forEach((k,v) -> System.out.println(k + "=" + v));
	}

}
