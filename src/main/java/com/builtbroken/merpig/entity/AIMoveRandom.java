package com.builtbroken.merpig.entity;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.MathHelper;

/**
 * Copy of {@link net.minecraft.entity.passive.EntitySquid.AIMoveRandom} for use with Merpig
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 4/6/2018.
 */
class AIMoveRandom extends EntityAIBase
{
    private final EntityMerpig merpig;

    public AIMoveRandom(EntityMerpig pig)
    {
        this.merpig = pig;
    }

    @Override
    public boolean shouldExecute()
    {
        return !merpig.isSaddled() && !merpig.isBeingRidden();
    }

    @Override
    public void updateTask()
    {
        int i = this.merpig.getIdleTime();

        if (i > 100)
        {
            this.merpig.setMovementVector(0.0F, 0.0F, 0.0F);
        }
        else if (this.merpig.getRNG().nextInt(50) == 0 || !this.merpig.isInWater() || !this.merpig.hasMovementVector())
        {
            float f = this.merpig.getRNG().nextFloat() * ((float) Math.PI * 2F);
            float f1 = MathHelper.cos(f) * 0.2F;
            float f2 = -0.1F + this.merpig.getRNG().nextFloat() * 0.2F;
            float f3 = MathHelper.sin(f) * 0.2F;
            this.merpig.setMovementVector(f1, f2, f3);
        }
    }
}
