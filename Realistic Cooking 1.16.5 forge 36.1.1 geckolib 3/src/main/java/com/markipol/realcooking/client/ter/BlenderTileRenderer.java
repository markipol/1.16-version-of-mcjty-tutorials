package com.markipol.realcooking.client.ter;

import com.markipol.realcooking.common.geomodels.BlenderModel;
import com.markipol.realcooking.common.tileentities.BlenderTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class BlenderTileRenderer extends GeoBlockRenderer<BlenderTileEntity>{

	public BlenderTileRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn, new BlenderModel());
		// TODO Auto-generated constructor stub
	}
	
@Override
public RenderType getRenderType(BlenderTileEntity animatable, float partialTicks, MatrixStack stack,
		IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn,
		ResourceLocation textureLocation) {
	// TODO Auto-generated method stub
	return RenderType.entityTranslucent(getTextureLocation(animatable));
}
	
	

}
