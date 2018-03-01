package fvarrui.sysadmin.editor.components;

import fvarrui.sysadmin.challenger.Challenge;
import fvarrui.sysadmin.challenger.Goal;
import fvarrui.sysadmin.challenger.command.Command;
import fvarrui.sysadmin.challenger.test.CommandTest;
import fvarrui.sysadmin.challenger.test.CompoundTest;
import fvarrui.sysadmin.challenger.test.Test;
import javafx.collections.ListChangeListener;
import javafx.scene.control.TreeItem;

public class TreeItemFactory {
	

	public static TreeItem<Object> createChallengeTreeItem(Challenge challenge) {
		TreeItem<Object> challengeItem = new TreeItem<Object>();
		challengeItem.setExpanded(true);
		challengeItem.setValue(challenge);
		for (Goal goal : challenge.getGoals()) {
			challengeItem.getChildren().add(createGoalTreeItem(goal));
		}
		challenge.goalsProperty().addListener(new ListChangeListener<Goal>() {
			public void onChanged(Change<? extends Goal> c) {
				while (c.next()) {
					c.getAddedSubList().stream().forEach(g -> challengeItem.getChildren().add(createGoalTreeItem(g)));
					c.getRemoved().stream().forEach(g -> {
						TreeItem<Object> item = challengeItem.getChildren().stream().filter(i -> i.getValue().equals(g)).findFirst().get();
						challengeItem.getChildren().remove(item);
					});
				}
			}
		});
		return challengeItem;
	}

	public static TreeItem<Object> createGoalTreeItem(Goal goal) {
		TreeItem<Object> goalItem = new TreeItem<Object>();
		goalItem.setExpanded(true);
		goalItem.setValue(goal);
		if (goal.getTest() != null) { 
			goalItem.getChildren().add(createTestTreeItem(goal.getTest()));
		}
		goal.testProperty().addListener((o, ov, nv) -> {
			goalItem.getChildren().clear();
			if (nv != null) {
				goalItem.getChildren().add(createTestTreeItem(nv));
			}
		});
		return goalItem;
	}

	public static TreeItem<Object> createTestTreeItem(Test test) {
		TreeItem<Object> testItem = new TreeItem<Object>();
		testItem.setExpanded(true);
		testItem.setValue(test);
		if (test instanceof CompoundTest) {
			CompoundTest ct = (CompoundTest) test;
			for (Test t : ct.getTests()) {
				testItem.getChildren().add(createTestTreeItem(t));
			}
		} else if (test instanceof CommandTest) {
			CommandTest ct = (CommandTest) test;
			if (ct.getCommand() != null) {
				testItem.getChildren().add(createCommandTreeItem(ct.getCommand()));
			}
			ct.commandProperty().addListener((o, ov, nv) -> {
				if (nv != null) {
					testItem.getChildren().add(createCommandTreeItem(ct.getCommand()));
				} else {
					testItem.getChildren().clear();
				}
			});
		}
		return testItem;
	}

	public static TreeItem<Object> createCommandTreeItem(Command command) {
		TreeItem<Object> commandItem = new TreeItem<Object>();
		commandItem.setExpanded(true);
		commandItem.setValue(command);
		return commandItem;
	}
	
}
