package com.builtbroken.merpig.animation;

import net.minecraft.client.model.ModelRenderer;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 4/5/2018.
 */
public class AnimationStep
{
    public AnimationStep previousStep;
    public AnimationStep nextStep;

    boolean rotateX = false;
    boolean rotateY = false;
    boolean rotateZ = false;

    float rotateAngleX;
    float rotateAngleY;
    float rotateAngleZ;

    boolean lerp;

    int duraction;

    public AnimationStep(int duration, float x, float y, float z, boolean lerp, boolean zeroDisable)
    {
        this.duraction = duration;
        this.rotateAngleX = x;
        this.rotateAngleY = y;
        this.rotateAngleZ = z;
        this.lerp = lerp;

        if (zeroDisable)
        {
            if (x > 0.0001 || x < -0.001)
            {
                enableX();
            }
            if (y > 0.0001 || y < -0.001)
            {
                enableY();
            }
            if (z > 0.0001 || z < -0.001)
            {
                enableZ();
            }
        }
        else
        {
            enableX();
            enableY();
            enableZ();
        }
    }

    public void apply(ModelRenderer renderer, int time, float deltaTime)
    {
        if (rotateX)
        {
            renderer.rotateAngleX = lerp ? lerp(renderer.rotateAngleX, rotateAngleX, deltaTime) : rotateAngleX;
        }
        if (rotateY)
        {
            renderer.rotateAngleY = lerp ? lerp(renderer.rotateAngleY, rotateAngleY, deltaTime) : rotateAngleY;
        }
        if (rotateZ)
        {
            //TODO cache movement rate, as it should only be run on setup

            //Calculate movement rate
            float prev_rotation = previousStep != null ? previousStep.rotateAngleZ : 0;
            float travel_distance = Math.abs(prev_rotation - rotateAngleZ);
            float movementR_rate = travel_distance / duraction;

            //Get rotation for current time
            float rotation = prev_rotation + movementR_rate * time;

            //Set rotation
            renderer.rotateAngleZ = lerp ? lerp(renderer.rotateAngleZ, rotation, deltaTime) : rotateAngleZ;
        }
    }

    public AnimationStep enableX()
    {
        this.rotateX = true;
        return this;
    }

    public AnimationStep enableY()
    {
        this.rotateY = true;
        return this;
    }

    public AnimationStep enableZ()
    {
        this.rotateZ = true;
        return this;
    }

    protected final float lerp(float current, float desired, float movementPercentage)
    {
        return current + movementPercentage * (desired - current);
    }
}
