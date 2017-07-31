package com.estrelsteel.ld39.events;

import com.estrelsteel.ld39.Hud;
import com.estrelsteel.ld39.region.Empire;

public class TutorialEvent extends MonthlyEvent {

	private Empire empire;
	private int profit;
	
	public TutorialEvent(Empire empire, int profit) {
		super("You found $" + Hud.stringPopulation(profit) + " in a hidden vault.", EventScope.EMPIRE, EventType.MESSAGE);
		this.empire = empire;
		this.profit = profit;
	}
	
	public int getProfit() {
		return profit;
	}

	@Override
	public void answerEvent(int option) {
		empire.setTreasury(empire.getTreasury() + profit);
		setDone(true);
	}

}
