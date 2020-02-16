package uk.co.jacekk.bukkit.bloodmoon.entity;

import net.minecraft.server.v1_15_R1.EntityMonster;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;

public class BloodMoonEntityGeneric extends BloodMoonEntityMonster {

    public BloodMoonEntityGeneric(BloodMoon plugin, EntityMonster nmsEntity, BloodMoonEntityType type) {
        super(plugin, nmsEntity, type);
    }
}
