package com.markipol.realcooking.capabilites.foodstats;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class FoodStatsStorage implements IStorage<IFoodStats>{

	@Override
	public INBT writeNBT(Capability<IFoodStats> capability, IFoodStats instance, Direction side) {
		CompoundNBT nbt = new CompoundNBT();
		nbt.putInt("fixedFoodLevel", instance.retrieveLevel());
		nbt.putFloat("fixedFoodSaturation", instance.retrieveSaturation());
		nbt.putFloat("fixedFoodExhaustion", instance.retrieveExhaustion());
		
		return nbt;
	}

	@Override
	public void readNBT(Capability<IFoodStats> capability, IFoodStats instance, Direction side, INBT nbt) {
		CompoundNBT cnbt = (CompoundNBT) nbt;
		instance.storeLevel(cnbt.getInt("fixedFoodLevel"));
		instance.storeSaturation(cnbt.getFloat("fixedFoodSaturation"));
		instance.storeExhaustion(cnbt.getFloat("fixedFoodExhaustion"));
	}

}
