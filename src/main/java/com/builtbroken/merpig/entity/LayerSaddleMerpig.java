package com.builtbroken.merpig.entity;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PigModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LayerSaddleMerpig extends LayerRenderer<EntityMerpig,ModelMerpig>
{
    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/pig/pig_saddle.png");
    private final RenderMerpig pigRenderer;
    private final PigModel pigModel = new PigModel(6F);

    public LayerSaddleMerpig(RenderMerpig pigRendererIn)
    {
        super(pigRendererIn);
        this.pigRenderer = pigRendererIn;
    }

    @Override
    public void render(EntityMerpig entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        if (entitylivingbaseIn.isSaddled())
        {
            this.pigRenderer.bindTexture(TEXTURE);
            pigModel.setModelAttributes(this.pigRenderer.getEntityModel());
            GlStateManager.pushMatrix();
            GlStateManager.translated(0.0D, -0.275D, 0.0D); //position the saddle correctly
            pigModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            GlStateManager.popMatrix();
        }
    }

    @Override
    public boolean shouldCombineTextures()
    {
        return false;
    }
}