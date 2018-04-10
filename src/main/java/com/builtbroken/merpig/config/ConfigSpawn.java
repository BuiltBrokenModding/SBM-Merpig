package com.builtbroken.merpig.config;

import com.builtbroken.merpig.Merpig;
import net.minecraftforge.common.config.Config;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 2/22/2018.
 */
@Config(modid = Merpig.DOMAIN, name = "merpig/spawning")
@Config.LangKey("config.merpig:spawning.title")
public class ConfigSpawn
{
    @Config.Name("should_spawn")
    @Config.Comment("Should the merpig spawn in the world")
    @Config.RequiresMcRestart
    public static boolean SHOULD_SPAWN = true;

    @Config.Name("spawn_weight")
    @Config.Comment("How likely the entity is to spawn, 10 is the same as quid. Higher the value the higher the spawn chance")
    @Config.RequiresMcRestart
    public static int SPAWN_WEIGHT = 10;

    @Config.Name("spawn_group_min")
    @Config.Comment("Smallest number to spawn in a group.")
    @Config.RequiresMcRestart
    public static int SPAWN_MIN = 2;

    @Config.Name("spawn_group_max")
    @Config.Comment("Largest number to spawn in a group.")
    @Config.RequiresMcRestart
    public static int SPAWN_MAX = 5;

    @Config.Name("spawn_biomes")
    @Config.Comment("Biomes to spawn entities inside")
    @Config.RequiresMcRestart
    public static String[] BIOMES = new String[]{"ocean", "deep_ocean"};
}
