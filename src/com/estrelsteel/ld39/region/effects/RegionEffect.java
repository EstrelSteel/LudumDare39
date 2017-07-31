package com.estrelsteel.ld39.region.effects;

public abstract class RegionEffect {
	
	private String name;
	
	public RegionEffect(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public abstract double getRebelRate();
	
	public void setName(String name) {
		this.name = name;
	}
}
