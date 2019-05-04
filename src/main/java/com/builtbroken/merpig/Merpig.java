package com.builtbroken.merpig;

import com.builtbroken.merpig.config.ConfigSpawn;
import com.builtbroken.merpig.entity.EntityMerpig;
import com.builtbroken.merpig.item.ItemSeagrassOnStick;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 3/28/2018.
 */
@Mod(modid = Merpig.DOMAIN, name = "[SBM] Merpig", version = Merpig.VERSION)
@Mod.EventBusSubscriber(modid = Merpig.DOMAIN)
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

    protected static Logger logger = LogManager.getLogger(DOMAIN);

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

        //Enable spawns if config is enabled
        if (ConfigSpawn.SHOULD_SPAWN && ConfigSpawn.BIOMES.length > 0)
        {
            //Build supported biome list
            List<Biome> biomesList = new ArrayList();
            for (String id : ConfigSpawn.BIOMES)
            {
                Biome biome = Biome.REGISTRY.getObject(new ResourceLocation(id));
                if (biome != null)
                {
                    biomesList.add(biome);
                }
                else
                {
                    logger.error("Merpig#registerEntity() -> Failed to find a biome with id [" + id + "] while loading config data for entity registry");
                }
            }

            //Convert to array
            Biome[] biomes = new Biome[biomesList.size()];
            for (int i = 0; i < biomesList.size(); i++)
            {
                biomes[i] = biomesList.get(i);
            }

            //Add spawn data
            builder.spawn(EnumCreatureType.WATER_CREATURE, ConfigSpawn.SPAWN_WEIGHT, ConfigSpawn.SPAWN_MIN, ConfigSpawn.SPAWN_MAX, biomes);
        }

        //Register entity
        event.getRegistry().register(builder.build());

        //Fix for spawn placement
        EntitySpawnPlacementRegistry.setPlacementType(EntityMerpig.class, EntityLiving.SpawnPlacementType.IN_WATER);
    }
}
