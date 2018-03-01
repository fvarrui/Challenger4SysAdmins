package fvarrui.sysadmin.editor.components;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;

public class MenuBuilder {

	public static ContextMenu getMenu(TreeItem<String> itemSelecterd) {

		ContextMenu contextMenu = new ContextMenu();
		ListProperty<MenuItem> menuItems = new SimpleListProperty<>(FXCollections.observableArrayList());

		if (itemSelecterd.isLeaf()) {
			menuItems.addAll(new MenuItem("Nuevo Test"), new MenuItem("Editar Test"), new MenuItem("Eliminar Test"));
			contextMenu.getItems().addAll(menuItems);
		} else {
			menuItems.addAll(new MenuItem("Nuevo Goal"), new MenuItem("Editar Goal"), new MenuItem("Eliminar Goal"));
			contextMenu.getItems().setAll(menuItems);
		}

		return contextMenu;
	}
}
