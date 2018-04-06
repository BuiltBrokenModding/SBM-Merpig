package com.builtbroken.merpig;

import com.builtbroken.merpig.entity.EntityMerpig;
import com.builtbroken.merpig.item.ItemSeagrassOnStick;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
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

    @Mod.Instance(DOMAIN)
    public static Merpig INSTANCE;

    public static Item itemStick;

    public static final int ENTITY_ID_PREFIX = 50;
    private static int nextEntityID = ENTITY_ID_PREFIX;

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().register(itemStick = new ItemSeagrassOnStick());
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
        builder.spawn(EnumCreatureType.WATER_CREATURE, 10, 2, 5, Biomes.OCEAN, Biomes.DEEP_OCEAN);
        event.getRegistry().register(builder.build());

        EntitySpawnPlacementRegistry.setPlacementType(EntityMerpig.class, EntityLiving.SpawnPlacementType.IN_WATER);
    }
}
