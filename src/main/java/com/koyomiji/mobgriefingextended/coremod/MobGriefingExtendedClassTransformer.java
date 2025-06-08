package com.koyomiji.mobgriefingextended.coremod;

import net.minecraft.launchwrapper.IClassTransformer;

public class MobGriefingExtendedClassTransformer implements IClassTransformer {
    private ClassTransformLoader applier;

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (applier == null) {
            applier = new ClassTransformLoader(MobGriefingExtendedCorePlugin.coremodLocation, "com.koyomiji.mobgriefingextended.coremod.transforms");
        }

        return applier.transform(name, transformedName, basicClass);
    }
}
