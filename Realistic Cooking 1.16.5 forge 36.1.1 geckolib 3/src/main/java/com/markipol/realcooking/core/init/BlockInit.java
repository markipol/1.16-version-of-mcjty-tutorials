package com.markipol.realcooking.core.init;

import com.markipol.realcooking.RealCooking;
import com.markipol.realcooking.common.blocks.BlenderBlock;
import com.markipol.realcooking.common.blocks.FirstBlock;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
			RealCooking.MOD_ID);
	public static final RegistryObject<Block> BLENDER = BLOCKS.register("blender",
			() -> new BlenderBlock(AbstractBlock.Properties.of(Material.DIRT).harvestTool(ToolType.SHOVEL)
					.harvestLevel(3).sound(SoundType.ANVIL).jumpFactor(2.0f).speedFactor(1.5f).noOcclusion()));
	public static final RegistryObject<Block> FIRST_BLOCK = BLOCKS.register("first_block",
			() -> new FirstBlock());
	

}
