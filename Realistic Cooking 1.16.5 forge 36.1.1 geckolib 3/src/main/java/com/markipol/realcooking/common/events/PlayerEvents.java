package com.markipol.realcooking.common.events;

import com.markipol.realcooking.RealCooking;
import com.markipol.realcooking.common.recipes.ThrownItemFromBlockRecipe;
import com.markipol.realcooking.common.recipes.ThrownItemFromBlockRecipeType;
import com.markipol.realcooking.core.init.BlockInit;
import com.markipol.realcooking.core.init.RecipeInit;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.items.ItemHandlerHelper;

@EventBusSubscriber(modid = RealCooking.MOD_ID, bus = Bus.FORGE)
public class PlayerEvents {
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event) {
		PlayerEntity player = event.player;
		player.inventory.armor.set(3, new ItemStack(Items.GOLDEN_HELMET));
	}
	@SubscribeEvent
	@SuppressWarnings(value = { "" })
	public static void onPlayerToss(ItemTossEvent event) {
		PlayerEntity player = (ServerPlayerEntity) event.getPlayer();
		World world = player.level;
		BlockState state = world.getBlockState(player.blockPosition().below());
		//first getitem() gets the stack (which can be more than 1), second getitem() gets the actual singular item it is 
		ItemStack item = event.getEntityItem().getItem();
		//loops over every custom thrown recipe
		for (final IRecipe<?> recipe : world.getRecipeManager().getAllRecipesFor(RecipeInit.THROWN)) {
			if (recipe instanceof ThrownItemFromBlockRecipe) {
			if(((ThrownItemFromBlockRecipe) recipe).isValid(item,state.getBlock())) {
				ItemHandlerHelper.giveItemToPlayer(player, recipe.getResultItem().copy());
				event.setCanceled(true);
			}}
		}

	}
	

}
