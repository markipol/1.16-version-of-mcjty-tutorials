package com.markipol.realcooking.core.init;

import java.awt.event.KeyEvent;

import com.markipol.realcooking.RealCooking;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@OnlyIn(Dist.CLIENT)
public class KeybindsInit {
	public static KeyBinding boulderPunch;
	public static void registerKeybindings(final  FMLClientSetupEvent event)
	{
		boulderPunch = create("boulder_punch",KeyEvent.VK_B);
		ClientRegistry.registerKeyBinding(boulderPunch);
	}
	private static KeyBinding create (String name, int key) {
		return new KeyBinding ("running."+ RealCooking.MOD_ID + "." + name, key, "running.category." + RealCooking.MOD_ID);
	}

}
