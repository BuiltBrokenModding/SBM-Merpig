package com.builtbroken.merpig.entity;

import javax.annotation.Nullable;

import com.builtbroken.merpig.Merpig;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderMerpig extends LivingRenderer<EntityMerpig,ModelMerpig>
{
    public static final ResourceLocation resourceLocation = new ResourceLocation(Merpig.DOMAIN, "textures/models/merpig.png");

    public RenderMerpig(EntityRendererManager rendermanagerIn)
    {
        super(rendermanagerIn, new ModelMerpig(), 0);
        this.addLayer(new LayerSaddleMerpig(this));
    }

    @Override
    protected boolean canRenderName(EntityMerpig entity) //disable always rendering the name
    {
        return entity.hasCustomName();
    }

    @Nullable
    @Override
    public ResourceLocation getEntityTexture(EntityMerpig entity)
    {
        return resourceLocation;
    }
}
