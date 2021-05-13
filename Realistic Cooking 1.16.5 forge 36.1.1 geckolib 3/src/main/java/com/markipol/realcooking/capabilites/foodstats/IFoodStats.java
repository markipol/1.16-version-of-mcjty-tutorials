package com.markipol.realcooking.capabilites.foodstats;

public interface IFoodStats {
	public void storeLevel(int foodLevel);
	public void storeSaturation(float foodSaturation);
	public void storeExhaustion(float foodExhaustion);
	public int retrieveLevel();
	public float retrieveSaturation();
	public float retrieveExhaustion();
	
	public void reset();

}
