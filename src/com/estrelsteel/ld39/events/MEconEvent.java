package com.estrelsteel.ld39.events;

import java.util.Collection;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.ld39.Hud;
import com.estrelsteel.ld39.region.Empire;
import com.estrelsteel.ld39.region.Region;
import com.estrelsteel.ld39.region.effects.PlayerChoiceRegionEffect;

public class MEconEvent extends MonthlyEvent {

	private Empire empire;
	private Collection<Region> regions;
	private int profit;
	private double rr;
	
	public MEconEvent(Empire empire, Collection<Region> regions, int profit, double rr) {
		super("The minister of the economy has been caught stealling money, but he has been distributing the money to the common people.\n"
				+ " Do you want to retake $" + Hud.stringPopulation(profit) + " of your stolen taxes and increase rebel recruitment by" +  Engine2.ROUNDING_FORMAT.format(rr * 100)
				+ " or do you allow him to continue?", EventScope.EMPIRE, EventType.TRUE_FALSE);
		this.empire = empire;
		this.profit = profit;
		this.rr = rr;
		this.regions = regions;
	}
	
	public int getProfit() {
		return profit;
	}
	
	public double getRebelRecruitment() {
		return rr;
	}

	@Override
	public void answerEvent(int option) {
		if(option == 0) {
			empire.setTreasury(empire.getTreasury() + profit);
			for(Region r : regions) {
				r.getRegionEffects().add(new PlayerChoiceRegionEffect("Minister of the Economy Arrested", rr));
				r.updateRebelRate();
			}
			setDone(true);
		}
		else {
			setDone(true);
		}
	}

}
