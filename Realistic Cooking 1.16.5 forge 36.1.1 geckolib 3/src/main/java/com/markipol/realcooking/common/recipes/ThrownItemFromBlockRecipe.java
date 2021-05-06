package com.markipol.realcooking.common.recipes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.markipol.realcooking.RealCooking;
import com.markipol.realcooking.core.init.RecipeInit;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class ThrownItemFromBlockRecipe implements IRecipe<IInventory> {
	public static final Logger LOGGER = LogManager.getLogger();
	public static final Serializer SERIALIZER = new Serializer();

	private final Ingredient input;
	private final ItemStack output;
	private final Block block;
	private final ResourceLocation id;

	private final Item outputItem;

	private final int amountGiven;

	private final int amountReceived;

	public ThrownItemFromBlockRecipe(ResourceLocation id, Ingredient input, ItemStack output, Block block,
			int amountGiven, int amountReceived, Item outputItem) {
		this.output = output;
		this.input = input;
		this.block = block;
		this.id = id;
		this.outputItem = outputItem;
		this.amountGiven = amountGiven;
		this.amountReceived = amountReceived;

	}

	@Override
	public boolean matches(IInventory inv, World world) {
		return this.input.test(inv.getItem(0));
	}

	@Override
	public ItemStack assemble(IInventory p_77572_1_) {
		return this.output.copy();
	}

	@Override
	public boolean canCraftInDimensions(int p_194133_1_, int p_194133_2_) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public ItemStack getResultItem() {
		return this.output;
	}

	@Override
	public ResourceLocation getId() {
		return this.id;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return SERIALIZER;
	}

	@Override
	public IRecipeType<?> getType() {
		return RecipeInit.THROWN;

	}

	@Override
	public ItemStack getToastSymbol() {
		return this.output.copy();
	}

	public boolean isValid(ItemStack input, Block block) {
		return this.input.test(input) && block == this.block;
	}

	public int getAmountGiven() {
		return amountGiven;
	}
	public int getAmountReceived() {
		return amountReceived;
	}
	public String getInputItemString() {
		return input.getItems()[0].getItem().toString();
	}

	private static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>>
			implements IRecipeSerializer<ThrownItemFromBlockRecipe> {
		Serializer() {
			this.setRegistryName(RealCooking.MOD_ID, "thrown");

		}

		@Override
		public ThrownItemFromBlockRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
			final JsonElement inputEl = JSONUtils.isArrayNode(json, "input") ? JSONUtils.getAsJsonArray(json, "input")
					: JSONUtils.getAsJsonObject(json, "input");
			final Ingredient input = Ingredient.fromJson(inputEl);
			final int amountGiven = JSONUtils.getAsJsonObject(json, "input").get("amountGiven").getAsInt();
			final ItemStack output = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(json, "output"));
			final Item outputItem = output.getItem();
			final int amountReceived = JSONUtils.getAsJsonObject(json, "output").get("amountReceived").getAsInt();
			final Block block = ForgeRegistries.BLOCKS
					.getValue(new ResourceLocation(JSONUtils.getAsString(json, "block")));
			if (block == null) {
				throw new IllegalStateException("Block does not exist.");
			} else {
				return new ThrownItemFromBlockRecipe(recipeId, input, output, block, amountGiven, amountReceived,
						outputItem);

			}
		}

		@Override
		public ThrownItemFromBlockRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
			LOGGER.info("Buffer Read Index: " + buffer.readerIndex());
			final Block block = ForgeRegistries.BLOCKS.getValue(buffer.readResourceLocation());
			LOGGER.info("Buffer Read Index: " + buffer.readerIndex());
			final ItemStack output = buffer.readItem();
			LOGGER.info("Buffer Read Index: " + buffer.readerIndex());
			final int amountReceived = buffer.readVarInt();
			LOGGER.info("Buffer Read Index: " + buffer.readerIndex());
			final int amountGiven = buffer.readVarInt();
			LOGGER.info("Buffer Read Index: " + buffer.readerIndex());
			final Ingredient input = Ingredient.fromNetwork(buffer);
			LOGGER.info("Buffer Read Index: " + buffer.readerIndex());

			final Item outputItem = output.getItem();
			if (block == null) {
				throw new IllegalStateException("Block does not exist.");
			} else {
				return new ThrownItemFromBlockRecipe(recipeId, input, output, block, amountGiven, amountReceived,
						outputItem);

			}
		}

		@Override
		public void toNetwork(PacketBuffer buffer, ThrownItemFromBlockRecipe recipe) {
			LOGGER.info("Buffer W Index: " + buffer.writerIndex());
			recipe.input.toNetwork(buffer);
			LOGGER.info("Buffer W Index: " + buffer.writerIndex());
			buffer.writeVarInt(recipe.getAmountGiven());
			LOGGER.info("Buffer W Index: " + buffer.writerIndex());
			buffer.writeVarInt(recipe.amountReceived);
			LOGGER.info("Buffer W Index: " + buffer.writerIndex());
			buffer.writeItemStack(recipe.output, false);
			LOGGER.info("Buffer W Index: " + buffer.writerIndex());
			buffer.writeResourceLocation(recipe.block.getRegistryName());
			LOGGER.info("Buffer W Index: " + buffer.writerIndex());

		}

	}

}
