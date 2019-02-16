package com.builtbroken.merpig.entity;

import javax.annotation.Nullable;

import com.builtbroken.merpig.Merpig;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderMerpig extends RenderLiving<EntityMerpig>
{
    public static final ResourceLocation resourceLocation = new ResourceLocation(Merpig.DOMAIN, "textures/models/merpig.png");

    public RenderMerpig(RenderManager rendermanagerIn)
    {
        super(rendermanagerIn, new ModelMerpig(), 0);
        this.addLayer(new LayerSaddleMerpig(this));
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityMerpig entity)
    {
        return resourceLocation;
    }
}
