package com.markipol.realcooking.capabilites.foodstats;

public class FoodStats implements IFoodStats {
	private int foodLevel;
	private float foodSaturation;
	private float foodExhaustion;
	
	public FoodStats() {
		this.storeLevel(-1);
		this.storeExhaustion(-1f);
		this.storeSaturation(-1f);
	}
	public FoodStats(int level, int exhaustion, int saturation) {
		this.storeLevel(level);
		this.storeExhaustion(exhaustion);
		this.storeSaturation(saturation);
	}
	
	@Override
	public void storeLevel(int foodLevel) {
		this.foodLevel = foodLevel;
	}
	@Override
	public void storeSaturation(float foodSaturation) {
		this.foodSaturation = foodSaturation;
	}
	@Override
	public void storeExhaustion(float foodExhaustion) {
		this.foodExhaustion = foodExhaustion;
	}
	@Override
	public int retrieveLevel() {
		
		return this.foodLevel;
	}
	@Override
	public float retrieveSaturation() {
		
		return this.foodSaturation;
	}
	@Override
	public float retrieveExhaustion() {
		
		return this.foodExhaustion;
	}
	@Override
	public void reset() {
		this.foodLevel = -1;
		this.foodExhaustion = -1f;
		this.foodSaturation = -1f;
	}
	

}