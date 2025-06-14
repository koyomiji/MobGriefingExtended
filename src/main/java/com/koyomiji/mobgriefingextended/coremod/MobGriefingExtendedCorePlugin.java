package com.koyomiji.mobgriefingextended.coremod;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

@IFMLLoadingPlugin.TransformerExclusions({"com.koyomiji.mobgriefingextended", "com.koyomiji.asmine"})
public class MobGriefingExtendedCorePlugin implements IFMLLoadingPlugin {
    public static File coremodLocation;
    public static File mcLocation;
    public static ArrayList<String> coremodList;
    public static Boolean runtimeDeobfuscationEnabled;
    public static Logger logger = LogManager.getLogger("MobGriefingExtended");

    @Override
    public String[] getASMTransformerClass() {
        return new String[]{"com.koyomiji.mobgriefingextended.coremod.MobGriefingExtendedClassTransformer"};
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        if (data.containsKey("coremodLocation")) {
            coremodLocation = (File) data.get("coremodLocation");
        }

        if (data.containsKey("mcLocation")) {
            mcLocation = (File) data.get("mcLocation");
        }

        if (data.containsKey("coremodList")) {
            coremodList = (ArrayList<String>) data.get("coremodList");
        }

        if (data.containsKey("runtimeDeobfuscationEnabled")) {
            runtimeDeobfuscationEnabled = (Boolean) data.get("runtimeDeobfuscationEnabled");
        }
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
