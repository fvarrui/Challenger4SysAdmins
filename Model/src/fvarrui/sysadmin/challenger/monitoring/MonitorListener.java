package fvarrui.sysadmin.challenger.monitoring;

import java.time.LocalDateTime;

public interface MonitorListener {

	public void commandExecuted(String command, LocalDateTime time);
	
}
