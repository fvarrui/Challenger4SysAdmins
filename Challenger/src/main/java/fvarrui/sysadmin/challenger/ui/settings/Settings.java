package fvarrui.sysadmin.challenger.ui.settings;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Settings {

	private BooleanProperty enableShellMonitoring;

	public Settings() {
		enableShellMonitoring = new SimpleBooleanProperty(this, "enableShellMonitoring");
	}

	public final BooleanProperty enableShellMonitoringProperty() {
		return this.enableShellMonitoring;
	}

	public final boolean isEnableShellMonitoring() {
		return this.enableShellMonitoringProperty().get();
	}

	public final void setEnableShellMonitoring(final boolean enableShellMonitoring) {
		this.enableShellMonitoringProperty().set(enableShellMonitoring);
	}

}
