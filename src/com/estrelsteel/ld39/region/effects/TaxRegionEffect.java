package com.estrelsteel.ld39.region.effects;

public class TaxRegionEffect extends RegionEffect {

	private double tax_rate;
	
	public TaxRegionEffect(double tax_rate) {
		super("Taxes");
		this.tax_rate = tax_rate;
	} 
	
	public double getTaxRate() {
		return tax_rate;
	}
	
	public double getRebelRate() {
		return (Math.pow(1.015, tax_rate * 100) - 1) / 1000;
	}
	
	public double updateRebelRate(double tax_rate) {
		setTaxRate(tax_rate);
		return getRebelRate();
	}
	
	public void setTaxRate(double tax_rate) {
		this.tax_rate = tax_rate;
	}

}
