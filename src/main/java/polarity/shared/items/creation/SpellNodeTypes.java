package polarity.shared.items.creation;

import polarity.shared.spellforge.nodes.SpellNodeData;
import polarity.shared.spellforge.nodes.cores.*;
import polarity.shared.spellforge.nodes.effect.*;
import polarity.shared.spellforge.nodes.generators.*;
import polarity.shared.spellforge.nodes.modifiers.*;

/**
 *
 * @author SinisteRing
 */
public enum SpellNodeTypes {
    // Generators
    EnergyGenerator(EnergyGenData.class, 2),
    ManaGenerator(ManaGenData.class, 2),
    // Cores
    ProjectileCore(ProjectileCoreData.class, 2),
    // Effects
    DamageEffect(DamageEffectData.class, 3),
    PoisonEffect(PoisonEffectData.class, 3),
    SlowEffect(SlowEffectData.class, 3),
    VampEffect(VampEffectData.class, 3),
    // Modifier
    MultiplierModifier(MultiModData.class, 3),
    RadiusModifier(RadiusModData.class, 3),
    SpeedModifier(SpeedModData.class, 3);

    protected Class<? extends SpellNodeData> clazz;
    protected int dropWeight;

    public Class getTypeClass(){
        return clazz;
    }
    public int getDropWeight(){
        return dropWeight;
    }

    SpellNodeTypes(Class<? extends SpellNodeData> clazz, int dropWeight){
        this.clazz = clazz;
        this.dropWeight = dropWeight;
    }
}
