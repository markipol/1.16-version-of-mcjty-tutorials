package com.markipol.realcooking.core.network;

import com.markipol.realcooking.RealCooking;
import com.markipol.realcooking.core.network.message.BlenderAnimationMessage;
import com.markipol.realcooking.core.network.message.InputMessage;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class RealCookingNetwork {
	public static final String NETWORK_VERSION = "0.0.1";
	public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
			new ResourceLocation (RealCooking.MOD_ID, "network"), 
			() -> NETWORK_VERSION, 
			version -> version.equals(NETWORK_VERSION), 
			version -> version.equals(NETWORK_VERSION));
	public static void init() {
		CHANNEL.registerMessage(0,
				InputMessage.class,
				InputMessage::encode,
				InputMessage::decode,
				InputMessage::handle);
		CHANNEL.registerMessage(1,
				BlenderAnimationMessage.class,
				BlenderAnimationMessage::encode,
				BlenderAnimationMessage::decode,
				BlenderAnimationMessage::handle);
	}

}
