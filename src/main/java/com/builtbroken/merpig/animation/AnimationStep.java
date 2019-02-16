package com.builtbroken.merpig.animation;

import net.minecraft.client.renderer.entity.model.ModelRenderer;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 4/5/2018.
 */
public class AnimationStep
{
    public AnimationStep previousStep;
    public AnimationStep nextStep;
    public AnimationPart host;

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
        this.rotateAngleX = (float) Math.toRadians(x);
        this.rotateAngleY = (float) Math.toRadians(y);
        this.rotateAngleZ = (float) Math.toRadians(z);
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
            float startingRotation = previousStep != null ? previousStep.rotateAngleX : 0;
            renderer.rotateAngleX = calculateRotation(host.prev_rotateAngleX, startingRotation, rotateAngleX, time, deltaTime);
        }
        if (rotateY)
        {
            float startingRotation = previousStep != null ? previousStep.rotateAngleY : 0;
            renderer.rotateAngleY = calculateRotation(host.prev_rotateAngleY, startingRotation, rotateAngleY, time, deltaTime);
        }
        if (rotateZ)
        {
            float startingRotation = previousStep != null ? previousStep.rotateAngleZ : 0;
            renderer.rotateAngleZ = calculateRotation(host.prev_rotateAngleZ, startingRotation, rotateAngleZ, time, deltaTime);
        }
    }

    protected float calculateRotation(float current_rotation, float startingRotation, float goalRotation, int time, float deltaTime)
    {
        //Figure out how far we need to move
        float movementDirection = getMovement(startingRotation, goalRotation);

        //Fire out how far we move per tick
        float movementPerTime = movementDirection / duraction;

        //Get rotation for current time
        float movement = movementPerTime * time;
        float rotation = startingRotation + movement;

        //Lerp rotation id desired
        return lerp ? lerp(current_rotation, rotation, deltaTime) : rotation;
    }

    protected float getMovement(float start, float end)
    {
        return -(start - end);
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
