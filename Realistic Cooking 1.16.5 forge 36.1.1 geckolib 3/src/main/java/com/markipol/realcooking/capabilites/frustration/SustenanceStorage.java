package com.markipol.realcooking.capabilites.frustration;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class SustenanceStorage implements IStorage<ISustenance>{

	@Override
	public INBT writeNBT(Capability<ISustenance> capability, ISustenance instance, Direction side) {
		
		final CompoundNBT cnbt = new CompoundNBT();
		cnbt.putInt("frustration", instance.getFoodLevel());
		return cnbt;
	}

	@Override
	public void readNBT(Capability<ISustenance> capability, ISustenance instance, Direction side,
			INBT nbt) {
		final CompoundNBT cnbt = (CompoundNBT) nbt;
		instance.getFoodLevel(cnbt.getInt("frustration"));
	}
	

}
