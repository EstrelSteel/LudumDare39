package com.estrelsteel.ld39.region.effects;

public class SoldiersRegionEffect extends RegionEffect {

	private int s;
	
	public SoldiersRegionEffect(int s) {
		super("Soldiers");
		this.s = s;
	}
	
	public int getSoldiers() {
		return s;
	}

	@Override
	public double getRebelRate() {
		return s * -0.00005;
	}
	
	public double updateRebelRate(int s) {
		setSoldiers(s);
		return getRebelRate();
	}
	
	public void setSoldiers(int s) {
		this.s = s;
	}

}
