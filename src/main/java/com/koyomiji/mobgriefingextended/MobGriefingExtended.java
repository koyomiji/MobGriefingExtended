package com.koyomiji.mobgriefingextended;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Mod(modid = MobGriefingExtended.MODID, version = MobGriefingExtended.VERSION)
public class MobGriefingExtended {
    public static final String MODID = "mobgriefingextended";
    public static final String VERSION = "0.1.0";

    @Mod.Instance
    private static MobGriefingExtended instance;

    private static Map<String, String> classNameToKey = new HashMap<>();
    private static Map<String, Boolean> values = new HashMap<>();

    public static MobGriefingExtended getInstance() {
        return instance;
    }

    @Mod.EventHandler
    public void preInitialization(FMLPreInitializationEvent event) {
        File configFile = new File(event.getModConfigurationDirectory(), "mobGriefingExtended.cfg");
        Configuration config = new Configuration(configFile);
        config.load();

        for (String key : classNameToKey.values()) {
            boolean defaultValue = values.get(key);
            boolean value = config.getBoolean(key, "general", defaultValue, "Allow " + key);
            values.put(key, value);
        }

        config.save();
        MinecraftForge.EVENT_BUS.register(this);
    }

    static {
        classNameToKey.put("net.minecraft.block.BlockFarmland", "fallingOnFarmland");
        classNameToKey.put("net.minecraft.entity.EntityLiving", "pickUpItem");
        classNameToKey.put("net.minecraft.entity.ai.EntityAIBreakDoor", "breakDoor");
        classNameToKey.put("net.minecraft.entity.ai.EntityAIEatGrass", "eatGrass");
        classNameToKey.put("net.minecraft.entity.boss.EntityDragon", "enderDragonDestroyBlock");
        classNameToKey.put("net.minecraft.entity.boss.EntityWither", "witherDestroyBlock");
        classNameToKey.put("net.minecraft.entity.monster.EntityCreeper", "creeperDestroyBlock");
        classNameToKey.put("net.minecraft.entity.monster.EntityEnderman", "endermanPickUpBlock");
        classNameToKey.put("net.minecraft.entity.monster.EntitySilverfish", "silverfishDestroyBlock");
        classNameToKey.put("net.minecraft.entity.projectile.EntityLargeFireball", "largeFireballDestroyBlock");
        classNameToKey.put("net.minecraft.entity.projectile.EntityWitherSkull", "witherSkullDestroyBlock");
        values.put("fallingOnFarmland", true);
        values.put("pickUpItem", true);
        values.put("breakDoor", false);
        values.put("eatGrass", true);
        values.put("enderDragonDestroyBlock", false);
        values.put("witherDestroyBlock", false);
        values.put("creeperDestroyBlock", false);
        values.put("endermanPickUpBlock", false);
        values.put("silverfishDestroyBlock", false);
        values.put("largeFireballDestroyBlock", false);
        values.put("witherSkullDestroyBlock", false);
    }

    public static boolean shouldPrevent(String key) {
        if (!Objects.equals(key, "mobGriefing")) {
            return false;
        }

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        if (stackTrace.length < 4) {
            return false;
        }

        StackTraceElement element = stackTrace[3];
        String className = element.getClassName();
        String overrideKey = classNameToKey.get(className);

        if (overrideKey != null) {
            Boolean allow = values.get(overrideKey);

            if (allow != null) {
                return !allow;
            }
        }

        return false;
    }
}
