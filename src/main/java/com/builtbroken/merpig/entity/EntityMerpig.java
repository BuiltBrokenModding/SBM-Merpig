package com.builtbroken.merpig.entity;

import com.builtbroken.merpig.animation.Animation;
import com.builtbroken.merpig.item.ItemSeagrassOnStick;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

/**
 * Pig that swims through water :P
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 3/28/2018.
 */
public class EntityMerpig extends EntityWaterMob
{
    private static final DataParameter<Boolean> SADDLED = EntityDataManager.<Boolean>createKey(EntityPig.class, DataSerializers.BOOLEAN);

    @SideOnly(Side.CLIENT)
    public Animation rotationStorage;

    public EntityMerpig(World worldIn)
    {
        super(worldIn);
        this.setSize(0.8F, 0.8F);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(SADDLED, Boolean.valueOf(false));
        //this.dataManager.register(BOOST_TIME, Integer.valueOf(0)); TODO add
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
    }

    @Nullable
    public Entity getControllingPassenger()
    {
        return this.getPassengers().isEmpty() ? null : (Entity) this.getPassengers().get(0);
    }

    @Override
    public boolean canBeSteered()
    {
        Entity entity = this.getControllingPassenger();
        if (entity instanceof EntityPlayer)
        {
            EntityPlayer entityplayer = (EntityPlayer) entity;
            return entityplayer.getHeldItemMainhand().getItem() instanceof ItemSeagrassOnStick || entityplayer.getHeldItemOffhand().getItem() instanceof ItemSeagrassOnStick;
        }
        return false;
    }

    @Override
    public void travel(float strafe, float vertical, float forward)
    {
        if(isInWater())
        {
            Entity entity = this.getPassengers().isEmpty() ? null : (Entity) this.getPassengers().get(0);

            if (this.isBeingRidden() && this.canBeSteered())
            {
                //Sync rotation
                this.prevRotationYaw = this.rotationYaw = entity.rotationYaw;
                this.rotationPitch = entity.rotationPitch * 0.5F;
                this.setRotation(this.rotationYaw, this.rotationPitch);
                this.rotationYawHead = this.renderYawOffset = this.rotationYaw;

                //Set jump height
                this.stepHeight = 0;
                this.jumpMovementFactor = this.getAIMoveSpeed() * 0.1F;

                //Do movement
                if (this.canPassengerSteer())
                {
                    //Set speed
                    float speed = (float) this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
                    this.setAIMoveSpeed(speed);

                    //Get vertical movement
                    float v = entity.rotationPitch / 180f;

                    super.travel(0.0F, -v * 4, 1.0F);
                }
                else
                {
                    this.motionX *= 0.98D;
                    this.motionY *= 0.98D;
                    this.motionZ *= 0.98D;
                    move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
                }

                //Do animation
                this.prevLimbSwingAmount = this.limbSwingAmount;
                double d1 = this.posX - this.prevPosX;
                double d0 = this.posZ - this.prevPosZ;
                float f1 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;
                if (f1 > 1.0F)
                {
                    f1 = 1.0F;
                }
                this.limbSwingAmount += (f1 - this.limbSwingAmount) * 0.4F;
                this.limbSwing += this.limbSwingAmount;
            }
            else
            {
                this.stepHeight = 0.5F;
                this.jumpMovementFactor = 0.02F;
                super.travel(strafe, vertical, forward);
            }
        }
        else
        {
            this.motionY -= 0.03999999910593033D;
            move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
        }
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
        if (!super.processInteract(player, hand))
        {
            ItemStack itemstack = player.getHeldItem(hand);

            if (itemstack.getItem() == Items.NAME_TAG)
            {
                itemstack.interactWithEntity(player, this, hand);
                return true;
            }
            else if (getSaddled() && !this.isBeingRidden())
            {
                if (!this.world.isRemote)
                {
                    player.startRiding(this);
                }
                return true;
            }
            else if (itemstack.getItem() == Items.SADDLE)
            {
                if (!getSaddled())
                {
                    setSaddled(true);
                    world.playSound(player, posX, posY, posZ, SoundEvents.ENTITY_PIG_SADDLE, SoundCategory.NEUTRAL, 0.5F, 1.0F);
                    itemstack.shrink(1);
                }
                return true;
            }
            return false;
        }
        return true;
    }

    @Override
    public void onDeath(DamageSource cause)
    {
        super.onDeath(cause);

        if (!this.world.isRemote)
        {
            if (this.getSaddled())
            {
                this.dropItem(Items.SADDLE, 1);
            }
        }
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable()
    {
        return LootTableList.ENTITIES_PIG;
    }

    /**
     * Returns true if the pig is saddled.
     */
    public boolean getSaddled()
    {
        return ((Boolean) this.dataManager.get(SADDLED)).booleanValue();
    }

    /**
     * Set or remove the saddle of the pig.
     */
    public void setSaddled(boolean saddled)
    {
        if (saddled)
        {
            this.dataManager.set(SADDLED, Boolean.valueOf(true));
        }
        else
        {
            this.dataManager.set(SADDLED, Boolean.valueOf(false));
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setBoolean("Saddle", this.getSaddled());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.setSaddled(compound.getBoolean("Saddle"));
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_PIG_AMBIENT; //TODO change
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_PIG_HURT; //TODO change
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_PIG_DEATH; //TODO change
    }

    @Override
    public boolean shouldDismountInWater(Entity rider)
    {
        return false;
    }

    @Override
    public boolean hasNoGravity()
    {
        return isInWater() || super.hasNoGravity();
    }

    @Override
    protected float getWaterSlowDown()
    {
        return 1F;
    }

    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }

    @Override
    protected boolean canDespawn()
    {
        return false;
    }
}
