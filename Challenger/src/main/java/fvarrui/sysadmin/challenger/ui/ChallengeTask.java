package fvarrui.sysadmin.challenger.ui;

import fvarrui.sysadmin.challenger.model.Challenge;
import fvarrui.sysadmin.challenger.model.Goal;
import fvarrui.sysadmin.challenger.monitoring.Monitoring;
import javafx.concurrent.Task;

public class ChallengeTask extends Task<Boolean> {
	
	private Challenge challenge;
	private volatile boolean stopped;
	
	public ChallengeTask(Challenge challenge) {
		super();
		this.challenge = challenge;
	}	

	@Override
	protected Boolean call() throws Exception {
		System.out.println("Iniciando reto " + challenge.getName());
		int goalIndex = 0;
		
		// 
		while (!stopped && goalIndex < challenge.getGoals().size() ) {
			
			Goal currentGoal = challenge.getGoals().get(goalIndex);
			
			currentGoal.subscribeTo(Monitoring.getExecutedCommands());
			
			while (!stopped && !currentGoal.verify()) {
				System.out.println(challenge.toString(0));
				Thread.sleep(1000L);
			}
			
			currentGoal.removeSubscription();
			
			goalIndex++;
		}
		System.out.println(challenge.toString(0));
		System.out.println("Reto parado");		
		return challenge.verify();
	}
	
	public void stop() {
		System.out.println("Parando el reto");
		this.stopped = true;
	}

}
