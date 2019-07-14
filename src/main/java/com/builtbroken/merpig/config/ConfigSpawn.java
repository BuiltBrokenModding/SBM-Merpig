package com.builtbroken.merpig.config;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;

import net.minecraft.world.biome.Biomes;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 2/22/2018.
 */
public class ConfigSpawn
{
    public static final ForgeConfigSpec CONFIG_SPEC;
    public static final ConfigSpawn CONFIG;

    public final BooleanValue shouldSpawn;
    public final IntValue spawnWeight;
    public final IntValue spawnMin;
    public final IntValue spawnMax;
    public final ConfigValue<List<? extends String>> biomes;

    static
    {
        Pair<ConfigSpawn,ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ConfigSpawn::new);

        CONFIG_SPEC = specPair.getRight();
        CONFIG = specPair.getLeft();
    }

    public ConfigSpawn(ForgeConfigSpec.Builder builder)
    {
        builder.comment("Configuration options to handle the spawning of the Merpig")
        .push("spawning");
        shouldSpawn = builder
                .comment("Should the merpig spawn in the world")
                .define("should_spawn", true);
        spawnWeight = builder
                .comment("How likely the entity is to spawn, 10 is the same as squid. The higher the value the higher the spawn chance")
                .defineInRange("spawn_weight", 10, 0, Integer.MAX_VALUE);
        spawnMin = builder
                .comment("Smallest number to spawn in a group.")
                .defineInRange("spawn_group_min", 2, 0, Integer.MAX_VALUE);
        spawnMax = builder
                .comment("Largest number to spawn in a group.")
                .defineInRange("spawn_group_max", 5, 0, Integer.MAX_VALUE);
        biomes = builder
                .comment("Biomes to spawn the Merpig inside")
                .defineList("spawn_biomes", Lists.newArrayList(
                        Biomes.COLD_OCEAN.getRegistryName().toString(),
                        Biomes.FROZEN_OCEAN.getRegistryName().toString(),
                        Biomes.LUKEWARM_OCEAN.getRegistryName().toString(),
                        Biomes.OCEAN.getRegistryName().toString(),
                        Biomes.WARM_OCEAN.getRegistryName().toString(),
                        Biomes.DEEP_COLD_OCEAN.getRegistryName().toString(),
                        Biomes.DEEP_FROZEN_OCEAN.getRegistryName().toString(),
                        Biomes.DEEP_LUKEWARM_OCEAN.getRegistryName().toString(),
                        Biomes.DEEP_OCEAN.getRegistryName().toString(),
                        Biomes.DEEP_WARM_OCEAN.getRegistryName().toString()
                        ), e -> e instanceof String);
        builder.pop();
    }
}
