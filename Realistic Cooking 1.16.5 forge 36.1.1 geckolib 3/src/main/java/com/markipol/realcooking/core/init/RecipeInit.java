package com.markipol.realcooking.core.init;

import java.util.Map;

import com.markipol.realcooking.common.recipes.ThrownItemFromBlockRecipe;
import com.markipol.realcooking.common.recipes.ThrownItemFromBlockRecipeType;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent.Register;

public class RecipeInit {
	public static final IRecipeType<ThrownItemFromBlockRecipe> THROWN = new ThrownItemFromBlockRecipeType();

	public static void registerRecipes(Register<IRecipeSerializer<?>> event) {
		registerRecipe(event, THROWN, ThrownItemFromBlockRecipe.SERIALIZER);
	}

	private static void registerRecipe(
			Register<IRecipeSerializer<?>> event, 
			IRecipeType<?> type, 
			IRecipeSerializer<?> serializer) {
		Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(type.toString()), type);
		event.getRegistry().register(serializer);
	} 
	

}
	


