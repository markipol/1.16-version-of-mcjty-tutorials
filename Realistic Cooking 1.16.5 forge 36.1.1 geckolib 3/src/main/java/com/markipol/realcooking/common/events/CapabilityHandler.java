package com.markipol.realcooking.common.events;

import org.apache.logging.log4j.LogManager;

import com.markipol.realcooking.RealCooking;
import com.markipol.realcooking.capabilites.foodstats.FoodStatsProvider;
import com.markipol.realcooking.capabilites.foodstats.IFoodStats;
import com.markipol.realcooking.capabilites.frustration.SustenanceProvider;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = RealCooking.MOD_ID, bus = Bus.FORGE)
public class CapabilityHandler {
	public static final ResourceLocation FOOD_STATS_CAP = new ResourceLocation(RealCooking.MOD_ID, "food_stats");
	public static final ResourceLocation FRUSTRATION_LEVEL = new ResourceLocation(RealCooking.MOD_ID, "frustration_level");
	
	@SubscribeEvent
	public void attachCapability(AttachCapabilitiesEvent<Entity> event) {
		if ((event.getObject().level.isClientSide())) {return;}
		if (!(event.getObject() instanceof PlayerEntity))
			return;
		PlayerEntity player = (PlayerEntity) event.getObject();
		 if (! ( player.getCapability(FoodStatsProvider.STATS_CAP).isPresent() )  ){
		event.addCapability(FOOD_STATS_CAP, new FoodStatsProvider());
		 }
		 if (! ( player.getCapability(SustenanceProvider.FRUSTRATION_LEVEL).isPresent() )  ){
		event.addCapability(FRUSTRATION_LEVEL, new SustenanceProvider());
		 }
		 LogManager.getLogger().info("Capabilities event fired");
		 

	}



}
