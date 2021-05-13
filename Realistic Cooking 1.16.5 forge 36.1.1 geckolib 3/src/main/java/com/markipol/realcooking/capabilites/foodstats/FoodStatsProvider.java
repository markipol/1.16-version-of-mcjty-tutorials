package com.markipol.realcooking.capabilites.foodstats;

import javax.annotation.Nonnull;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class FoodStatsProvider implements ICapabilitySerializable<CompoundNBT> {
//	public int foodLevel = -1;
//	public float foodSaturation = -1f;
//	public float foodExhaustion = -1f;
	@CapabilityInject(IFoodStats.class)
	public static final Capability<IFoodStats> STATS_CAP = null;
	private LazyOptional<IFoodStats> instance = LazyOptional.of(STATS_CAP::getDefaultInstance);

//	private IFoodStats instance = STATS_CAP.getDefaultInstance();
//	private final LazyOptional<IFoodStats> holder = LazyOptional.of(() -> {
//		return this;
//	});

	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {

		if (cap == STATS_CAP) {
			return instance.cast();
		} else {
			return LazyOptional.empty();
		}
	}

	

//
//	@Override
//	public void storeLevel(int foodLevel) {
//		this.foodLevel = foodLevel;
//	}
//	@Override
//	public void storeSaturation(float foodSaturation) {
//		this.foodSaturation = foodSaturation;
//	}
//	@Override
//	public void storeExhaustion(float foodExhaustion) {
//		this.foodExhaustion = foodExhaustion;
//	}
//	@Override
//	public int retrieveLevel() {
//		
//		return this.foodLevel;
//	}
//	@Override
//	public float retrieveSaturation() {
//		
//		return this.foodSaturation;
//	}
//	@Override
//	public float retrieveExhaustion() {
//		
//		return this.foodExhaustion;
//	}
//	@Override
//	public void reset() {
//		this.foodLevel = -1;
//		this.foodExhaustion = -1f;
//		this.foodSaturation = -1f;
//	}

	@Override
	public CompoundNBT serializeNBT() {

//		CompoundNBT nbt = new CompoundNBT();
//		nbt.putInt("fixedFoodLevel", this.foodLevel);
//		nbt.putFloat("fixedFoodSaturation", this.foodSaturation);
//		nbt.putFloat("fixedFoodExhaustion", this.foodExhaustion);
//
//		return nbt;
		
		return (CompoundNBT) STATS_CAP.getStorage().writeNBT(STATS_CAP, instance.orElseThrow(() -> new NullPointerException()), null);
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
//		CompoundNBT cnbt = (CompoundNBT) nbt;
//		this.foodLevel = cnbt.getInt("fixedFoodLevel");
//		this.foodSaturation = cnbt.getFloat("fixedFoodSaturation");
//		this.foodExhaustion = cnbt.getFloat("fixedFoodExhaustion");
		
		STATS_CAP.getStorage().readNBT(STATS_CAP, instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional is empty")), null, nbt);
	}

}
