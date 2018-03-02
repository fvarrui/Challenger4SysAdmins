package fvarrui.sysadmin.editor.components;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
<<<<<<< HEAD

public class MenuBuilder {

	public static ContextMenu getMenu(TreeItem<String> itemSelecterd) {
=======
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Logica de negocio para la gestion de los menus contextuales.
 * 
 * @author Ricardo Vargas
 * @version 1.0
 */

public class MenuBuilder {

	/**
	 * 
	 * @param itemSelected
	 *            elemento que tengamos seleccionado.
	 * @return menu contextual correspondiente al tipo del objeto seleccionado.
	 */
	public static ContextMenu getMenu(TreeItem<Object> itemSelected) {

		
>>>>>>> 7afdb606eb5ff55c3880a5f4dbdef57dacedc15a

		ContextMenu contextMenu = new ContextMenu();
		ListProperty<MenuItem> menuItems = new SimpleListProperty<>(FXCollections.observableArrayList());

<<<<<<< HEAD
		if (itemSelecterd.isLeaf()) {
			menuItems.addAll(new MenuItem("Nuevo Test"), new MenuItem("Editar Test"), new MenuItem("Eliminar Test"));
			contextMenu.getItems().addAll(menuItems);
		} else {
			menuItems.addAll(new MenuItem("Nuevo Goal"), new MenuItem("Editar Goal"), new MenuItem("Eliminar Goal"));
			contextMenu.getItems().setAll(menuItems);
		}

		return contextMenu;
	}
=======
		if (itemSelected.getParent() == null) {
			
			
			menuItems.add(new MenuItem("Añadir Goal",new ImageView(new Image("/fvarrui/sysadmin/editor/ui/resources/mas-16x16.png"))));
			menuItems.add(new MenuItem("Eliminar Goal",new ImageView(new Image("/fvarrui/sysadmin/editor/ui/resources/menos-16x16.png"))));	
			contextMenu.getItems().addAll(menuItems);

		}else {
			
			menuItems.add(new MenuItem("Añadir Test",new ImageView(new Image("/fvarrui/sysadmin/editor/ui/resources/mas-16x16.png"))));
			menuItems.add(new MenuItem("Eliminar Test",new ImageView(new Image("/fvarrui/sysadmin/editor/ui/resources/menos-16x16.png"))));	
			contextMenu.getItems().addAll(menuItems);
		}
		
		return contextMenu;
	}

>>>>>>> 7afdb606eb5ff55c3880a5f4dbdef57dacedc15a
}
