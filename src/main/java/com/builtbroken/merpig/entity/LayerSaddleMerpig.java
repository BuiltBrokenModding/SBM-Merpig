package com.builtbroken.merpig.entity;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PigModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LayerSaddleMerpig extends LayerRenderer<EntityMerpig,ModelMerpig>
{
    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/pig/pig_saddle.png");
    private final PigModel<EntityMerpig> pigModel = new PigModel<>(6F);

    public LayerSaddleMerpig(RenderMerpig pigRendererIn)
    {
        super(pigRendererIn);
    }

    @Override
    public void render(MatrixStack stack, IRenderTypeBuffer buffer, int p_225628_3_, EntityMerpig entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch)
    {
        if (entity.isSaddled())
        {
            stack.translate(0.0D, 0.45D, 0.0D); //position the saddle correctly
            stack.scale(0.5F, 0.5F, 0.5F); //scale the saddle correctly
            renderCopyCutoutModel(this.getEntityModel(), this.pigModel, TEXTURE, stack, buffer, p_225628_3_, entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTicks, 1, 1, 1);
        }
    }
}