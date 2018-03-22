package fvarrui.sysadmin.challenger.monitoring;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class Monitor extends Thread {
	
	private volatile boolean stop;
	private List<MonitorListener> listeners;

	public Monitor(String name) {
		super(name);
		listeners = new ArrayList<>();
	}

	@Override
	public void run() {
		stop = false;
		System.out.println(String.format("monitor %s iniciado", getName()));
		doWork();
		System.out.println(String.format("monitor %s detenido", getName()));
	}
	
	protected abstract void doWork();

	public boolean isStopped() {
		return stop;
	}

	public void requestStop() {
		System.out.println(String.format("solicitando parada del monitor %s", getName()));
		this.stop = true;
	}
	
	public void addListener(MonitorListener listener) {
		this.listeners.add(listener);
	}
	
	public void removeListener(MonitorListener listener) {
		this.listeners.remove(listener);
	}
	
	protected void notify(String command, LocalDateTime time) {
		for (MonitorListener listener : listeners) {
			listener.commandExecuted(command, time);
		}
	}

}
