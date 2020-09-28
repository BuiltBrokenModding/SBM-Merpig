package com.builtbroken.merpig;

import java.awt.Color;
import java.util.List;

import com.builtbroken.merpig.config.ConfigSpawn;
import com.builtbroken.merpig.entity.EntityMerpig;
import com.builtbroken.merpig.item.ItemSeagrassOnStick;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntitySpawnPlacementRegistry.PlacementType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.registries.ForgeRegistries;

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

    public static final EntityType<EntityMerpig> MERPIG_ENTITY_TYPE = (EntityType<EntityMerpig>)EntityType.Builder.<EntityMerpig>create(EntityMerpig::new, EntityClassification.WATER_CREATURE).size(0.8F, 0.8F).setTrackingRange(128).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).build(PREFIX + "merpig").setRegistryName(new ResourceLocation(DOMAIN, "merpig"));

    public Merpig()
    {
        INSTANCE = this;
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigSpawn.CONFIG_SPEC);
        //Fix for spawn placement
        //test with seed 5892482181512470195 frozen ocean near spawn
        EntitySpawnPlacementRegistry.register(MERPIG_ENTITY_TYPE, PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EntityMerpig::canSpawn);
    }

    @SubscribeEvent
    public static void onLoadComplete(FMLLoadCompleteEvent event)
    {
        List<? extends String> biomes = ConfigSpawn.CONFIG.biomes.get();

        for(Biome biome : ForgeRegistries.BIOMES)
        {
            if(ConfigSpawn.CONFIG.shouldSpawn.get() && biomes.size() > 0)
            {
                if(biomes.contains(biome.getRegistryName().toString()))
                {
                    biome.getSpawns(EntityClassification.WATER_CREATURE).add(
                            new Biome.SpawnListEntry(MERPIG_ENTITY_TYPE,
                                    ConfigSpawn.CONFIG.spawnWeight.get(),
                                    ConfigSpawn.CONFIG.spawnMin.get(),
                                    ConfigSpawn.CONFIG.spawnMax.get()));
                }
            }
        }
    }

    @SubscribeEvent
    public static void registerEntity(RegistryEvent.Register<EntityType<?>> event)
    {
        event.getRegistry().register(MERPIG_ENTITY_TYPE);
        GlobalEntityTypeAttributes.put(MERPIG_ENTITY_TYPE, EntityMerpig.getAttributes().create());
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().register(new ItemSeagrassOnStick());
        event.getRegistry().register(new SpawnEggItem(MERPIG_ENTITY_TYPE, Color.BLUE.getRGB(), Color.GREEN.getRGB(), new Item.Properties()
                .group(ItemGroup.MISC)).setRegistryName(PREFIX + "merpig_spawn_egg"));
    }
}
