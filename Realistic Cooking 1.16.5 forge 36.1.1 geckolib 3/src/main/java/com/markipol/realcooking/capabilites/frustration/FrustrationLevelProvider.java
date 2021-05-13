package com.markipol.realcooking.capabilites.frustration;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class FrustrationLevelProvider implements ICapabilitySerializable<CompoundNBT> {
	@CapabilityInject(IFrustrationLevel.class)
	public static Capability<IFrustrationLevel> FRUSTRATION_LEVEL = null;
	private LazyOptional<IFrustrationLevel> instance = LazyOptional.of(FRUSTRATION_LEVEL::getDefaultInstance);

	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {

		if (cap== FRUSTRATION_LEVEL) {
			return instance.cast();
		} else {
			return LazyOptional.empty();
		}
	}

	@Override
	public CompoundNBT serializeNBT() {

		return (CompoundNBT) FRUSTRATION_LEVEL.getStorage().writeNBT(FRUSTRATION_LEVEL, instance.orElseThrow(() -> new NullPointerException()), null);
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		FRUSTRATION_LEVEL.getStorage().readNBT(FRUSTRATION_LEVEL, instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional is empty")), null, nbt);
	}

}
