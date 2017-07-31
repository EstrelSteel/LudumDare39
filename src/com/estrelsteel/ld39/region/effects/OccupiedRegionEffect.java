package com.estrelsteel.ld39.region.effects;

public class OccupiedRegionEffect extends RegionEffect {

	private double rebel_rate;
	
	public OccupiedRegionEffect(double rebel_rate) {
		super("Occupied Region");
		this.rebel_rate = rebel_rate;
	}

	@Override
	public double getRebelRate() {
		return rebel_rate;
	}
}
