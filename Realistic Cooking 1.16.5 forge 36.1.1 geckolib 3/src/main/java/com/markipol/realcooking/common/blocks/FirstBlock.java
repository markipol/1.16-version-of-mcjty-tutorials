package com.markipol.realcooking.common.blocks;

import java.util.function.ToIntFunction;

import com.markipol.realcooking.common.containers.FirstBlockContainer;
import com.markipol.realcooking.common.tileentities.FirstBlockTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class FirstBlock extends Block {

	public static ToIntFunction<BlockState> lightPower = BlockState -> 14;

	public FirstBlock() {
		super(Properties.of(Material.HEAVY_METAL).sound(SoundType.ANVIL).strength(2.0f).lightLevel(lightPower));
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
		return new FirstBlockTileEntity();
	}

	@Override
	public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
		// TODO Auto-generated method stub
		return state.getValue(BlockStateProperties.POWERED) ? super.getLightValue(state, world, pos) : 0;

	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		// TODO Auto-generated method stub
		return defaultBlockState().setValue(BlockStateProperties.FACING,
				context.getNearestLookingDirection().getOpposite());
	}

	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
			BlockRayTraceResult raytrace) {
		if (!world.isClientSide) {
			TileEntity tileEntity = world.getBlockEntity(pos);
			if (tileEntity instanceof FirstBlockTileEntity) {
				INamedContainerProvider containerProvider = new INamedContainerProvider() {
					public ITextComponent getDisplayName() {
						return new TranslationTextComponent("screen.realcooking.firstblock");
					}

					public net.minecraft.inventory.container.Container createMenu(int i,
							PlayerInventory playerInventory, PlayerEntity playerEntity) {
						return new FirstBlockContainer(i, world, pos, playerInventory, playerEntity);
					};

				};
				NetworkHooks.openGui((ServerPlayerEntity) player, containerProvider, tileEntity.getBlockPos());

			} else {
				throw new IllegalStateException("Our named container provider is missing!");
			}

		}

		return ActionResultType.SUCCESS;

	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		// TODO Auto-generated method stub
		
		builder.add(BlockStateProperties.FACING, BlockStateProperties.POWERED);
	}
}
