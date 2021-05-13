package com.markipol.realcooking.common.blocks;

import java.util.stream.Stream;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class MixerBlock extends Block {

	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

	private static final VoxelShape SHAPE_N = Stream.of(Block.box(5, 9, 14, 6, 13, 15),
			Block.box(10, 9, 14, 11, 13, 15), Block.box(7, 0, 15, 9, 14, 16), Block.box(6, 12, 2, 10, 15, 3),
			Block.box(6, 11, 5, 10, 12, 9), Block.box(6, 11, 10, 10, 12, 11), Block.box(6, 10, 7, 8, 11, 9),
			Block.box(6, 10, 11, 10, 12, 12), Block.box(6, 9, 12, 10, 12, 13), Block.box(5, 10, 13, 6, 14, 14),
			Block.box(10, 10, 13, 11, 14, 14), Block.box(5, 0, 13, 6, 4, 14), Block.box(10, 0, 13, 11, 4, 14),
			Block.box(6, 15, 3, 10, 16, 13), Block.box(5, 12, 3, 11, 15, 13), Block.box(6, 0, 14, 10, 15, 15),
			Block.box(6, 1, 12, 10, 2, 13), Block.box(6, 0, 13, 10, 16, 14), Block.box(5, 0, 12, 11, 1, 13),
			Block.box(12, 0, 4, 13, 1, 10), Block.box(3, 0, 4, 4, 1, 10), Block.box(5, 0, 2, 11, 1, 3),
			Block.box(4, 0, 3, 12, 1, 12), Block.box(6, 5, 10, 7, 7, 11), Block.box(6, 4, 9, 7, 5, 10),
			Block.box(6, 5, 8, 7, 7, 9), Block.box(6, 3, 8, 7, 4, 9), Block.box(6, 4, 7, 7, 5, 8),
			Block.box(6, 5, 6, 7, 7, 7), Block.box(6, 7, 7, 7, 8, 10), Block.box(6, 8, 8, 7, 10, 9),
			Block.box(0, 4, 7, 1, 8, 8), Block.box(1, 8, 7, 2, 9, 8), Block.box(1, 3, 7, 3, 4, 8),
			Block.box(5, 2, 3, 11, 3, 4), Block.box(5, 1, 4, 11, 2, 10), Block.box(11, 2, 4, 12, 3, 10),
			Block.box(4, 2, 4, 5, 3, 10), Block.box(4, 3, 3, 5, 4, 4), Block.box(3, 3, 4, 4, 4, 10),
			Block.box(4, 3, 10, 5, 4, 11), Block.box(5, 2, 10, 11, 3, 11), Block.box(5, 3, 11, 11, 4, 12),
			Block.box(11, 3, 10, 12, 4, 11), Block.box(12, 3, 4, 13, 4, 10), Block.box(11, 3, 3, 12, 4, 4),
			Block.box(5, 3, 2, 11, 4, 3), Block.box(13, 4, 4, 14, 9, 10), Block.box(12, 4, 10, 13, 9, 11),
			Block.box(11, 4, 11, 12, 9, 12), Block.box(5, 4, 12, 11, 9, 13), Block.box(4, 4, 11, 5, 9, 12),
			Block.box(3, 4, 10, 4, 9, 11), Block.box(2, 4, 4, 3, 9, 10), Block.box(3, 4, 3, 4, 9, 4),
			Block.box(4, 4, 2, 5, 9, 3), Block.box(11, 4, 2, 12, 9, 3), Block.box(5, 4, 1, 11, 9, 2),
			Block.box(12, 4, 3, 13, 9, 4)).reduce((v1, v2) -> {return VoxelShapes.join(v1, v2, IBooleanFunction.OR);}).get();

	private static final VoxelShape SHAPE_W = Stream.of(Block.box(14, 9, 10, 15, 13, 11),
			Block.box(14, 9, 5, 15, 13, 6), Block.box(15, 0, 7, 16, 14, 9), Block.box(2, 12, 6, 3, 15, 10),
			Block.box(5, 11, 6, 9, 12, 10), Block.box(10, 11, 6, 11, 12, 10), Block.box(7, 10, 8, 9, 11, 10),
			Block.box(11, 10, 6, 12, 12, 10), Block.box(12, 9, 6, 13, 12, 10), Block.box(13, 10, 10, 14, 14, 11),
			Block.box(13, 10, 5, 14, 14, 6), Block.box(13, 0, 10, 14, 4, 11), Block.box(13, 0, 5, 14, 4, 6),
			Block.box(3, 15, 6, 13, 16, 10), Block.box(3, 12, 5, 13, 15, 11), Block.box(14, 0, 6, 15, 15, 10),
			Block.box(12, 1, 6, 13, 2, 10), Block.box(13, 0, 6, 14, 16, 10), Block.box(12, 0, 5, 13, 1, 11),
			Block.box(4, 0, 3, 10, 1, 4), Block.box(4, 0, 12, 10, 1, 13), Block.box(2, 0, 5, 3, 1, 11),
			Block.box(3, 0, 4, 12, 1, 12), Block.box(10, 5, 9, 11, 7, 10), Block.box(9, 4, 9, 10, 5, 10),
			Block.box(8, 5, 9, 9, 7, 10), Block.box(8, 3, 9, 9, 4, 10), Block.box(7, 4, 9, 8, 5, 10),
			Block.box(6, 5, 9, 7, 7, 10), Block.box(7, 7, 9, 10, 8, 10), Block.box(8, 8, 9, 9, 10, 10),
			Block.box(7, 4, 15, 8, 8, 16), Block.box(7, 8, 14, 8, 9, 15), Block.box(7, 3, 13, 8, 4, 15),
			Block.box(3, 2, 5, 4, 3, 11), Block.box(4, 1, 5, 10, 2, 11), Block.box(4, 2, 4, 10, 3, 5),
			Block.box(4, 2, 11, 10, 3, 12), Block.box(3, 3, 11, 4, 4, 12), Block.box(4, 3, 12, 10, 4, 13),
			Block.box(10, 3, 11, 11, 4, 12), Block.box(10, 2, 5, 11, 3, 11), Block.box(11, 3, 5, 12, 4, 11),
			Block.box(10, 3, 4, 11, 4, 5), Block.box(4, 3, 3, 10, 4, 4), Block.box(3, 3, 4, 4, 4, 5),
			Block.box(2, 3, 5, 3, 4, 11), Block.box(4, 4, 2, 10, 9, 3), Block.box(10, 4, 3, 11, 9, 4),
			Block.box(11, 4, 4, 12, 9, 5), Block.box(12, 4, 5, 13, 9, 11), Block.box(11, 4, 11, 12, 9, 12),
			Block.box(10, 4, 12, 11, 9, 13), Block.box(4, 4, 13, 10, 9, 14), Block.box(3, 4, 12, 4, 9, 13),
			Block.box(2, 4, 11, 3, 9, 12), Block.box(2, 4, 4, 3, 9, 5), Block.box(1, 4, 5, 2, 9, 11),
			Block.box(3, 4, 3, 4, 9, 4)).reduce((v1, v2) -> {return VoxelShapes.join(v1, v2, IBooleanFunction.OR);}).get();

	private static final VoxelShape SHAPE_S = Stream.of(Block.box(10, 9, 1, 11, 13, 2), Block.box(5, 9, 1, 6, 13, 2),
			Block.box(7, 0, 0, 9, 14, 1), Block.box(6, 12, 13, 10, 15, 14), Block.box(6, 11, 7, 10, 12, 11),
			Block.box(6, 11, 5, 10, 12, 6), Block.box(8, 10, 7, 10, 11, 9), Block.box(6, 10, 4, 10, 12, 5),
			Block.box(6, 9, 3, 10, 12, 4), Block.box(10, 10, 2, 11, 14, 3), Block.box(5, 10, 2, 6, 14, 3),
			Block.box(10, 0, 2, 11, 4, 3), Block.box(5, 0, 2, 6, 4, 3), Block.box(6, 15, 3, 10, 16, 13),
			Block.box(5, 12, 3, 11, 15, 13), Block.box(6, 0, 1, 10, 15, 2), Block.box(6, 1, 3, 10, 2, 4),
			Block.box(6, 0, 2, 10, 16, 3), Block.box(5, 0, 3, 11, 1, 4), Block.box(3, 0, 6, 4, 1, 12),
			Block.box(12, 0, 6, 13, 1, 12), Block.box(5, 0, 13, 11, 1, 14), Block.box(4, 0, 4, 12, 1, 13),
			Block.box(9, 5, 5, 10, 7, 6), Block.box(9, 4, 6, 10, 5, 7), Block.box(9, 5, 7, 10, 7, 8),
			Block.box(9, 3, 7, 10, 4, 8), Block.box(9, 4, 8, 10, 5, 9), Block.box(9, 5, 9, 10, 7, 10),
			Block.box(9, 7, 6, 10, 8, 9), Block.box(9, 8, 7, 10, 10, 8), Block.box(15, 4, 8, 16, 8, 9),
			Block.box(14, 8, 8, 15, 9, 9), Block.box(13, 3, 8, 15, 4, 9), Block.box(5, 2, 12, 11, 3, 13),
			Block.box(5, 1, 6, 11, 2, 12), Block.box(4, 2, 6, 5, 3, 12), Block.box(11, 2, 6, 12, 3, 12),
			Block.box(11, 3, 12, 12, 4, 13), Block.box(12, 3, 6, 13, 4, 12), Block.box(11, 3, 5, 12, 4, 6),
			Block.box(5, 2, 5, 11, 3, 6), Block.box(5, 3, 4, 11, 4, 5), Block.box(4, 3, 5, 5, 4, 6),
			Block.box(3, 3, 6, 4, 4, 12), Block.box(4, 3, 12, 5, 4, 13), Block.box(5, 3, 13, 11, 4, 14),
			Block.box(2, 4, 6, 3, 9, 12), Block.box(3, 4, 5, 4, 9, 6), Block.box(4, 4, 4, 5, 9, 5),
			Block.box(5, 4, 3, 11, 9, 4), Block.box(11, 4, 4, 12, 9, 5), Block.box(12, 4, 5, 13, 9, 6),
			Block.box(13, 4, 6, 14, 9, 12), Block.box(12, 4, 12, 13, 9, 13), Block.box(11, 4, 13, 12, 9, 14),
			Block.box(4, 4, 13, 5, 9, 14), Block.box(5, 4, 14, 11, 9, 15), Block.box(3, 4, 12, 4, 9, 13)).reduce((v1, v2) -> {return VoxelShapes.join(v1, v2, IBooleanFunction.OR);}).get();
	private static final VoxelShape SHAPE_E = Stream.of(Block.box(1, 9, 5, 2, 13, 6), Block.box(1, 9, 10, 2, 13, 11),
			Block.box(0, 0, 7, 1, 14, 9), Block.box(13, 12, 6, 14, 15, 10), Block.box(7, 11, 6, 11, 12, 10),
			Block.box(5, 11, 6, 6, 12, 10), Block.box(7, 10, 6, 9, 11, 8), Block.box(4, 10, 6, 5, 12, 10),
			Block.box(3, 9, 6, 4, 12, 10), Block.box(2, 10, 5, 3, 14, 6), Block.box(2, 10, 10, 3, 14, 11),
			Block.box(2, 0, 5, 3, 4, 6), Block.box(2, 0, 10, 3, 4, 11), Block.box(3, 15, 6, 13, 16, 10),
			Block.box(3, 12, 5, 13, 15, 11), Block.box(1, 0, 6, 2, 15, 10), Block.box(3, 1, 6, 4, 2, 10),
			Block.box(2, 0, 6, 3, 16, 10), Block.box(3, 0, 5, 4, 1, 11), Block.box(6, 0, 12, 12, 1, 13),
			Block.box(6, 0, 3, 12, 1, 4), Block.box(13, 0, 5, 14, 1, 11), Block.box(4, 0, 4, 13, 1, 12),
			Block.box(5, 5, 6, 6, 7, 7), Block.box(6, 4, 6, 7, 5, 7), Block.box(7, 5, 6, 8, 7, 7),
			Block.box(7, 3, 6, 8, 4, 7), Block.box(8, 4, 6, 9, 5, 7), Block.box(9, 5, 6, 10, 7, 7),
			Block.box(6, 7, 6, 9, 8, 7), Block.box(7, 8, 6, 8, 10, 7), Block.box(8, 4, 0, 9, 8, 1),
			Block.box(8, 8, 1, 9, 9, 2), Block.box(8, 3, 1, 9, 4, 3), Block.box(12, 2, 5, 13, 3, 11),
			Block.box(6, 1, 5, 12, 2, 11), Block.box(6, 2, 11, 12, 3, 12), Block.box(6, 2, 4, 12, 3, 5),
			Block.box(12, 3, 4, 13, 4, 5), Block.box(6, 3, 3, 12, 4, 4), Block.box(5, 3, 4, 6, 4, 5),
			Block.box(5, 2, 5, 6, 3, 11), Block.box(4, 3, 5, 5, 4, 11), Block.box(5, 3, 11, 6, 4, 12),
			Block.box(6, 3, 12, 12, 4, 13), Block.box(12, 3, 11, 13, 4, 12), Block.box(13, 3, 5, 14, 4, 11),
			Block.box(6, 4, 13, 12, 9, 14), Block.box(5, 4, 12, 6, 9, 13), Block.box(4, 4, 11, 5, 9, 12),
			Block.box(3, 4, 5, 4, 9, 11), Block.box(4, 4, 4, 5, 9, 5), Block.box(5, 4, 3, 6, 9, 4),
			Block.box(6, 4, 2, 12, 9, 3), Block.box(12, 4, 3, 13, 9, 4), Block.box(13, 4, 4, 14, 9, 5),
			Block.box(13, 4, 11, 14, 9, 12), Block.box(14, 4, 5, 15, 9, 11), Block.box(12, 4, 12, 13, 9, 13)).reduce((v1, v2) -> {return VoxelShapes.join(v1, v2, IBooleanFunction.OR);}).get();

	public MixerBlock() {
		super(AbstractBlock.Properties.of(Material.METAL, MaterialColor.COLOR_GRAY).strength(15f)
				.sound(SoundType.METAL));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		switch (state.getValue(FACING)) {
		case NORTH:
			return SHAPE_S;
		case EAST:
			return SHAPE_W;
		case SOUTH:
			return SHAPE_N;
		case WEST:
			return SHAPE_E;
		default:
			return SHAPE_S;
		}
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext p_196258_1_) {
		return this.defaultBlockState().setValue(FACING, p_196258_1_.getHorizontalDirection().getOpposite());
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		// TODO Auto-generated method stub
		return super.hasTileEntity(state);
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		// TODO Auto-generated method stub
		return super.createTileEntity(state, world);
	}

}