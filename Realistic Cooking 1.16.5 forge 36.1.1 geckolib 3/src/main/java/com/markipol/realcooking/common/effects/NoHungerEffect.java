package com.markipol.realcooking.common.effects;

import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class NoHungerEffect extends Effect{

	public NoHungerEffect(EffectType type, int color) {
		super(type, color);
		// TODO Auto-generated constructor stub
	}
	public NoHungerEffect() {
		super(EffectType.BENEFICIAL, 0xd4ff00);
	}

	

}
