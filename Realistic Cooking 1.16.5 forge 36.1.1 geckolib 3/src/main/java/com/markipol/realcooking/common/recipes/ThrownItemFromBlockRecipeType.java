package com.markipol.realcooking.common.recipes;

import com.markipol.realcooking.RealCooking;

import net.minecraft.item.crafting.IRecipeType;

public class ThrownItemFromBlockRecipeType implements IRecipeType<ThrownItemFromBlockRecipe>{
	@Override
	public String toString() {
		return RealCooking.MOD_ID + ":thrown";
	}

}
