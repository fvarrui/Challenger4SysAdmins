package fvarrui.sysadmin.challenger.monitoring;

import java.util.Map;

public interface MonitorListener {

	public void notify(Monitor monitor, Map<String,Object> data);
	
}
