package com.estrelsteel.ld39.region.effects;

public class PlayerChoiceRegionEffect extends RegionEffect {

	private double rebel_rate;
	
	public PlayerChoiceRegionEffect(String name, double rebel_rate) {
		super(name);
	}

	@Override
	public double getRebelRate() {
		return rebel_rate;
	}

}
