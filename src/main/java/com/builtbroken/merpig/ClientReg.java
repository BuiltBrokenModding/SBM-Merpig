package com.builtbroken.merpig;

import com.builtbroken.merpig.entity.EntityMerpig;
import com.builtbroken.merpig.entity.RenderMerpig;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 3/28/2018.
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Merpig.DOMAIN)
public class ClientReg
{
    @SubscribeEvent
    public static void registerAllModels(ModelRegistryEvent event)
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityMerpig.class, manager -> new RenderMerpig(manager));
    }
}
