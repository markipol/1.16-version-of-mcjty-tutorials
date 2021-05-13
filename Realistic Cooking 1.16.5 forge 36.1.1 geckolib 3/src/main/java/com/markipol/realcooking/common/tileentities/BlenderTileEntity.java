package com.markipol.realcooking.common.tileentities;

import java.util.List;

import org.apache.logging.log4j.LogManager;

import com.markipol.realcooking.core.init.TileEntityTypesInit;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.network.play.server.SCombatPacket.Event;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class BlenderTileEntity extends TileEntity implements IAnimatable, ITickableTileEntity {
	public AnimationFactory factory = new AnimationFactory(this);
	public boolean bladesSpinning = true;
	public boolean bladesAnimation = true;
	public AnimationController spinController;
	private ServerWorld SW;

	public BlenderTileEntity(TileEntityType<? extends TileEntity> tileEntityTypeIn) {
		super(tileEntityTypeIn);
		// TODO Auto-generated constructor stub
	}
	private boolean getBladesSpinning() {
		return bladesSpinning;
	}

	public BlenderTileEntity() {
		this(TileEntityTypesInit.BLENDER_TILE_ENTITY.get());
		// TODO Auto-generated constructor stub
	}

//	public void toggleAnimation() {
//		bladesSpinning = !bladesSpinning;
//		LogManager.getLogger().info("bladesSpinning = " + Boolean.toString(bladesSpinning));
//		setChanged();
//	}
	//private <E extends BlenderTileEntity & IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		//event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.blender.bladesSpin", true));
		// LogManager.getLogger().info("Inside predicate: " +
		// Boolean.toString(bladesSpinning));
//		Boolean extra = event.getExtraDataOfType(Boolean.class)
//		if (extra == null) {
//			LogManager.getLogger().info("null");
//			return PlayState.STOP;
//		} else
//bladesAnimation = getBladesSpinning();
		

			//return bladesAnimation ? PlayState.CONTINUE : PlayState.STOP;
//		event.getController().clearAnimationCache();
//		//final boolean bladesAnimation = ((BlenderTileEntity) event.getAnimatable()).getBladesSpinning();
//		LogManager.getLogger().info(Boolean.toString(bladesSpinning));
//		if (bladesSpinning==true) {
//			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.blender.bladesSpin", true));
//			return PlayState.CONTINUE;
//		} else if (bladesSpinning==false){
//			 event.getController().setAnimation(null);
//			return PlayState.STOP;
//		} else return PlayState.STOP;
//			
//	}
	

    private <E extends TileEntity & IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        AnimationController<?> controller = event.getController();
        if (getState().getValue(BlockStateProperties.POWERED)==true) {
        	event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.blender.bladesSpin", true));
            return PlayState.CONTINUE;
        }
        else if (getState().getValue(BlockStateProperties.POWERED)==false) {
        	event.getController().setAnimation(null);
            return PlayState.STOP;
        }
        else {
        	LogManager.getLogger("Something is very wrong");
        	return PlayState.STOP;
        }
//        switch (controller.getName()) {
//        case "leg_controller":
//            animateLegs(controller);
//            return PlayState.CONTINUE;
//        case "head_controller":
//            animateHead(controller);
//            return PlayState.CONTINUE;
//        default:
//            return PlayState.CONTINUE;
//        }
//    }

    }
	public BlockState getState() {
		return this.level.getBlockState(getBlockPos());
	}

	@Override
	public void registerControllers(AnimationData data) {
		spinController = data.addAnimationController(
				new AnimationController(this, "blenderTileEntityAnimationController", 0, this::predicate));
		
		
	}
	

	@Override
	public AnimationFactory getFactory() {
		// TODO Auto-generated method stub
		return this.factory;
	}

	@Override
	public void load(BlockState state, CompoundNBT tag) {
		super.load(state, tag);

		bladesSpinning = tag.getBoolean("bladesSpinning");
		

	}
	

	@Override
	public CompoundNBT save(CompoundNBT compound) {

		//nbt.putBoolean("bladesSpinning", bladesSpinning);
		//return super.save(nbt);
		CompoundNBT nbt = super.save(compound);
		nbt.putBoolean("bladesSpinning",this.bladesSpinning);
		return nbt;
	}
	@Override
	public CompoundNBT getUpdateTag() {
		// TODO Auto-generated method stub
		return this.save(new CompoundNBT());
	}
	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		// TODO Auto-generated method stub
		CompoundNBT nbt = new CompoundNBT();
		this.save(nbt);
		return new SUpdateTileEntityPacket(this.getBlockPos(), 0, nbt);
	}
	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		// TODO Auto-generated method stub
		
		this.load(this.getBlockState(), pkt.getTag());
	}
	

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		if (!this.level.isClientSide) {
			SW = (ServerWorld) this.level;
			if (SW.getRandomPlayer() != null) {
//				SW.getRandomPlayer().sendMessage(
//						new StringTextComponent("Outside predicate: " + Boolean.toString(bladesSpinning)),
//						Util.NIL_UUID);
			}
		}
		// LogManager.getLogger().info("Outside predicate: " +
		// Boolean.toString(bladesSpinning));

	}

}
