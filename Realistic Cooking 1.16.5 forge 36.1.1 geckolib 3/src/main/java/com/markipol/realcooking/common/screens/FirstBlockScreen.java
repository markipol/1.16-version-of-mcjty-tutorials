package com.markipol.realcooking.common.screens;

import com.markipol.realcooking.RealCooking;
import com.markipol.realcooking.common.containers.FirstBlockContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class FirstBlockScreen extends ContainerScreen<FirstBlockContainer>{
	private ResourceLocation GUI = new ResourceLocation(RealCooking.MOD_ID, "textures/gui/first_block_gui.png");
	public PlayerEntity player;
	

	public FirstBlockScreen(FirstBlockContainer menu, PlayerInventory inv, ITextComponent name) {
		super(menu, inv, name);
		this.player = inv.player;
		// TODO Auto-generated constructor stub
	}
	@SuppressWarnings("deprecation")
	@Override
	protected void renderBg(MatrixStack m, float x, int y, int t) {
		// TODO Auto-generated method stub
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bind(GUI);
		int relX = (this.width - this.getXSize())/2;
		int relY = (this.height - this.getYSize())/2;
		this.blit(m,relX,relY, 0, 0, this.getXSize(), this.getYSize());
	}
	@Override
	public void render(MatrixStack m, int x, int y, float t) {
		// TODO Auto-generated method stub
		this.renderBackground(m);
		super.render(m, x, y, t);
		this.renderTooltip(m,x,y);
	}
	@Override
	protected void renderLabels(MatrixStack m, int x, int y) {
		drawString(m, Minecraft.getInstance().font, "Energy" + this.menu.getEnergy(), 10, 10, 0xffffff);
	}
	/*	@Override
	protected void render(MatrixStack matrix, float partialTicks, int mouseX, int mouseY) {
		this.renderBackground(matrix);
		super.render(matrix, mouseX, mouseY, partialTicks);
		this.renderTooltip(matrix, mouseX, mouseY);
	}
	
	 */





}
