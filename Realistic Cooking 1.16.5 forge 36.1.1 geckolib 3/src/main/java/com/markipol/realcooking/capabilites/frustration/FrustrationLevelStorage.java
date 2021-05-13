package com.markipol.realcooking.capabilites.frustration;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class FrustrationLevelStorage implements IStorage<IFrustrationLevel>{

	@Override
	public INBT writeNBT(Capability<IFrustrationLevel> capability, IFrustrationLevel instance, Direction side) {
		
		final CompoundNBT cnbt = new CompoundNBT();
		cnbt.putInt("frustration", instance.getFrustration());
		return cnbt;
	}

	@Override
	public void readNBT(Capability<IFrustrationLevel> capability, IFrustrationLevel instance, Direction side,
			INBT nbt) {
		final CompoundNBT cnbt = (CompoundNBT) nbt;
		instance.setFrustration(cnbt.getInt("frustration"));
	}
	

}
