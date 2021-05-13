package com.markipol.realcooking.core.init;

import com.markipol.realcooking.RealCooking;
import com.markipol.realcooking.common.effects.NoHungerEffect;

import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
//new NoHungerEffect(EffectType.BENEFICIAL, 0xd4ff00)

public class PotionsInit {
	
	public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS,
			RealCooking.MOD_ID);
	public static final RegistryObject<Effect> NO_HUNGER_EFFECT = EFFECTS.register("no_hunger",
			() -> new NoHungerEffect(EffectType.BENEFICIAL, 0xd4ff00));
	public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTION_TYPES,
			RealCooking.MOD_ID);
	public static final RegistryObject<Potion> NO_HUNGER_POTION = POTIONS.register("no_hunger",
			() -> new Potion(new EffectInstance(NO_HUNGER_EFFECT.get(),400,1,false,false,false,null)));

}
