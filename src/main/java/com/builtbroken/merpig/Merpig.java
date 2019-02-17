package com.builtbroken.merpig;

import com.builtbroken.merpig.config.ConfigSpawn;
import com.builtbroken.merpig.entity.EntityMerpig;
import com.builtbroken.merpig.item.ItemSeagrassOnStick;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntitySpawnPlacementRegistry.SpawnPlacementType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemSpawnEgg;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.awt.*;
import java.util.List;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 3/28/2018.
 */
@Mod(Merpig.DOMAIN)
@EventBusSubscriber(bus=Bus.MOD)
public class Merpig
{
    public static final String DOMAIN = "merpig";
    public static final String PREFIX = DOMAIN + ":";

    public static Merpig INSTANCE;

    public static final EntityType<EntityMerpig> MERPIG_ENTITY_TYPE = EntityType.register(PREFIX + "merpig", EntityType.Builder.create(EntityMerpig.class, EntityMerpig::new).tracker(128, 1, true));

    public Merpig()
    {
        INSTANCE = this;
        //ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigSpawn.CONFIG_SPEC);
        //Fix for spawn placement
        EntitySpawnPlacementRegistry.register(MERPIG_ENTITY_TYPE, SpawnPlacementType.IN_WATER, Type.MOTION_BLOCKING_NO_LEAVES, null);
        //FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onLoadComplete);
    }

    public void onLoadComplete(FMLLoadCompleteEvent event)
    {
        List<? extends String> biomes = ConfigSpawn.CONFIG.biomes.get();

        if(ConfigSpawn.CONFIG.shouldSpawn.get() && biomes.size() > 0)
        {
            for(Biome biome : ForgeRegistries.BIOMES)
            {
                if(biomes.contains(biome.getRegistryName().toString()))
                {
                    biome.addSpawn(EnumCreatureType.WATER_CREATURE,
                            new Biome.SpawnListEntry(MERPIG_ENTITY_TYPE,
                                    ConfigSpawn.CONFIG.spawnWeight.get(),
                                    ConfigSpawn.CONFIG.spawnMin.get(),
                                    ConfigSpawn.CONFIG.spawnMax.get()));
                }
            }
        }
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().register(new ItemSeagrassOnStick());
        event.getRegistry().register(new ItemSpawnEgg(MERPIG_ENTITY_TYPE, Color.BLUE.getRGB(), Color.GREEN.getRGB(), new Item.Properties()
                .group(ItemGroup.MISC)).setRegistryName(PREFIX + "merpig_spawn_egg"));
    }
}
