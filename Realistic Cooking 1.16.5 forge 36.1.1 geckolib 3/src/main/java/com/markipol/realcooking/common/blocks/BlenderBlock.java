package com.markipol.realcooking.common.blocks;

import org.apache.logging.log4j.LogManager;

import com.markipol.realcooking.RealCooking;
import com.markipol.realcooking.common.tileentities.BlenderTileEntity;
import com.markipol.realcooking.core.init.TileEntityTypesInit;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlenderBlock extends Block{
	

	
	public BlenderBlock(Properties p_i48440_1_) {
		super(p_i48440_1_);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public boolean hasTileEntity(BlockState state) {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		// TODO Auto-generated method stub
		return TileEntityTypesInit.BLENDER_TILE_ENTITY.get().create();
	}
	
	
	@Override
	public BlockRenderType getRenderShape(BlockState p_149645_1_) {
		// TODO Auto-generated method stub
		 return BlockRenderType.ENTITYBLOCK_ANIMATED;
	}
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext p_196258_1_) {
		// TODO Auto-generated method stub
		return super.getStateForPlacement(p_196258_1_);
	}
	@SuppressWarnings("deprecation")
	@Override
	public ActionResultType use(BlockState p_225533_1_, World worldIn, BlockPos pos,
			PlayerEntity p_225533_4_, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {
		
		
		if (!worldIn.isClientSide) {
			LogManager.getLogger().info("Blender Block Clicked");
			TileEntity tileEntity = worldIn.getBlockEntity(pos);
			if (tileEntity instanceof BlenderTileEntity) {
				((BlenderTileEntity) tileEntity).bladesSpinning = !(((BlenderTileEntity) tileEntity).bladesSpinning);
				
			}
		}
		
		return ActionResultType.SUCCESS;
	}


}
