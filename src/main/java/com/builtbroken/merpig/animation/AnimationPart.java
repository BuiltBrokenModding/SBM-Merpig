package com.builtbroken.merpig.animation;

import net.minecraft.client.renderer.entity.model.ModelRenderer;

/**
 * Handles animation loop for a single part of a model
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 4/5/2018.
 */
public class AnimationPart
{
    /** Part to animate */
    public final ModelRenderer renderer;

    /** First step in the linked list of animations, is always the default rotations */
    protected AnimationStep first;
    /** Last step in the linked list of animations */
    protected AnimationStep last;

    float prev_rotateAngleX;
    float prev_rotateAngleY;
    float prev_rotateAngleZ;

    public AnimationPart(ModelRenderer renderer)
    {
        this.renderer = renderer;
        //Create first
        first = new AnimationStep(0, (float) Math.toDegrees(renderer.rotateAngleX), (float) Math.toDegrees(renderer.rotateAngleY), (float) Math.toDegrees(renderer.rotateAngleZ), true, false);

        //Copy base data
        prev_rotateAngleX = first.rotateAngleX;
        prev_rotateAngleY = first.rotateAngleY;
        prev_rotateAngleZ = first.rotateAngleZ;

        //Wrap first to self
        last = first;
    }

    /**
     * Called to copy the current rotations into the part
     *
     * @param part - part to copy data into
     */
    public void copyRotations(AnimationPart part)
    {
        part.prev_rotateAngleX = prev_rotateAngleX;
        part.prev_rotateAngleY = prev_rotateAngleY;
        part.prev_rotateAngleZ = prev_rotateAngleZ;
    }

    /**
     * Called to set rotation data
     *
     * @param part - part to take data from
     */
    public void setRotations(AnimationPart part)
    {
        prev_rotateAngleX = part.prev_rotateAngleX;
        prev_rotateAngleY = part.prev_rotateAngleY;
        prev_rotateAngleZ = part.prev_rotateAngleZ;
    }


    /**
     * Called to apply animation to part
     *
     * @param time  - current animation time (used to get current animation step and progress)
     * @param delta - time since last frame render (used to smooth animation only)
     */
    public void apply(int time, float delta)
    {
        //Figure out what step we are on based on time
        AnimationStep currentStep = first.nextStep;
        int count = 0;
        while (currentStep != first)
        {
            //Count duration of each step
            count += currentStep.duraction;

            //If time is less than count, then step is current step to run
            if (time < count)
            {
                //Get start of step
                int start = count - currentStep.duraction;

                //Get amount of time already used
                int progress = time - start;

                //Trigger
                currentStep.apply(renderer, progress, delta);

                //Exit
                break;
            }
            currentStep = currentStep.nextStep;
        }

        //Track previous rotation
        prev_rotateAngleX = renderer.rotateAngleX;
        prev_rotateAngleY = renderer.rotateAngleY;
        prev_rotateAngleZ = renderer.rotateAngleZ;
    }

    /**
     * Adds a step to the end of the linked list
     *
     * @param step - step to add
     * @return animation part
     */
    public AnimationPart addStep(AnimationStep step)
    {
        AnimationStep prev = last;

        //Set prev on current
        step.previousStep = prev;
        last = step;

        //Set next on prev
        prev.nextStep = step;

        //Set current next to first
        step.nextStep = first;

        //Set host
        step.host = this;
        return this;
    }
}
