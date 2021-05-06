package com.markipol.realcooking.core.init;



import com.markipol.realcooking.RealCooking;
import com.markipol.realcooking.client.itemrenderer.BlenderItemRenderer;
import com.markipol.realcooking.common.items.BlenderItem;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
			RealCooking.MOD_ID);
	
	
	public static final RegistryObject<BlenderItem> BLENDER = ITEMS.register("blender",
			() -> new BlenderItem(BlockInit.BLENDER.get(),
					new Item.Properties().tab(ItemGroup.TAB_REDSTONE).setISTER(() -> BlenderItemRenderer::new)));
	public static final RegistryObject<BlockItem> FIRST_BLOCK = ITEMS.register("first_block",
			() -> new BlockItem(BlockInit.FIRST_BLOCK.get(),
					new Item.Properties().tab(ItemGroup.TAB_REDSTONE)));

}
