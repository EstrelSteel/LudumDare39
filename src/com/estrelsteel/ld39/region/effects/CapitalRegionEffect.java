package com.estrelsteel.ld39.region.effects;

public class CapitalRegionEffect extends RegionEffect {

	public CapitalRegionEffect() {
		super("Capital Region");
	}

	@Override
	public double getRebelRate() {
		return -0.01;
	}
	
}
