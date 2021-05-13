package com.markipol.realcooking.capabilites.foodstats;

import java.util.concurrent.Callable;

public class FoodStatsFactory implements Callable<IFoodStats> {
	  @Override
	  public IFoodStats call() throws Exception {
	    return new FoodStats();
	  }
	}
