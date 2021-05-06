package com.markipol.realcooking.common.tileentities;

import com.markipol.realcooking.core.init.TileEntityTypesInit;

import net.minecraft.block.Blocks;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class BlenderTileEntity extends TileEntity implements IAnimatable, ITickableTileEntity {
	private AnimationFactory factory = new AnimationFactory(this);
	public boolean bladesSpinning = true;

	public BlenderTileEntity(TileEntityType<? extends TileEntity> tileEntityTypeIn) {
		super(tileEntityTypeIn);
		// TODO Auto-generated constructor stub
	}

	public BlenderTileEntity() {
		this(TileEntityTypesInit.BLENDER_TILE_ENTITY.get());
		// TODO Auto-generated constructor stub
	}

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.blender.bladesSpin", true));
		if (bladesSpinning) {
			return PlayState.CONTINUE;
		} else {
			return PlayState.STOP;
		}
	}

	@Override
	public void registerControllers(AnimationData data) {
		// TODO Auto-generated method stub
		data.addAnimationController(new AnimationController(this, "blenderTileEntityAnimationController", 0, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		// TODO Auto-generated method stub
		return this.factory;
	}

	@Override
	public void tick() {
		this.level.setBlockAndUpdate(this.worldPosition.below(), Blocks.AIR.defaultBlockState());

	}

}
