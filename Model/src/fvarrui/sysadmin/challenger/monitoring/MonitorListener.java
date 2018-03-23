package fvarrui.sysadmin.challenger.monitoring;

import java.util.Map;

public interface MonitorListener {

	public void notify(Map<String,Object> data);
	
}
