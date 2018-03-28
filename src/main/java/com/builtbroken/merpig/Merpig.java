package com.builtbroken.merpig;

import com.builtbroken.merpig.entity.EntityMerpig;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

import java.awt.*;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 3/28/2018.
 */
@Mod(modid = Merpig.DOMAIN, name = "[SBM] Merpig", version = Merpig.VERSION)
@Mod.EventBusSubscriber
public class Merpig
{
    public static final String DOMAIN = "merpig";
    public static final String PREFIX = DOMAIN + ":";

    public static final String MAJOR_VERSION = "@MAJOR@";
    public static final String MINOR_VERSION = "@MINOR@";
    public static final String REVISION_VERSION = "@REVIS@";
    public static final String BUILD_VERSION = "@BUILD@";
    public static final String MC_VERSION = "@MC@";
    public static final String VERSION = MC_VERSION + "-" + MAJOR_VERSION + "." + MINOR_VERSION + "." + REVISION_VERSION + "." + BUILD_VERSION;

    public static final int ENTITY_ID_PREFIX = 50;

    @Mod.Instance(DOMAIN)
    public static Merpig INSTANCE;
    private static int nextEntityID = ENTITY_ID_PREFIX;

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {

    }

    @SubscribeEvent
    public static void registerEntity(RegistryEvent.Register<EntityEntry> event)
    {
        EntityEntryBuilder builder = EntityEntryBuilder.create();
        builder.name(PREFIX + "merpig");
        builder.id(new ResourceLocation(DOMAIN, "merpig"), nextEntityID++);
        builder.tracker(128, 1, true);
        builder.entity(EntityMerpig.class);
        builder.egg(Color.BLUE.getRGB(), Color.GREEN.getRGB());
        event.getRegistry().register(builder.build());
    }
}
