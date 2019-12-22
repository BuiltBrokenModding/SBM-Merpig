package com.builtbroken.merpig.animation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.client.renderer.model.ModelRenderer;

/**
 * Handles a single animation
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 4/5/2018.
 */
public class Animation
{
    /** List of parts to animate */
    protected final List<AnimationPart> partsToAnimate = new ArrayList<>();
    protected final HashMap<ModelRenderer, AnimationPart> partMap = new HashMap<>();

    /** Total duration of all parts */
    public final int duration;

    /**
     * @param duration - time in ticks for the animation to run
     */
    public Animation(int duration)
    {
        this.duration = duration;
    }

    /**
     * Adds a new part to animate
     *
     * @param part
     * @return
     */
    public Animation add(AnimationPart part)
    {
        partsToAnimate.add(part);
        partMap.put(part.renderer, part);
        return this;
    }

    /**
     * Called to update animation and apply it to the render
     *
     * @param progress - progress of the animation (0.0 - 1.0)
     * @param delta    - time since last frame (Used to smooth animation)
     */
    public void applyFromProgress(float progress, float delta)
    {
        //Get time from progress
        int time = (int) Math.floor(progress * duration);
        apply(time, delta);
    }

    public void applyFromLifeTicks(int ticks, float delta)
    {
        int time = ticks % duration;
        //float progress = (time / (float) animation.duration);
        apply(time, delta);
    }

    public void apply(int time, float delta)
    {
        //Animate all parts
        for (AnimationPart part : partsToAnimate)
        {
            part.apply(time, delta);
        }
    }

    public void restoreRotations(Animation rotationStorage)
    {
        for (AnimationPart part : rotationStorage.partsToAnimate)
        {
            if (partMap.containsKey(part.renderer))
            {
                partMap.get(part.renderer).setRotations(part);
            }
        }
    }

    public void copyRotations(Animation rotationStorage)
    {
        for (AnimationPart part : rotationStorage.partsToAnimate)
        {
            if (partMap.containsKey(part.renderer))
            {
                partMap.get(part.renderer).copyRotations(part);
            }
        }
    }
}
