package com.estrelsteel.ld39.region;

import java.util.Collection;

public class Empire {
	
	private int population;
	private int rebels;
	private int stability;
	private int treasury;
	private double tax;
	private int total_soldiers;
	private int active_soldiers;
	private Collection<Region> regions;
	
	public Empire(Collection<Region> regions) {
		updatePopulation(regions);
		treasury = 1000;
		total_soldiers = 1000;
		tax = .30;
		
		this.regions = regions;
	}
	
	public int getPopulation() {
		return population;
	}
	
	public int getRebels() {
		return rebels;
	}
	
	public int getStability() {
		return stability;
	}
	
	public int getTreasury() {
		return treasury;
	}
	
	public double getTaxes() {
		return tax;
	}
	
	public int getTotalSoldiers() {
		return total_soldiers;
	}
	
	public int getFreeSoldiers() {
		return total_soldiers - active_soldiers;
	}
	
	public int getActiveSoldiers() {
		return active_soldiers;
	}
	
	public int getIncome() {
		return (int) ((population - rebels) * tax);
	}
	
	public Collection<Region> getRegions() {
		return regions;
	}
	
	public int updatePopulation(Collection<Region> regions) {
		population = 0;
		for(Region r : regions) {
			if(r.getStatus() != Status.INDEPENDENT) {
				population = population + r.getPopulation();
			}
		}
		return population;
	}
	
	public int updateStability(Collection<Region> regions) {
		int tot_valid = 0;
		int c = 0;
		for(Region r : regions) {
			if(r.getStatus() != Status.INDEPENDENT) {
				c = c + r.getStatus().ordinal();
				tot_valid++;
			}
		}
		stability = c / tot_valid;
		return stability;
	}
	
	public int updateActiveSoldiers(Collection<Region> regions) {
		active_soldiers = 0;
		for(Region r : regions) {
			if(r.getStatus() != Status.INDEPENDENT) {
				active_soldiers = active_soldiers + r.getSoldiers();
			}
		}
		return active_soldiers;
	}
	
	public int updateRebels(Collection<Region> regions) {
		rebels = 0;
		for(Region r : regions) {
			if(r.getStatus() != Status.INDEPENDENT) {
				rebels = rebels + r.getRebel();
			}
		}
		return rebels;
	}
	
	public boolean correctFreeSoldiers() {
		for(Region r : regions) {
			if(r.getStatus() != Status.INDEPENDENT  && r.getSoldiers() > getFreeSoldiers() * -1) {
				r.setSoldiers(r.getSoldiers() + getFreeSoldiers());
				return true;
			}
		}
		return false;
	}
	
	public void setTreasury(int treasury) {
		this.treasury = treasury; 
	}
	
	public void setTaxes(double tax) {
		this.tax = tax;
	}
	
	public void setTotalSoldiers(int total_soldiers) {
		this.total_soldiers = total_soldiers;
	}
	
	public void setRebels(int rebels) {
		this.rebels = rebels;
	}
	
	public void setRegions(Collection<Region> regions) {
		this.regions = regions;
	}
}
