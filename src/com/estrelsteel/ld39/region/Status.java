package com.estrelsteel.ld39.region;

public enum Status {
	LOYAL(.025), STABLE(.05), PROTESTS(.1), RIOTS(.2), REVOLUTION(.3), INDEPENDENT(.5);
	
	private double rebel_percent;
	
	Status(double rebel_percent) {
		this.rebel_percent = rebel_percent;
	}
	
	public double getRebelPercentage() {
		return rebel_percent;
	}
}
