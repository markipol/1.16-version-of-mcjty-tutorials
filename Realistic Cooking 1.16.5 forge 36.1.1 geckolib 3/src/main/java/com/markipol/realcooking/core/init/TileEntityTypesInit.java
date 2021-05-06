package com.markipol.realcooking.core.init;

import com.markipol.realcooking.RealCooking;
import com.markipol.realcooking.common.tileentities.BlenderTileEntity;
import com.markipol.realcooking.common.tileentities.FirstBlockTileEntity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityTypesInit {
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPE = DeferredRegister
			.create(ForgeRegistries.TILE_ENTITIES, RealCooking.MOD_ID);
	public static final RegistryObject<TileEntityType<BlenderTileEntity>> BLENDER_TILE_ENTITY = TILE_ENTITY_TYPE.register(
			"blender", () -> TileEntityType.Builder.of(BlenderTileEntity::new, BlockInit.BLENDER.get()).build(null));
	public static final RegistryObject<TileEntityType<FirstBlockTileEntity>> FIRST_BLOCK_TILE_ENTITY = TILE_ENTITY_TYPE.register(
			"first_block", () -> TileEntityType.Builder.of(FirstBlockTileEntity::new, BlockInit.FIRST_BLOCK.get()).build(null));
	

}
