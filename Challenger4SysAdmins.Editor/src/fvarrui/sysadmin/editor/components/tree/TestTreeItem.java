package fvarrui.sysadmin.editor.components.tree;

import fvarrui.sysadmin.challenger.test.CommandTest;
import fvarrui.sysadmin.challenger.test.CompoundTest;
import fvarrui.sysadmin.challenger.test.NotTest;
import fvarrui.sysadmin.challenger.test.Test;
import javafx.collections.ListChangeListener;
import javafx.event.Event;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;



/**
 * Logica de negocio que gestiona los item de tipo test
 * @author Fran Vargas
 * @version 1.0
 *
 */
public class TestTreeItem extends TreeItem<Object> {
	
	private static final Image TEST = new Image("/fvarrui/sysadmin/editor/ui/resources/test-16x16.png");

	public TestTreeItem(Test value) {
		super(value);
		setExpanded(true);
		setGraphic(new ImageView(TEST));
		
		if (value instanceof CompoundTest) {
			
			CompoundTest compoundTest = (CompoundTest) value;
			for (Test childTest : compoundTest.getTests()) {
				getChildren().add(new TestTreeItem(childTest));
			}
			compoundTest.testsProperty().addListener(new ListChangeListener<Test>() {
				public void onChanged(Change<? extends Test> c) {
					while (c.next()) {
						c.getAddedSubList().stream().forEach(g -> getChildren().add(new TestTreeItem(g)));
						c.getRemoved().stream().forEach(g -> {
							TreeItem<Object> item = getChildren().stream().filter(i -> i.getValue().equals(g)).findFirst().get();
							getChildren().remove(item);
						});
					}
				}
			});

		} else if (value instanceof NotTest) {

			NotTest notTest = (NotTest) value;
			if (notTest.getTest() != null) {
				getChildren().add(new TestTreeItem(notTest.getTest()));
			}
			notTest.testProperty().addListener((o, ov, nv) -> {
				if (nv != null) {
					getChildren().add(new TestTreeItem(notTest.getTest()));
				} else {
					getChildren().clear();
				}
			});

		} else if (value instanceof CommandTest) {
			
			CommandTest commandTest = (CommandTest) value;
			if (commandTest.getCommand() != null) {
				getChildren().add(new CommandTreeItem(commandTest.getCommand()));
			}
			commandTest.commandProperty().addListener((o, ov, nv) -> {
				if (nv != null) {
					getChildren().add(new CommandTreeItem(commandTest.getCommand()));
				} else {
					getChildren().clear();
				}
			});
			
		}
		
		getTest().nameProperty().addListener(e -> {
			TreeModificationEvent<Object> ev = new TreeModificationEvent<>(valueChangedEvent(), this);
	        Event.fireEvent(this, ev);
		});
	}
	
	public Test getTest() {
		return (Test) getValue();
	}
	
}
