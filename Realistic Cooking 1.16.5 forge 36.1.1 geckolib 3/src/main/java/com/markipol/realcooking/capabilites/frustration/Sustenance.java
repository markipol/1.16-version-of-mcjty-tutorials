package com.markipol.realcooking.capabilites.frustration;

public class Sustenance implements ISustenance{
	private int foodLevel;
	public Sustenance() {
		this.getFoodLevel(9001);
	}

	@Override
	public void getFoodLevel(int frustration) {
		this.foodLevel = frustration;
	}

	@Override
	public int getFoodLevel() {
		return this.foodLevel; 
	}

}
