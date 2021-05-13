package com.markipol.realcooking.core.network.message;

import java.util.function.Supplier;

import net.minecraft.block.Blocks;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

public class InputMessage {
	public int key;
	public InputMessage() {
		
	}
	public InputMessage(int key) {
		this.key=key;
	}
	public static void encode(InputMessage message, PacketBuffer buffer) {
		buffer.writeInt(message.key);
		
	}
	public static InputMessage decode(PacketBuffer buffer) {
		return new InputMessage(buffer.readInt());
		
	}
	public static void handle(InputMessage message, Supplier<NetworkEvent.Context> contextSupp) {
		NetworkEvent.Context context = contextSupp.get();
		context.enqueueWork(() -> {
			ServerPlayerEntity player = context.getSender();
			player.addItem(new ItemStack(Items.DIAMOND));	
			World world = player.level;
			world.setBlockAndUpdate(player.blockPosition().north(), Blocks.AIR.defaultBlockState());
			world.setBlockAndUpdate(player.blockPosition().north().above(), Blocks.AIR.defaultBlockState());
			player.sendMessage(new StringTextComponent(Integer.toString(message.key)), Util.NIL_UUID);
		});
		context.setPacketHandled(true);
	}

}
