package com.builtbroken.merpig;

import com.builtbroken.merpig.entity.EntityMerpig;
import com.builtbroken.merpig.entity.RenderMerpig;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 3/28/2018.
 */
@Mod.EventBusSubscriber(value = Side.CLIENT, modid = Merpig.DOMAIN)
public class ClientReg
{
    @SubscribeEvent
    public static void registerAllModels(ModelRegistryEvent event)
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityMerpig.class, manager -> new RenderMerpig(manager));
        ModelLoader.setCustomModelResourceLocation(Merpig.itemStick, 0, new ModelResourceLocation(Merpig.itemStick.getRegistryName(), "inventory"));
    }
}
