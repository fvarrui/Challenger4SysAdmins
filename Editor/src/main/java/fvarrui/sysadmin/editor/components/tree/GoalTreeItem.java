package fvarrui.sysadmin.editor.components.tree;

import fvarrui.sysadmin.challenger.model.Goal;
import javafx.event.Event;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * Logica de negocio que gestiona los item de tipo goal
 * @author Fran Vargas
 * @version 1.0
 *
 */
public class GoalTreeItem extends TreeItem<Object> {
	
	private static final Image GOAL = new Image("/fvarrui/sysadmin/editor/ui/resources/goal-16x16.png");

	public GoalTreeItem(Goal value) {
		super(value);
		setExpanded(true);
		setGraphic(new ImageView(GOAL));
		
		if (getGoal().getTest() != null) { 
			getChildren().add(new TestTreeItem(getGoal().getTest()));
		}
		
		getGoal().testProperty().addListener((o, ov, nv) -> {
			getChildren().clear();
			if (nv != null) {
				getChildren().add(new TestTreeItem(nv));
			}
		});
		
		getGoal().nameProperty().addListener(e -> {
			TreeModificationEvent<Object> ev = new TreeModificationEvent<>(valueChangedEvent(), this);
	        Event.fireEvent(this, ev);
		});
	}
	
	public Goal getGoal() {
		return (Goal) getValue();
	}
	
}
