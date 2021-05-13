package com.markipol.realcooking.capabilites.frustration;

public class FrustrationLevel implements IFrustrationLevel{
	private int frustration;
	public FrustrationLevel() {
		this.setFrustration(9001);
	}

	@Override
	public void setFrustration(int frustration) {
		this.frustration = frustration;
	}

	@Override
	public int getFrustration() {
		return this.frustration; 
	}

}
