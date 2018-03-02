package fvarrui.sysadmin.challenger.test.os;

import java.io.File;

import org.apache.commons.lang.SystemUtils;


/**
 * Clase de apoyo para determinar el S.O de la maquina que ejecuta la aplicacion.
 * 
 * @author Fran Vargas
 * @version 1.0
 *
 */
public class OSUtils {
	
	private static final String PS_PATH = "System32\\WindowsPowerShell\\v1.0\\PowerShell.exe"; 
	
	public static boolean isPSSupported() {
		if (!isWindows()) return false;
		File windir = new File(System.getenv("windir"));
		File ps = new File(windir, PS_PATH);
		return ps.exists();
	}

	public static boolean isMacOsX() {
		return SystemUtils.IS_OS_MAC_OSX;
	}
	
	public static boolean isWindows() {
		return SystemUtils.IS_OS_WINDOWS;
	}

	public static boolean isLinux() {
		return SystemUtils.IS_OS_LINUX;
	}
	
	public static SystemType getSystemType() {
		if (isLinux()) { 
			return SystemType.LINUX;
		} else if (isWindows()) {
			if (isPSSupported())
				return SystemType.WINDOWS_WITH_PS;
			else
				return SystemType.WINDOWS;
		} else if (isMacOsX()) {
			return SystemType.MAC_OS_X; 
		}
		return SystemType.UNKNOWN; 
	}
	
}
