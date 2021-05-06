package com.markipol.realcooking.common.geomodels;

import com.markipol.realcooking.RealCooking;

import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BlenderModel extends AnimatedGeoModel{
	public BlenderModel() {
		
		
	}
	public static final ResourceLocation texture = new ResourceLocation(RealCooking.MOD_ID, "textures/block/blender.png");



	@Override
	public ResourceLocation getAnimationFileLocation(Object animatable) {
		// TODO Auto-generated method stub
		return new ResourceLocation(RealCooking.MOD_ID, "animations/blender.animation.json");
	}

	@Override
	public ResourceLocation getModelLocation(Object object) {
		// TODO Auto-generated method stub
		return new ResourceLocation(RealCooking.MOD_ID, "geo/blender.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(Object object) {
		// TODO Auto-generated method stub
		return new ResourceLocation(RealCooking.MOD_ID, "textures/block/blender.png");
	}

}
