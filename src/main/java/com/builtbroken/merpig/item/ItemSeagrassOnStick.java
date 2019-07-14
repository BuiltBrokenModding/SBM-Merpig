package com.builtbroken.merpig.item;

import com.builtbroken.merpig.Merpig;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 4/5/2018.
 */
public class ItemSeagrassOnStick extends Item
{
    public ItemSeagrassOnStick()
    {
        super(new Item.Properties().group(ItemGroup.TRANSPORTATION).maxStackSize(1).defaultMaxDamage(25));
        this.setRegistryName(Merpig.DOMAIN, "seagrassonastick");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        //TODO implement boosting from pig
        /** {@link net.minecraft.item.ItemCarrotOnAStick } */
        return new ActionResult<ItemStack>(ActionResultType.PASS, itemstack);
    }
}
