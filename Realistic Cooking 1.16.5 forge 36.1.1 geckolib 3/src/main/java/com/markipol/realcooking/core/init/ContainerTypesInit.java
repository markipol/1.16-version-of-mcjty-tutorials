package com.markipol.realcooking.core.init;

import com.markipol.realcooking.RealCooking;
import com.markipol.realcooking.common.containers.FirstBlockContainer;

import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerTypesInit {
	public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister
			.create(ForgeRegistries.CONTAINERS, RealCooking.MOD_ID);
	public static final RegistryObject<ContainerType<FirstBlockContainer>> FIRST_BLOCK_CONTAINER = CONTAINER_TYPES
			.register("first_block", () -> IForgeContainerType.create((windowId, inv, data) -> {
				BlockPos pos = data.readBlockPos();
				World world = inv.player.getCommandSenderWorld();
				return new FirstBlockContainer(windowId, world, pos, inv, inv.player);
			}));

}
