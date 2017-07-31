package com.estrelsteel.ld39.events;

import javax.swing.JOptionPane;

import com.estrelsteel.ld39.Hud;
import com.estrelsteel.ld39.region.Empire;

public class SoldierPayEvent extends MonthlyEvent {

	private Empire empire;
	private int cost;
	private int desert;
	
	public SoldierPayEvent(Empire empire, int cost, int desert) {
		super("Your soldiers demand a pay increase. This will cost you $" + Hud.stringPopulation(cost) + " for just this month.\nFailure to comply will result in " 
				+ Hud.stringPopulation(desert) + " soldiers deserting you.\nWill you pay?", EventScope.EMPIRE, EventType.TRUE_FALSE);
		this.empire = empire;
		this.cost = cost;
		this.desert = desert;
	}
	
	public int getCost() {
		return cost;
	}
	
	public int getDesert() {
		return desert;
	}

	@Override
	public void answerEvent(int option) {
		if(option == 0) {
			empire.setTreasury(empire.getTreasury() - cost);
			setDone(true);
		}
		else {
			empire.setTotalSoldiers(empire.getTotalSoldiers() - desert);
			if(!empire.correctFreeSoldiers()) {
				JOptionPane.showMessageDialog(null, "You cannot pick this option.");
				empire.setTotalSoldiers(empire.getTotalSoldiers() + desert);
				answerEvent(0);
				return;
			}
			setDone(true);
		}
	}

}
