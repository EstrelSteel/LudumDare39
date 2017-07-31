package com.estrelsteel.ld39.events;

import java.util.Collection;

import com.estrelsteel.ld39.Hud;
import com.estrelsteel.ld39.region.Empire;
import com.estrelsteel.ld39.region.Region;
import com.estrelsteel.ld39.region.effects.PlayerChoiceRegionEffect;

public class GamesEvent extends MonthlyEvent {

	private Empire empire;
	private Collection<Region> regions;
	private int cost;
	private double rebel;
	
	public GamesEvent(Empire empire, Collection<Region> regions, int cost, double rebel) {
		super("Your empire has been selected to host an international games. Hosting will cost $" + Hud.stringPopulation(cost) + " and rebel recruitment will go down.\n"
				+ "Will you host the games?", EventScope.EMPIRE, EventType.TRUE_FALSE);
		this.empire = empire;
		this.cost = cost;
		this.rebel = rebel;
		this.regions = regions;
	}
	
	public int getCost() {
		return cost;
	}
	
	public double getRebelRate() {
		return rebel;
	}

	@Override
	public void answerEvent(int option) {
		if(option == 0) {
			empire.setTreasury(empire.getTreasury() - cost);
			for(Region r : regions) {
				r.getRegionEffects().add(new PlayerChoiceRegionEffect("Hosting Games", rebel));
			}
			setDone(true);
		}
		else {
			setDone(true);
		}
	}

}
