package com.markipol.realcooking.common.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;

public class msg {
	public static void send(PlayerEntity player, String message) {
		player.sendMessage(new StringTextComponent(message), Util.NIL_UUID);
	}

}
