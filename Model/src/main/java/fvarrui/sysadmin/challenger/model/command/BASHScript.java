package fvarrui.sysadmin.challenger.model.command;

import java.io.File;

import javax.xml.bind.annotation.XmlType;

/**
 * Clase Modelo que representa un script BASH
 * @author Fran Vargas
 * @version 1.0
 * 
 */
@XmlType
public class BASHScript extends Command {
	
	private static final String BASH = "bash";
	
	public BASHScript() {
		this("");
	}

	public BASHScript(String scriptPath, String ...params) {
		this(false, scriptPath, params);
	}

	public BASHScript(File scriptFile, String ...params) {
		this(false, scriptFile.getAbsolutePath(), params);
	}
	
	public BASHScript(boolean asRoot, File scriptFile, String ...params) {
		this(asRoot, scriptFile.getAbsolutePath(), params);
	}

	public BASHScript(boolean asRoot, String scriptPath, String ... params) {
		super(BASH, scriptPath);
		getArguments().addAll(params);
	}

}
