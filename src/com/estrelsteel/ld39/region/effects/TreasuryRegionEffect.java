package com.estrelsteel.ld39.region.effects;

public class TreasuryRegionEffect extends RegionEffect {

	private int bank;
	
	public TreasuryRegionEffect(int bank) {
		super("Treasury");
		this.bank = bank;
	} 
	
	public int getTreasury() {
		return bank;
	}
	
	public double getRebelRate() {
		return 0.000000009 * Math.pow(bank - 10000, 2) / 100;
	}
	
	public double updateRebelRate(int bank) {
		setTreasury(bank);
		return getRebelRate();
	}
	
	public void setTreasury(int bank) {
		this.bank = bank;
	}

}
