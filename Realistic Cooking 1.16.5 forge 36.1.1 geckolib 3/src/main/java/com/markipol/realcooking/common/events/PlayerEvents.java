package com.markipol.realcooking.common.events;

import java.util.logging.Logger;

import org.apache.logging.log4j.LogManager;

import com.markipol.realcooking.RealCooking;
import com.markipol.realcooking.capabilites.foodstats.FoodStatsProvider;
import com.markipol.realcooking.capabilites.frustration.FrustrationLevelProvider;
import com.markipol.realcooking.capabilites.frustration.IFrustrationLevel;
import com.markipol.realcooking.common.effects.NoHungerEffect;
import com.markipol.realcooking.common.recipes.ThrownItemFromBlockRecipe;
import com.markipol.realcooking.common.util.msg;
import com.markipol.realcooking.core.init.RecipeInit;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.FoodStats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.items.ItemHandlerHelper;

@EventBusSubscriber(modid = RealCooking.MOD_ID, bus = Bus.FORGE)
public class PlayerEvents {
	public static float exhaustionLevel;
	// public static final ResourceLocation FOOD_STATS_CAP = new
	// ResourceLocation(RealCooking.MOD_ID, "food_stats");

	// public FoodStatsProvider fsp = createFSP();

	// public LazyOptional<IFoodStats> foodstats = LazyOptional.of(() -> fsp);
	public static final ResourceLocation FOOD_STATS_CAP = new ResourceLocation(RealCooking.MOD_ID, "food_stats");
	public static final ResourceLocation FRUSTRATION_LEVEL = new ResourceLocation(RealCooking.MOD_ID,
			"frustration_level");
	public static int counter;
	// public static IFrustrationLevel iFrustrationLevel;

	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event) {
		PlayerEntity player = event.player;
		player.inventory.armor.set(3, new ItemStack(Items.GOLDEN_HELMET));
		if (!event.player.level.isClientSide && event.phase == TickEvent.Phase.END) {
			if (counter == 20) {
				for (EffectInstance effectInstance : player.getActiveEffects()) {
					if (effectInstance.getEffect() instanceof NoHungerEffect) {
						LazyOptional<IFrustrationLevel> frustrationCap = player
								.getCapability(FrustrationLevelProvider.FRUSTRATION_LEVEL);
						IFrustrationLevel iFrustrationLevel = frustrationCap.orElseThrow(NullPointerException::new);

						msg.send(player, "Frustration level: " + iFrustrationLevel.getFrustration());
						// LogManager.getLogger().info("ITS OVER:" +
						// Integer.toString(iFrustrationLevel.getFrustration()));

					}
					counter = 0;
				}
			} else
				counter++;
		}
	}

	private FoodStatsProvider createFSP() {

		return new FoodStatsProvider();
	}

