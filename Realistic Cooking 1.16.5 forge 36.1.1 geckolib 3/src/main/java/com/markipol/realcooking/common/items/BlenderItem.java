package com.markipol.realcooking.common.items;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class BlenderItem extends BlockItem implements IAnimatable{
	public AnimationFactory factory = new AnimationFactory(this);

	public BlenderItem(Block block, Properties p_i48487_1_) {
		super(block, p_i48487_1_);
		// TODO Auto-generated constructor stub
	}
	
	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.blender.bladesSpin", true));
		
			return PlayState.CONTINUE;
		
			
		
	}

	@Override
	public void registerControllers(AnimationData data) {
		// TODO Auto-generated method stub
		 data.addAnimationController(new AnimationController(this, "blenderItemAnimationController", 20, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		// TODO Auto-generated method stub
		return this.factory;
	}

}
