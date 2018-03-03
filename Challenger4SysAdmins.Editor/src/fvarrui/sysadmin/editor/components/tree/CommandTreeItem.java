package fvarrui.sysadmin.editor.components.tree;

import fvarrui.sysadmin.challenger.command.Command;
import javafx.event.Event;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CommandTreeItem extends TreeItem<Object> {
	
	private static final Image COMMAND = new Image("/fvarrui/sysadmin/editor/ui/resources/command-16x16.png");

	public CommandTreeItem(Command value) {
		super(value);
		setExpanded(true);
		setGraphic(new ImageView(COMMAND));
		getCommand().commandProperty().addListener(e -> {
			TreeModificationEvent<Object> ev = new TreeModificationEvent<>(valueChangedEvent(), this);
	        Event.fireEvent(this, ev);
		});
	}
	
	public Command getCommand() {
		return (Command) getValue();
	}
	
}
