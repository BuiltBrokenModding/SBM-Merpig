package com.builtbroken.merpig.entity;

import java.util.Random;

import javax.annotation.Nullable;

import com.builtbroken.merpig.Merpig;
import com.builtbroken.merpig.animation.Animation;
import com.builtbroken.merpig.item.ItemSeagrassOnStick;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Pig that swims through water :P
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 3/28/2018.
 */
public class EntityMerpig extends WaterMobEntity
{
    //Data values
    private static final DataParameter<Boolean> SADDLED = EntityDataManager.<Boolean>createKey(EntityMerpig.class, DataSerializers.BOOLEAN);

    //Constants
    public static final String NBT_SADDLE = "Saddle";

    //Settings TODO move to config
    public static float WATER_MOVEMENT_FACTOR = 0.8f;
    public static float WATER_MOVEMENT_FACTOR_RIDDEN = 0.99f;

    /** Storage for animation state */ //TODO move to capability?
    @OnlyIn(Dist.CLIENT)
    public Animation rotationStorage;

    //Movement vector
    private float randomMotionVecX;
    private float randomMotionVecY;
    private float randomMotionVecZ;

    public EntityMerpig(World worldIn)
    {
        this(Merpig.MERPIG_ENTITY_TYPE, worldIn);
    }

    public EntityMerpig(EntityType<? extends EntityMerpig> type, World worldIn)
    {
        super(type, worldIn);
    }

    @Override
    protected void registerData()
    {
        super.registerData();
        this.dataManager.register(SADDLED, Boolean.valueOf(false));
        //this.dataManager.register(BOOST_TIME, Integer.valueOf(0)); TODO add
    }

    @Override
    protected void registerGoals()
    {
        this.goalSelector.addGoal(0, new AIMoveRandom(this));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
    }

    @Override
    protected void registerAttributes()
    {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
    }

    @Override
    @Nullable
    public Entity getControllingPassenger()
    {
        return this.getPassengers().isEmpty() ? null : (Entity) this.getPassengers().get(0);
    }

    @Override
    public boolean canBeSteered()
    {
        Entity entity = this.getControllingPassenger();
        if (entity instanceof PlayerEntity)
        {
            PlayerEntity entityplayer = (PlayerEntity) entity;
            return isSteeringItem(entityplayer.getHeldItemMainhand()) || isSteeringItem(entityplayer.getHeldItemOffhand());
        }
        return false;
    }

    protected boolean isSteeringItem(ItemStack stack)
    {
        return stack.getItem() instanceof ItemSeagrassOnStick;  //TODO add support more items
    }

    @Override
    public void livingTick()
    {
        super.livingTick();

        //If in water and not a mount, move randomly to simulate life
        if (isInWater() && !isSaddled())
        {
            if (!this.world.isRemote)
            {
                //Set speed
                float speed = (float) this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue();
                this.setAIMoveSpeed(speed);

                //Move entity in vector direction
                this.setMotion(this.randomMotionVecX * getAIMoveSpeed(), this.randomMotionVecY * getAIMoveSpeed(), this.randomMotionVecZ * getAIMoveSpeed());
            }

            //Update rotation to face movement
            this.renderYawOffset += (-((float) MathHelper.atan2(this.getMotion().x, this.getMotion().z)) * (180F / (float) Math.PI) - this.renderYawOffset) * 0.1F;
            this.rotationYaw = this.renderYawOffset;
        }
    }

    @Override
    public void travel(Vec3d motionIn)
    {
        if (isInWater())
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
                    float speed = (float) this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue();
                    this.setAIMoveSpeed(speed);

                    //Get vertical movement
                    float v = entity.rotationPitch / 180f;
                    super.travel(new Vec3d(0.0F, -v * 4, 1.0F));
                }
                else
                {
                    this.setMotion(getMotion().x * 0.98D, getMotion().y * 0.98D, getMotion().z * 0.98D);
                    move(MoverType.SELF, getMotion());
                }

                //Do animation
                this.prevLimbSwingAmount = this.limbSwingAmount;
                double d1 = this.func_226277_ct_() - this.prevPosX;
                double d0 = this.func_226281_cx_() - this.prevPosZ;
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
                super.travel(motionIn);
            }
        }
        else
        {
            this.setMotion(getMotion().x, getMotion().y - 0.03999999910593033D, getMotion().z);
            move(MoverType.SELF, getMotion());
        }
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand)
    {
        if (!super.processInteract(player, hand))
        {
            ItemStack itemstack = player.getHeldItem(hand);

            if (itemstack.getItem() == Items.NAME_TAG)
            {
                itemstack.interactWithEntity(player, this, hand);
                return true;
            }
            else if (isSaddled() && !this.isBeingRidden())
            {
                if (!this.world.isRemote)
                {
                    player.startRiding(this);
                }
                return true;
            }
            else if (itemstack.getItem() == Items.SADDLE)
            {
                if (!isSaddled())
                {
                    setSaddled(true);
                    world.playSound(player, func_226277_ct_(), func_226278_cu_(), func_226281_cx_(), SoundEvents.ENTITY_PIG_SADDLE, SoundCategory.NEUTRAL, 0.5F, 1.0F);
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
            if (this.isSaddled())
            {
                this.entityDropItem(Items.SADDLE, 1);
            }
        }
    }

    public static boolean canSpawn(EntityType<? extends EntityMerpig> type, IWorld world, SpawnReason reason, BlockPos spawnPos, Random random)
    {
        return world.getBlockState(spawnPos).getBlock() == Blocks.WATER && world.getBlockState(spawnPos.up()).getBlock() == Blocks.WATER;
    }

    /**
     * Returns true if the pig is saddled.
     */
    public boolean isSaddled()
    {
        return this.dataManager.get(SADDLED).booleanValue();
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
    public void writeAdditional(CompoundNBT compound)
    {
        super.writeAdditional(compound);
        compound.putBoolean(NBT_SADDLE, this.isSaddled());
    }

    @Override
    public void readAdditional(CompoundNBT compound)
    {
        super.readAdditional(compound);
        this.setSaddled(compound.getBoolean(NBT_SADDLE));
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
    public boolean canBeRiddenInWater(Entity rider) {
        return true;
    }

    @Override
    public boolean hasNoGravity()
    {
        return isInWater() || super.hasNoGravity();
    }

    @Override
    protected float getWaterSlowDown()
    {
        return isBeingRidden() ? WATER_MOVEMENT_FACTOR_RIDDEN : WATER_MOVEMENT_FACTOR;
    }

    @Override
    protected boolean func_225502_at_() //canTriggerWalking
    {
        return false;
    }

    @Override
    public boolean preventDespawn()
    {
        return true;
    }

    public void setMovementVector(float randomMotionVecXIn, float randomMotionVecYIn, float randomMotionVecZIn)
    {
        this.randomMotionVecX = randomMotionVecXIn;
        this.randomMotionVecY = randomMotionVecYIn;
        this.randomMotionVecZ = randomMotionVecZIn;
    }

    public boolean hasMovementVector()
    {
        return this.randomMotionVecX != 0.0F || this.randomMotionVecY != 0.0F || this.randomMotionVecZ != 0.0F;
    }
}
