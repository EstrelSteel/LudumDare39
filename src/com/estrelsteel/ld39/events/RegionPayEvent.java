package com.estrelsteel.ld39.events;

import javax.swing.JOptionPane;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.ld39.Hud;
import com.estrelsteel.ld39.region.Empire;
import com.estrelsteel.ld39.region.Region;
import com.estrelsteel.ld39.region.effects.PlayerChoiceRegionEffect;

public class RegionPayEvent extends MonthlyEvent {

	private Empire empire;
	private Region r;
	private int cost;
	private int s_cost;
	private double rebel;
	
	public RegionPayEvent(Empire empire, Region r, int cost, int s_cost, double rebel) {
		super("Infrastructure in Region " + (r.getID() + 1) + " has decayed to a point where it is nearly unusable. Repairs will cost $" + Hud.stringPopulation(cost) + " and " + Hud.stringPopulation(s_cost)
				+ " soldiers to build.\nNot repairing will result in slowed military response time, and thus rebel recruitment will increase by " + rebel * 100 + "%."
				+ ".\nWill you repair the infrastructure? (Current stability: " + r.getStatus() + "; current rebel recruitment: " + Engine2.ROUNDING_FORMAT.format(r.getRebelRate() * 100)
				+ "%).", EventScope.REGION, EventType.TRUE_FALSE);
		this.empire = empire;
		this.r = r;
		this.cost = cost;
		this.s_cost = s_cost;
		this.rebel = rebel;
	}
	
	public int getCost() {
		return cost;
	}
	
	public int getSoldierCost() {
		return s_cost;
	}
	
	public double getRebelRate() {
		return rebel;
	}
	
	public Region getRegion() {
		return r;
	}

	@Override
	public void answerEvent(int option) {
		if(option == 0) {
			empire.setTreasury(empire.getTreasury() - cost);
			empire.setTotalSoldiers(empire.getTotalSoldiers() - s_cost);
			if(empire.getFreeSoldiers() < 0) {
				if(!empire.correctFreeSoldiers()) {
					JOptionPane.showMessageDialog(null, "You cannot pick this option.");
					empire.setTotalSoldiers(empire.getTotalSoldiers() + s_cost);
					answerEvent(option + 1);
					return;
				}
			}
			setDone(true);
		}
		else {
			r.getRegionEffects().add(new PlayerChoiceRegionEffect("Infrastructure Repair", rebel));
			setDone(true);
		}
	}

}
