package fvarrui.sysadmin.challenger.common.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class DateTimeUtils {

	public static LocalDateTime xmlInstantToLocalDateTime(String xmlInstant) { 
		return LocalDateTime.ofInstant(Instant.parse(xmlInstant), ZoneOffset.UTC);
	}

	public static ZonedDateTime xmlInstantToZonedDateTime(String xmlInstant) { 
		return ZonedDateTime.ofInstant(Instant.parse(xmlInstant), ZoneOffset.UTC);
	}

}
