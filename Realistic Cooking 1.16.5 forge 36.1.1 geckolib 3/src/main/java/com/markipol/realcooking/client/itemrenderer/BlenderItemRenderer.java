package com.markipol.realcooking.client.itemrenderer;


import com.markipol.realcooking.common.geomodels.BlenderModel;
import com.markipol.realcooking.common.items.BlenderItem;

import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class BlenderItemRenderer extends GeoItemRenderer<BlenderItem>
{
    public BlenderItemRenderer()
    {
        super(new BlenderModel());
    }
}