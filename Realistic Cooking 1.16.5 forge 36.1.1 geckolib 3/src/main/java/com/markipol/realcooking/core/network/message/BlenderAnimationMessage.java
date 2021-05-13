package com.markipol.realcooking.core.network.message;

import java.util.function.Supplier;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Util;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.NetworkEvent;

public class BlenderAnimationMessage {
	public boolean running;
	public BlockPos bp;
	public BlenderAnimationMessage() {
		
	}
	public BlenderAnimationMessage(boolean running, BlockPos bp) {
		this.running=running;
		this.bp = bp;
	}
	public static void encode(BlenderAnimationMessage message, PacketBuffer buffer) {
		buffer.writeBoolean(message.running);
		buffer.writeBlockPos(message.bp);
		
	}
	public static BlenderAnimationMessage decode(PacketBuffer buffer) {
		return new BlenderAnimationMessage(buffer.readBoolean(),buffer.readBlockPos());
		
	}
	public static void handle(BlenderAnimationMessage message, Supplier<NetworkEvent.Context> contextSupp) {
		NetworkEvent.Context context = contextSupp.get();
		context.enqueueWork(() -> {
			ServerPlayerEntity player = context.getSender();
			World world = player.level;
			//BlenderTileEntity blender = (BlenderTileEntity)world.getBlockEntity(message.bp);
			//blender.bladesSpinning = message.running;
			BlockState bs = world.getBlockState(message.bp);
			BlockState nbs = bs.setValue(BlockStateProperties.POWERED, message.running);
			 world.setBlock(message.bp, nbs,
	                    Constants.BlockFlags.NOTIFY_NEIGHBORS + Constants.BlockFlags.BLOCK_UPDATE);
			
			world.sendBlockUpdated(message.bp, nbs, nbs, 3);
			
			player.sendMessage(new StringTextComponent(Boolean.toString(world.getBlockState(message.bp).getValue(BlockStateProperties.POWERED))), Util.NIL_UUID);
		});
		context.setPacketHandled(true);
	}

}
