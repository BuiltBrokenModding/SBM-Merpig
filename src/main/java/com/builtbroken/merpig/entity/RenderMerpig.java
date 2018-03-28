package com.builtbroken.merpig.entity;

import com.builtbroken.merpig.Merpig;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderMerpig extends RenderLiving<EntityMerpig>
{
    public static final ResourceLocation resourceLocation = new ResourceLocation(Merpig.DOMAIN, "textures/models/merpig.png");

    public RenderMerpig(RenderManager rendermanagerIn)
    {
        super(rendermanagerIn, new ModelMerpig(), 0);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityMerpig entity)
    {
        return resourceLocation;
    }
}