//	@SubscribeEvent
//	public void attachCapability(AttachCapabilitiesEvent<Entity> event) {
//		if ((event.getObject().level.isClientSide())) {
//			return;
//		}
//		if (!(event.getObject() instanceof PlayerEntity))
//			return;
//		PlayerEntity player = (PlayerEntity) event.getObject();
//		if (!(player.getCapability(FoodStatsProvider.STATS_CAP).isPresent())) {
//			event.addCapability(FOOD_STATS_CAP, new FoodStatsProvider());
//		}
//		if (!(player.getCapability(FrustrationLevelProvider.FRUSTRATION_LEVEL).isPresent())) {
//			event.addCapability(FRUSTRATION_LEVEL, new FrustrationLevelProvider());
//		}
//		LogManager.getLogger().info("Capabilities event fired");
//
//	}

	@SubscribeEvent
	public void onPlayerLogsIn(PlayerLoggedInEvent event) {
		PlayerEntity player = event.getPlayer();
//		LazyOptional<IFoodStats> foodstats = player.getCapability(FoodStatsProvider.STATS_CAP);
//		if (foodstats.isPresent()) {
//		foodstats.orElseThrow(() -> new NullPointerException() ).storeLevel(-1);
//		int lvl = foodstats.orElseThrow(() -> new NullPointerException() ).retrieveLevel();
//		msg.send(player, Integer.toString(lvl));
//		}
		LazyOptional<IFrustrationLevel> frustrationCap = player
				.getCapability(FrustrationLevelProvider.FRUSTRATION_LEVEL);
		IFrustrationLevel iFrustrationLevel = frustrationCap.orElseThrow(NullPointerException::new);
		player.sendMessage(
				new StringTextComponent("LOGGED IN AND FRUSTRATION LVL: " + (iFrustrationLevel.getFrustration())),
				event.getEntity().getUUID());
		LogManager.getLogger().info("ITS OVER:" + Integer.toString(iFrustrationLevel.getFrustration()));
	}

	@SubscribeEvent
	@SuppressWarnings(value = { "" })
	public static void onPlayerToss(ItemTossEvent event) {
		PlayerEntity player = (ServerPlayerEntity) event.getPlayer();
		World world = player.level;
		BlockState state = world.getBlockState(player.blockPosition().below());
		// first getitem() gets the stack (which can be more than 1), second getitem()
		// gets the actual singular item it is
		ItemStack item = event.getEntityItem().getItem();

		// loops over every custom thrown recipe
		for (final IRecipe<?> recipe : world.getRecipeManager().getAllRecipesFor(RecipeInit.THROWN)) {
			if (recipe instanceof ThrownItemFromBlockRecipe) {
				ThrownItemFromBlockRecipe thrownRecipe = ((ThrownItemFromBlockRecipe) recipe);
				if (thrownRecipe.isValid(item, state.getBlock(), world)) {
					final int initialCount = event.getEntityItem().getItem().getCount();
					final int remainder = initialCount % thrownRecipe.getAmountGiven();

					if (remainder == 0) {
						event.setCanceled(true);
					} else {
						event.getEntityItem().getItem().setCount(remainder);

					}
					final ItemStack give = recipe.getResultItem().copy();
					final float ratio = (float) thrownRecipe.getAmountReceived()
							/ (float) thrownRecipe.getAmountGiven();
					player.sendMessage(
							new StringTextComponent("GAR is: " + thrownRecipe.getAmountReceived() + "GAG is "
									+ thrownRecipe.getAmountGiven() + ", Ratio is:" + ratio),
							net.minecraft.util.Util.NIL_UUID);
					final int count = (int) Math.floor((float) (initialCount - remainder) * ratio);
					player.sendMessage(new StringTextComponent(
							"test1" + (initialCount - remainder) + " " + event.getEntityItem().getItem().toString()
									+ " and receiving " + count + " " + recipe.getResultItem().getItem().toString()),
							net.minecraft.util.Util.NIL_UUID);
					give.setCount(count);
					ItemHandlerHelper.giveItemToPlayer(player, give);

				}
			}

		}

		exhaustionLevel = ObfuscationReflectionHelper.getPrivateValue(FoodStats.class, player.getFoodData(),
				"exhaustionLevel");
		// player.sendMessage(new StringTextComponent(Float.toString(exhaustionLevel)),
		// Util.NIL_UUID);
		// LazyOptional<IFrustrationLevel> frustrationCap =
		// player.getCapability(FrustrationLevelProvider.FRUSTRATION_LEVEL);
		// IFrustrationLevel iFrustrationLevel =
		// frustrationCap.orElseThrow(NullPointerException::new);
		// player.sendMessage(new StringTextComponent("Welcome, your FRUSTRATION is " +
		// (iFrustrationLevel.getFrustration())), event.getEntity().getUUID());
		// LogManager.getLogger().info("ITS
		// OVER:"+Integer.toString(iFrustrationLevel.getFrustration()));
	}

}
