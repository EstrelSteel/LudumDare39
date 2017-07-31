package com.estrelsteel.ld39.events;

import com.estrelsteel.ld39.Hud;
import com.estrelsteel.ld39.region.Empire;
import com.estrelsteel.ld39.region.Region;

public class MoneyClaimEvent extends MonthlyEvent {

	private Empire empire;
	private int c;
	private int rebel;
	private Region r;
	
	public MoneyClaimEvent(Empire empire, Region r, int c, int rebel) {
		super("The people of Region " + (r.getID() + 1) + " claim that officials made them pay $" + Hud.stringPopulation(c) + " extra in taxes. They demand you either pay them back\n"
				+ "or they will become rebels. Do you pay them back?", EventScope.EMPIRE, EventType.TRUE_FALSE);
		this.empire = empire;
		this.c = c;
		this.rebel = rebel;
		this.r = r;
	}
	
	public int getCost() {
		return c;
	}

	@Override
	public void answerEvent(int option) {
		if(option == 0) {
			empire.setTreasury(empire.getTreasury() - c);
			setDone(true);
		}
		else {
			r.setRebel(r.getRebel() + rebel);
			setDone(true);
		}
	}

}
