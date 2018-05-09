package fvarrui.sysadmin.challenger.monitoring;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Monitor extends Thread {
	
	private volatile boolean stop;
	private List<MonitorListener> listeners;

	public Monitor(String name) {
		super(name);
		listeners = new ArrayList<>();
		setDaemon(true);
	}

	@Override
	public void run() {
		stop = false;
		System.out.println(String.format("monitor %s started", getName()));
		doWork();
		System.out.println(String.format("monitor %s stopped", getName()));
	}
	
	protected abstract void doWork();

	public boolean isStopped() {
		return stop;
	}

	public void requestStop() {
		System.out.println(String.format("request stop monitor %s", getName()));
		this.stop = true;
	}
	
	public void addListener(MonitorListener listener) {
		this.listeners.add(listener);
	}
	
	public void removeListener(MonitorListener listener) {
		this.listeners.remove(listener);
	}
	
	protected void notifyAll(Map<String, Object> data) {
		new Thread(() -> {
			for (MonitorListener listener : listeners) {
				listener.notify(this, data);
			}
		}).start();
	}

}
