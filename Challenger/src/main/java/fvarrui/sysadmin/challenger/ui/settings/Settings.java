package fvarrui.sysadmin.challenger.ui.settings;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Settings {

	private StringProperty os;
	private BooleanProperty enableShellMonitoring;

	public Settings() {
		os = new SimpleStringProperty(this, "os");
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

	public final StringProperty osProperty() {
		return this.os;
	}

	public final String getOs() {
		return this.osProperty().get();
	}

	public final void setOs(final String os) {
		this.osProperty().set(os);
	}

}
