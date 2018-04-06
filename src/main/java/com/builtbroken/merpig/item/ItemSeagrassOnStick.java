package com.builtbroken.merpig.item;

import com.builtbroken.merpig.Merpig;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 4/5/2018.
 */
public class ItemSeagrassOnStick extends Item
{
    public ItemSeagrassOnStick()
    {
        this.setCreativeTab(CreativeTabs.TRANSPORTATION);
        this.setUnlocalizedName(Merpig.PREFIX + "seagrass_on_a_stick");
        this.setRegistryName(Merpig.DOMAIN, "seagrassonastick");
        this.setMaxStackSize(1);
        this.setMaxDamage(25);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldRotateAroundWhenRendering()
    {
        return true;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        //TODO implement boosting from pig
        /** {@link net.minecraft.item.ItemCarrotOnAStick } */
        return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
    }
}
