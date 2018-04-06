package com.builtbroken.merpig.entity;

import com.builtbroken.merpig.animation.Animation;
import com.builtbroken.merpig.animation.AnimationPart;
import com.builtbroken.merpig.animation.AnimationStep;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

/**
 * ModelPig
 * Created using Tabula 7.0.0
 *
 * @author Finemanederby
 */
public class ModelMerpig extends ModelBase
{
    Animation currentAnimation;

    public ModelRenderer merpigbody;
    public ModelRenderer flipper1;
    public ModelRenderer flipper2;
    public ModelRenderer merpighead;
    public ModelRenderer merpignose;
    public ModelRenderer TailBase;
    public ModelRenderer headfin;
    public ModelRenderer TailMiddle;
    public ModelRenderer FinStart;
    public ModelRenderer FinMiddle;
    public ModelRenderer FinEnd;

    public ModelMerpig()
    {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.TailBase = new ModelRenderer(this, 66, 20);
        this.TailBase.setRotationPoint(-4.0F, 11.0F, 6.0F);
        this.TailBase.addBox(0.0F, 0.0F, 0.0F, 8, 6, 5, 0.0F);
        this.flipper1 = new ModelRenderer(this, 0, 36);
        this.flipper1.setRotationPoint(5.4F, 16.0F, -5.0F);
        this.flipper1.addBox(-2.0F, 0.0F, -2.0F, 1, 6, 5, 0.0F);
        this.setRotateAngle(flipper1, 0.0F, 0.0F, -0.7285004297824331F);
        this.TailMiddle = new ModelRenderer(this, 96, 21);
        this.TailMiddle.setRotationPoint(2.0F, 1.5F, 4.0F);
        this.TailMiddle.addBox(0.0F, 0.0F, 0.0F, 4, 3, 6, 0.0F);
        this.FinMiddle = new ModelRenderer(this, 42, 36);
        this.FinMiddle.setRotationPoint(-5.551115123125783E-17F, 0.0F, 4.0F);
        this.FinMiddle.addBox(0.0F, 0.0F, 0.0F, 1, 7, 5, 0.0F);
        this.merpignose = new ModelRenderer(this, 16, 16);
        this.merpignose.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.merpignose.addBox(-2.0F, 0.0F, -9.0F, 4, 3, 1, 0.0F);
        this.FinEnd = new ModelRenderer(this, 59, 38);
        this.FinEnd.setRotationPoint(0.0F, -1.0F, 4.0F);
        this.FinEnd.addBox(0.0F, 0.0F, 0.0F, 1, 9, 2, 0.0F);
        this.merpigbody = new ModelRenderer(this, 28, 8);
        this.merpigbody.setRotationPoint(0.0F, 11.0F, 2.0F);
        this.merpigbody.addBox(-5.0F, -10.0F, -7.0F, 10, 15, 8, 0.0F);
        this.setRotateAngle(merpigbody, 1.5707963267948966F, 0.0F, 0.0F);
        this.headfin = new ModelRenderer(this, 66, 0);
        this.headfin.setRotationPoint(-0.5F, -10.0F, -6.0F);
        this.headfin.addBox(0.0F, 0.0F, 0.0F, 1, 7, 7, 0.0F);
        this.setRotateAngle(headfin, -0.27314402793711257F, 0.0F, 0.0F);
        this.FinStart = new ModelRenderer(this, 42, 36);
        this.FinStart.setRotationPoint(1.5F, -2.0F, 4.0F);
        this.FinStart.addBox(0.0F, 0.0F, 0.0F, 1, 7, 5, 0.0F);
        this.flipper2 = new ModelRenderer(this, 0, 36);
        this.flipper2.setRotationPoint(-3.0F, 18.0F, -5.0F);
        this.flipper2.addBox(-2.0F, 0.0F, -2.0F, 1, 6, 5, 0.0F);
        this.setRotateAngle(flipper2, 0.0F, 0.0F, 0.7285004297824331F);
        this.merpighead = new ModelRenderer(this, 0, 0);
        this.merpighead.setRotationPoint(0.0F, 12.0F, -6.0F);
        this.merpighead.addBox(-4.0F, -4.0F, -8.0F, 8, 8, 8, 0.0F);
        this.TailBase.addChild(this.TailMiddle);
        this.FinStart.addChild(this.FinMiddle);
        this.merpighead.addChild(this.merpignose);
        this.FinMiddle.addChild(this.FinEnd);
        this.merpighead.addChild(this.headfin);
        this.TailMiddle.addChild(this.FinStart);

        //Animations
        currentAnimation = new Animation(32);
        currentAnimation.add(
                new AnimationPart(flipper1)
                        .addStep(new AnimationStep(4, 0, 0, -65f, true, true))
                        .addStep(new AnimationStep(8, 0, 0, -18.48f, true, true))
                        .addStep(new AnimationStep(6, 0, 0, -65f, true, true))
                        .addStep(new AnimationStep(8, 0, 0, -18.48f, true, true))
                        .addStep(new AnimationStep(3, 0, 0, -35.92f, true, true))
        );

        currentAnimation.add(
                new AnimationPart(flipper2)
                        .addStep(new AnimationStep(4, 0, 0, 65f, true, true))
                        .addStep(new AnimationStep(8, 0, 0, 18.48f, true, true))
                        .addStep(new AnimationStep(6, 0, 0, 65f, true, true))
                        .addStep(new AnimationStep(8, 0, 0, 18.48f, true, true))
                        .addStep(new AnimationStep(3, 0, 0, 35.92f, true, true))
        );

        currentAnimation.add(
                new AnimationPart(TailBase)
                        .addStep(new AnimationStep(9, 0, -8, 0, true, true))
                        .addStep(new AnimationStep(16, 0, 8, 0, true, true))
                        .addStep(new AnimationStep(7, 0, 1, 0, true, true))
        );

        currentAnimation.add(
                new AnimationPart(TailMiddle)
                        .addStep(new AnimationStep(9, 0, -14, 0, true, true))
                        .addStep(new AnimationStep(16, 0, 14, 0, true, true))
                        .addStep(new AnimationStep(7, 0, 1.75f, 0, true, true))
        );

        currentAnimation.add(
                new AnimationPart(FinStart)
                        .addStep(new AnimationStep(9, 0, -20, 0, true, true))
                        .addStep(new AnimationStep(16, 0, 20, 0, true, true))
                        .addStep(new AnimationStep(7, 0, 2.5f, 0, true, true))
        );

        currentAnimation.add(
                new AnimationPart(FinMiddle)
                        .addStep(new AnimationStep(2, 0, 30, 0, true, true))
                        .addStep(new AnimationStep(6, 0, 30, 0, true, true)) //Pause
                        .addStep(new AnimationStep(4, 0, -30, 0, true, true))
                        .addStep(new AnimationStep(12, 0, -30, 0, true, true)) //Pause
                        .addStep(new AnimationStep(2, 0, 0, 0, true, true))
                        .addStep(new AnimationStep(5, 0, 0, 0, true, true)) //Pause
        );
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        this.flipper2.render(f5);
        this.flipper1.render(f5);
        this.TailBase.render(f5);
        this.merpigbody.render(f5);
        //this.merpignose.render(f5);
        this.merpighead.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public void setLivingAnimations(EntityLivingBase entity, float limbSwing, float limbSwingAmount, float partialTickTime)
    {
        if (entity instanceof EntityMerpig)
        {
            EntityMerpig merpig = (EntityMerpig) entity;

            //Create object to save rotation states per entity
            if (merpig.rotationStorage == null)
            {
                merpig.rotationStorage = new Animation(0)
                        .add(new AnimationPart(merpigbody))
                        .add(new AnimationPart(flipper1))
                        .add(new AnimationPart(flipper2))
                        .add(new AnimationPart(merpighead))
                        .add(new AnimationPart(merpignose))
                        .add(new AnimationPart(TailBase))
                        .add(new AnimationPart(headfin))
                        .add(new AnimationPart(TailMiddle))
                        .add(new AnimationPart(FinStart))
                        .add(new AnimationPart(FinMiddle))
                        .add(new AnimationPart(FinEnd));
            }

            //Restore rotation data
            currentAnimation.restoreRotations(merpig.rotationStorage);

            //Render current animation state
            //System.out.println((String.format("[%s]Render Data: %s %s %s", entitylivingbaseIn, time, progress, partialTickTime)));
            float progress = (entity.ticksExisted % currentAnimation.duration / (float)currentAnimation.duration);
            currentAnimation.applyFromProgress(progress, partialTickTime);

            //Store rotation data
            currentAnimation.copyRotations(merpig.rotationStorage);
        }
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        this.merpighead.rotateAngleX = headPitch * 0.017453292F;
        this.merpighead.rotateAngleY = netHeadYaw * 0.017453292F;
        this.merpigbody.rotateAngleX = ((float) Math.PI / 2F);
    }
}
