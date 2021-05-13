package com.markipol.realcooking.common.tileentities;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nonnull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.markipol.realcooking.common.util.CustomEnergyStorage;
import com.markipol.realcooking.core.init.Config;
import com.markipol.realcooking.core.init.TileEntityTypesInit;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class FirstBlockTileEntity extends TileEntity implements ITickableTileEntity {
	// public World world = this.level;
	private ItemStackHandler itemHandler = createHandler();
	private CustomEnergyStorage energyStorage = createEnergy();
	private int counter;
	private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
	private LazyOptional<IEnergyStorage> energy = LazyOptional.of(() -> energyStorage);
	public static final Logger LOGGER = LogManager.getLogger();

	public FirstBlockTileEntity(TileEntityType<?> p_i48289_1_) {
		super(p_i48289_1_);
		// TODO Auto-generated constructor stub
	}

	public FirstBlockTileEntity() {
		this(TileEntityTypesInit.FIRST_BLOCK_TILE_ENTITY.get());
	}

	@Override
	public void load(BlockState state, CompoundNBT tag) {
		itemHandler.deserializeNBT(tag.getCompound("inv"));
		itemHandler.deserializeNBT(tag.getCompound("energy"));
		counter = tag.getInt("counter");
		super.load(state, tag);

	}

	@Override
	public CompoundNBT save(CompoundNBT nbt) {
		nbt.put("inv", itemHandler.serializeNBT());
		nbt.put("energy", energyStorage.serializeNBT());
		nbt.putInt("counter", counter);
		return super.save(nbt);
	}

	private ItemStackHandler createHandler() {
		return new ItemStackHandler(2) {
			@Override
			protected void onContentsChanged(int slot) {
				setChanged();
			}

			@Override
			public boolean isItemValid(int slot, @Nonnull ItemStack stack) {

				return stack.getItem() == Items.DIAMOND;
			}

			@Nonnull
			@Override
			public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
//				if (stack.getItem() != Items.DIAMOND) {
//					return stack;
//				}
				if (slot==0) {
					if (stack.getItem() != Items.DIAMOND ||stack.getItem() != Items.CHARCOAL) {
					return stack;
				}
				}
				return super.insertItem(slot, stack, simulate);
			}

		};
	}

	private CustomEnergyStorage createEnergy() {
		return new CustomEnergyStorage(Config.FIRSTBLOCK_GENERATE.get(), 0) {

			@Override
			protected void onEnergyChanged() {
				// TODO Auto-generated method stub
				setChanged();
			}
		};

	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		if (level.isClientSide) {
			return;
		}
		if (counter > 0) {
			counter--;
			if (counter <= 0) {
				energyStorage.addEnergy(Config.FIRSTBLOCK_GENERATE.get());
				LOGGER.info("Currently stored energy of First Block at " + Integer.toString(getBlockPos().getX()) + ", "
						+ Integer.toString(getBlockPos().getY()) + " is "
						+ Integer.toString(energyStorage.getEnergyStored()) + " Number of slots: "
						+ Integer.toString(itemHandler.getSlots()));
			}
			setChanged();
		}
		if (counter <= 0) {
			ItemStack stack = itemHandler.getStackInSlot(0);
			if (stack.getItem() == Items.DIAMOND) {
				// itemHandler.extractItem(0, 1, false);
				counter = Config.FIRSTBLOCK_TICKS.get();
				setChanged();
			}
		}
		BlockState blockState = level.getBlockState(getBlockPos());
		if (blockState.getValue(BlockStateProperties.POWERED) != counter > 0) {
			level.setBlock(getBlockPos(), blockState.setValue(BlockStateProperties.POWERED, counter > 0),
					Constants.BlockFlags.NOTIFY_NEIGHBORS + Constants.BlockFlags.BLOCK_UPDATE);
		}
		sendOutPower();

	}

	private void sendOutPower() {
		AtomicInteger capacity = new AtomicInteger(energyStorage.getEnergyStored());
		
		if (capacity.get() > 0) {
			for (Direction direction : Direction.values()) {
				TileEntity te = level.getBlockEntity(getBlockPos().relative(direction));
				if (te != null) {
					boolean doContinue = te.getCapability(CapabilityEnergy.ENERGY, direction).map(handler -> {
						if (handler.canReceive()) {
							int received = handler.receiveEnergy(Math.min(capacity.get(), Config.FIRSTBLOCK_SEND.get()),
									false);
							capacity.addAndGet(-received);
							energyStorage.consumeEnergy(received);
							setChanged();
							return capacity.get() > 0;
						} else {
							return true;
						}

					}

					).orElse(true);
					if (!doContinue) {
						return;
					}
				}
			}

		}
	}

	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (cap.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)) {
			return handler.cast();

		}
		if (cap.equals(CapabilityEnergy.ENERGY)) {
			return energy.cast();
		}
		return super.getCapability(cap, side);
	}

}
