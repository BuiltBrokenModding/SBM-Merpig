package com.builtbroken.merpig.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * ModelPig
 * Created using Tabula 7.0.0
 * @author Finemanederby
 */
public class ModelMerpig extends ModelBase {
    public ModelRenderer merpigbody;
    public ModelRenderer flipper1;
    public ModelRenderer flipper2;
    public ModelRenderer merpighead;
    public ModelRenderer merpignose;
    public ModelRenderer tail1;
    public ModelRenderer tail2;
    public ModelRenderer tail3;
    public ModelRenderer tail4;
    public ModelRenderer tail5;
    public ModelRenderer headfin;

    public ModelMerpig() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.tail3 = new ModelRenderer(this, 96, 23);
        this.tail3.setRotationPoint(-2.0F, 13.0F, 14.3F);
        this.tail3.addBox(0.0F, 0.0F, 0.0F, 4, 2, 5, 0.0F);
        this.tail4 = new ModelRenderer(this, 39, 33);
        this.tail4.setRotationPoint(-0.5F, 11.0F, 18.4F);
        this.tail4.addBox(0.0F, 0.0F, 0.0F, 1, 7, 8, 0.0F);
        this.tail1 = new ModelRenderer(this, 66, 20);
        this.tail1.setRotationPoint(-4.0F, 11.0F, 6.3F);
        this.tail1.addBox(0.0F, 0.0F, 0.0F, 8, 6, 5, 0.0F);
        this.headfin = new ModelRenderer(this, 66, 0);
        this.headfin.setRotationPoint(-0.5F, 2.1F, -12.0F);
        this.headfin.addBox(0.0F, 0.0F, 0.0F, 1, 7, 7, 0.0F);
        this.setRotateAngle(headfin, -0.27314402793711257F, 0.0F, 0.0F);
        this.tail5 = new ModelRenderer(this, 59, 38);
        this.tail5.setRotationPoint(-0.5F, 10.0F, 24.3F);
        this.tail5.addBox(0.0F, 0.0F, 0.0F, 1, 9, 2, 0.0F);
        this.flipper1 = new ModelRenderer(this, 0, 36);
        this.flipper1.setRotationPoint(5.4F, 16.6F, -5.0F);
        this.flipper1.addBox(-2.0F, 0.0F, -2.0F, 1, 6, 5, 0.0F);
        this.setRotateAngle(flipper1, 0.0F, 0.0F, -0.7285004297824331F);
        this.merpighead = new ModelRenderer(this, 0, 0);
        this.merpighead.setRotationPoint(0.0F, 12.0F, -6.0F);
        this.merpighead.addBox(-4.0F, -4.0F, -8.0F, 8, 8, 8, 0.0F);
        this.merpignose = new ModelRenderer(this, 16, 16);
        this.merpignose.setRotationPoint(0.0F, 12.0F, -6.0F);
        this.merpignose.addBox(-2.0F, 0.0F, -9.0F, 4, 3, 1, 0.0F);
        this.merpigbody = new ModelRenderer(this, 28, 8);
        this.merpigbody.setRotationPoint(0.0F, 11.0F, 2.0F);
        this.merpigbody.addBox(-5.0F, -10.0F, -7.0F, 10, 15, 8, 0.0F);
        this.setRotateAngle(merpigbody, 1.5707963267948966F, 0.0F, 0.0F);
        this.flipper2 = new ModelRenderer(this, 0, 36);
        this.flipper2.setRotationPoint(-3.0F, 18.3F, -5.0F);
        this.flipper2.addBox(-2.0F, 0.0F, -2.0F, 1, 6, 5, 0.0F);
        this.setRotateAngle(flipper2, -0.0F, 0.0F, 0.7285004297824331F);
        this.tail2 = new ModelRenderer(this, 71, 34);
        this.tail2.setRotationPoint(-3.0F, 12.0F, 10.3F);
        this.tail2.addBox(0.0F, 0.0F, 0.0F, 6, 4, 5, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.tail3.render(f5);
        this.tail4.render(f5);
        this.tail1.render(f5);
        this.headfin.render(f5);
        this.tail5.render(f5);
        this.flipper1.render(f5);
        this.merpighead.render(f5);
        this.merpignose.render(f5);
        this.merpigbody.render(f5);
        this.flipper2.render(f5);
        this.tail2.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
