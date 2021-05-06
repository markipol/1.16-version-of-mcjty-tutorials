package com.markipol.realcooking.common.containers;

import com.markipol.realcooking.common.util.CustomEnergyStorage;
import com.markipol.realcooking.core.init.BlockInit;
import com.markipol.realcooking.core.init.ContainerTypesInit;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class FirstBlockContainer extends Container {
	private TileEntity tileEntity;
	private PlayerEntity playerEntity;
	private IItemHandler playerInventory;

	public FirstBlockContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory,
			PlayerEntity player) {
		super(ContainerTypesInit.FIRST_BLOCK_CONTAINER.get(), windowId);
		tileEntity = world.getBlockEntity(pos);
		this.playerEntity = player;
		this.playerInventory = new InvWrapper(playerInventory);
		if (tileEntity != null) {
			tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
				addSlot(new SlotItemHandler(h, 0, 64, 24));
			});
		}
		layoutPlayerInventorySlots(10, 70);
		trackPower();
	}
	
	//Setup syncing of power from server to client so that the GUI can show the amount of power in the Block
	private void trackPower() {
		//Need 2 split the 32 bit int into 2 16 bit shorts, because the server said so  
		addDataSlot (new IntReferenceHolder() {
			@Override
			public int get() {return getEnergy() & 0xffff; }
			@Override
			public void set(int value) {
				tileEntity.getCapability(CapabilityEnergy.ENERGY).ifPresent(h -> {
                    int energyStored = h.getEnergyStored() & 0xffff0000;
                    ((CustomEnergyStorage)h).setEnergy(energyStored + (value & 0xffff));
                });
				
			}
			
		});
		addDataSlot (new IntReferenceHolder() {

			@Override
			public int get() {
				return (getEnergy() >> 16) & 0xffff;
				
			}

			@Override
			public void set(int value) {
				tileEntity.getCapability(CapabilityEnergy.ENERGY).ifPresent(h -> {
                    int energyStored = h.getEnergyStored() & 0x0000ffff;
                    ((CustomEnergyStorage)h).setEnergy(energyStored | (value << 16));
                });
				
			}
			
		});
		
	}


	private void layoutPlayerInventorySlots(int leftCol, int topRow) {
		//Player Inventory
		addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);
		
		//Hotbar
		topRow += 58;
		addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
		
	}
	public int getEnergy() {
        return tileEntity.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getEnergyStored).orElse(0);
    }
	@Override
	public boolean stillValid(PlayerEntity playerIn) {
        return stillValid(IWorldPosCallable.create(tileEntity.getLevel(), tileEntity.getBlockPos()), playerEntity, BlockInit.FIRST_BLOCK.get());
    }

	private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
		for (int i = 0; i< amount; i++) {
			addSlot(new SlotItemHandler(handler, index, x, y));
			x += dx;
			index++;
		}
		return index;
	}
	
	private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
		for (int j = 0; j <verAmount; j++) {
			index = addSlotRange(handler, index, x, y, horAmount, dx);
			y += dy;
		}
		return index;
	}
	 public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
	        ItemStack itemstack = ItemStack.EMPTY;
	        Slot slot = this.getSlot(index);
	        if (slot != null && slot.hasItem()) {
	            ItemStack stack = slot.getItem();
	            itemstack = stack.copy();
	            if (index == 0) {
	                if (!this.moveItemStackTo(stack, 1, 37, true)) {
	                    return ItemStack.EMPTY;
	                }
	                slot.onQuickCraft(stack, itemstack);
	            } else {
	                if (stack.getItem() == Items.DIAMOND) {
	                    if (!this.moveItemStackTo(stack, 0, 1, false)) {
	                        return ItemStack.EMPTY;
	                    }
	                } else if (index < 28) {
	                    if (!this.moveItemStackTo(stack, 28, 37, false)) {
	                        return ItemStack.EMPTY;
	                    }
	                } else if (index < 37 && !this.moveItemStackTo(stack, 1, 28, false)) {
	                    return ItemStack.EMPTY;
	                }
	            }

	            if (stack.isEmpty()) {
	                slot.set(ItemStack.EMPTY);
	            } else {
	                slot.setChanged();
	            }

	            if (stack.getCount() == itemstack.getCount()) {
	                return ItemStack.EMPTY;
	            }

	            slot.onTake(playerIn, stack);
	        }

	        return itemstack;
	    }
	

}








