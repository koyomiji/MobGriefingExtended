package com.koyomiji.mobgriefingextended.coremod.transforms;

import com.koyomiji.asmine.common.Insns;
import com.koyomiji.asmine.query.ClassQuery;
import com.koyomiji.mobgriefingextended.coremod.IClassNodeTransformer;
import com.koyomiji.mobgriefingextended.coremod.MemberSymbol;
import com.koyomiji.mobgriefingextended.coremod.MobGriefingExtendedCorePlugin;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LabelNode;

@IClassNodeTransformer.Target("net.minecraft.world.GameRules")
public class TransformGameRules implements IClassNodeTransformer {
  private MemberSymbol getGameRuleBooleanValue = MobGriefingExtendedCorePlugin.runtimeDeobfuscationEnabled
          ? new MemberSymbol("b", "(Ljava/lang/String;)Z")
          : new MemberSymbol("getGameRuleBooleanValue", "(Ljava/lang/String;)Z");
  
  @Override
  public ClassNode transform(ClassNode classNode) {
    LabelNode l0 = new LabelNode();
    
    return ClassQuery.of(classNode)
            .selectMethod(getGameRuleBooleanValue.name, getGameRuleBooleanValue.desc)
            .addInsnsFirst(
                    Insns.aload(1),
                    Insns.invokestatic("com/koyomiji/mobgriefingextended/MobGriefingExtended", "shouldPrevent", "(Ljava/lang/String;)Z", false),
                    Insns.ifeq(l0),
                    Insns.iconst_0(),
                    Insns.ireturn(),
                    Insns.frame(Opcodes.F_NEW, 0, new Object[] {}, 0, new Object[0]),
                    l0
            )
            .done()
            .done();
  }
}
