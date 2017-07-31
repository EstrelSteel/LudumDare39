package com.estrelsteel.ld39.events;

import java.util.Collection;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.ld39.Hud;
import com.estrelsteel.ld39.region.Empire;
import com.estrelsteel.ld39.region.Region;
import com.estrelsteel.ld39.region.effects.PlayerChoiceRegionEffect;

public class PropagandaEvent extends MonthlyEvent {

	private Empire empire;
	private Collection<Region> regions;
	private int cost;
	private double rr;
	private int rebel;
	
	public PropagandaEvent(Empire empire, Collection<Region> regions, int cost, double rr, int rebel) {
		super("The minister of propaganda has died. Do you find a new one, costing $" + Hud.stringPopulation(cost) + " and reducing rebel recruitment by " 
				+ Engine2.ROUNDING_FORMAT.format(rr) + "% or do you disolve the office, \nwith " + Hud.stringPopulation(rebel) + " rebels leaving the rebel cause in each region"
				+ "due to your good faith \nbut rebel recruitment increasing by " + Engine2.ROUNDING_FORMAT.format(rr) + "%?\nWill you appoint a new person?", 
				EventScope.EMPIRE, EventType.TRUE_FALSE);
		this.empire = empire;
		this.cost = cost;
		this.rr = rr;
		this.regions = regions;
		this.rebel = rebel;
	}
	
	public int getCost() {
		return cost;
	}
	
	public double getRebelRecruitment() {
		return rr;
	}
	
	public int getRebel() {
		return rebel;
	}

	@Override
	public void answerEvent(int option) {
		if(option == 0) {
			empire.setTreasury(empire.getTreasury() - cost);
			for(Region r : regions) {
				r.getRegionEffects().add(new PlayerChoiceRegionEffect("Minister of Propaganda Re-appointed", -rr));
				r.updateRebelRate();
			}
			setDone(true);
		}
		else {
			for(Region r : regions) {
				r.setRebel(r.getRebel() - rebel);
				if(r.getRebel() < 0) {
					r.setRebel(0);
				}
				r.updateStatus();
				r.getRegionEffects().add(new PlayerChoiceRegionEffect("Minister of Propaganda Disolved", rr));
				r.updateRebelRate();
			}
			setDone(true);
		}
	}

}
