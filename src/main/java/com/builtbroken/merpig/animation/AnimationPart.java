package com.builtbroken.merpig.animation;

import net.minecraft.client.model.ModelRenderer;

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

    public AnimationPart(ModelRenderer renderer)
    {
        this.renderer = renderer;
        first = new AnimationStep(0, renderer.rotateAngleX, renderer.rotateAngleY, renderer.rotateAngleZ, true, false);
        last = first;
    }

    /**
     * Called to apply animation to part
     *
     * @param time  - current animation time (used to get current animation step and progress)
     * @param delta - time since last frame render (used to smooth animation only)
     */
    public void apply(int time, float delta)
    {
        //TODO find a faster way to get current step
        int count = 0;

        //Figure out what step we are on based on time
        AnimationStep currentStep = first.nextStep;
        while (currentStep != first.nextStep)
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
                return;
            }
        }
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
        return this;
    }
}
