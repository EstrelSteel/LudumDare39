package com.estrelsteel.ld39.region.effects;

import com.estrelsteel.ld39.region.Status;

public class EmpireStabilityRegionEffect extends RegionEffect {

	private Status s;
	
	public EmpireStabilityRegionEffect(Status s) {
		super("Empire Stability");
		this.s = s;
	}
	
	public Status getStatus() {
		return s;
	}

	@Override
	public double getRebelRate() {
		return (Math.pow(2, s.ordinal()) - 1) / 1000;
	}
	
	public double updateRebelRate(Status s) {
		setStatus(s);
		return getRebelRate();
	}
	
	public void setStatus(Status s) {
		this.s = s;
	}

}
