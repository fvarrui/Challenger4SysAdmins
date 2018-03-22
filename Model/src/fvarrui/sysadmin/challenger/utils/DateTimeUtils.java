package fvarrui.sysadmin.challenger.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateTimeUtils {

	public static LocalDateTime xmlInstantToLocalDateTime(String xmlInstant) { 
		return LocalDateTime.ofInstant(Instant.parse(xmlInstant), ZoneId.systemDefault());
	}
	
}
