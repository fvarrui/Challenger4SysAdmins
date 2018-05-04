package fvarrui.sysadmin.challenger.model.command;

import java.io.File;

import javax.xml.bind.annotation.XmlType;

/**
 * Clase modelo representa un script de PowerShell.
 * @author Fran Vargas
 * @version 1.0
 *
 */
@XmlType
public class PSScript extends Command {

	private static final String PS = "powershell";

	public PSScript() {
		this("");
	}

	public PSScript(File scriptFile) {
		this(scriptFile.getAbsolutePath());
	}
	
	public PSScript(String scriptPath) {
		super(PS, "-NoProfile", "-WindowStyle", "Hidden", "-ExecutionPolicy", "Bypass", "-File", scriptPath);
	}


}
