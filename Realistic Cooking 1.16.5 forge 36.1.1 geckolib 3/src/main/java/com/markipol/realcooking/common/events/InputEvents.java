package com.markipol.realcooking.common.events;

import java.lang.reflect.Field;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.markipol.realcooking.RealCooking;
import com.markipol.realcooking.core.init.KeybindsInit;
import com.markipol.realcooking.core.network.RealCookingNetwork;
import com.markipol.realcooking.core.network.message.InputMessage;

import net.minecraft.client.Minecraft;
import net.minecraft.util.FoodStats;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

@EventBusSubscriber(modid = RealCooking.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
public class InputEvents {
	public static final Logger LOGGER = LogManager.getLogger();
	public static Field fEL;
	public static float exhaustionLevel;
	@SubscribeEvent
	public static void onKeyPress(InputEvent.KeyInputEvent event) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.level == null) return;
		onInput(mc, event.getKey(), event.getAction(), event);
	}
	@SubscribeEvent
	public static void onPlayerLoad(PlayerEvent.LoadFromFile event) {
		fEL =ObfuscationReflectionHelper.findField(FoodStats.class, "exhaustionLevel");
	}

	private static void onInput(Minecraft mc, int key, int action, InputEvent event) {
		if (mc.screen == null && KeybindsInit.boulderPunch.isDown()) {
			RealCookingNetwork.CHANNEL.sendToServer(new InputMessage(key));
		}
		//Float exhaustion = 
		//LOGGER.info("Player exhaustion level is " + 
		
		
	}

}
