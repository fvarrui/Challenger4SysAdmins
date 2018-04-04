package fvarrui.sysadmin.editor.components.tree;

import fvarrui.sysadmin.challenger.command.Command;
import javafx.event.Event;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Logica de negocio que gestiona los item de tipo comando
 * @author Fran Vargas
 * @version 1.0
 *
 */

public class CommandTreeItem extends TreeItem<Object> {
	
	private static final Image COMMAND = new Image("/fvarrui/sysadmin/editor/ui/resources/command-16x16.png");

	public CommandTreeItem(Command value) {
		super(value);
		setExpanded(true);
		setGraphic(new ImageView(COMMAND));
		getCommand().executableProperty().addListener(e -> {
			TreeModificationEvent<Object> ev = new TreeModificationEvent<>(valueChangedEvent(), this);
	        Event.fireEvent(this, ev);
		});
	}
	
	public Command getCommand() {
		return (Command) getValue();
	}
	
}
