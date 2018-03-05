package fvarrui.sysadmin.editor.components.tree;

import fvarrui.sysadmin.challenger.Challenge;
import fvarrui.sysadmin.challenger.Goal;
import javafx.collections.ListChangeListener;
import javafx.event.Event;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * Logica de negocio que gestiona el estado inicial de el arbol
 * @author Fran Vargas
 * @version 1.0
 *
 */
public class ChallengeTreeItem extends TreeItem<Object> {
	
	private static final Image CHALLENGE = new Image("/fvarrui/sysadmin/editor/ui/resources/challenge-16x16.png");

	public ChallengeTreeItem(Challenge value) {
		super(value);
		setExpanded(true);
		setGraphic(new ImageView(CHALLENGE));
		
		for (Goal goal : getChallenge().getGoals()) {
			getChildren().add(new GoalTreeItem(goal));
		}
		
		getChallenge().goalsProperty().addListener(new ListChangeListener<Goal>() {
			public void onChanged(Change<? extends Goal> c) {
				while (c.next()) {
					c.getAddedSubList().stream().forEach(g -> getChildren().add(new GoalTreeItem(g)));
					c.getRemoved().stream().forEach(g -> {
						TreeItem<Object> item = getChildren().stream().filter(i -> i.getValue().equals(g)).findFirst().get();
						getChildren().remove(item);
					});
				}
			}
		});
		
		getChallenge().nameProperty().addListener(e -> {
			TreeModificationEvent<Object> ev = new TreeModificationEvent<>(valueChangedEvent(), this);
	        Event.fireEvent(this, ev);
		});
		
	}
	
	public Challenge getChallenge() {
		return (Challenge) getValue();
	}
	
}
