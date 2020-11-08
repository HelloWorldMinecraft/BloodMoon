package uk.co.jacekk.bukkit.bloodmoon.entity;

import net.minecraft.server.v1_16_R3.EntityInsentient;
import net.minecraft.server.v1_16_R3.EntityMonster;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;

public class BloodMoonEntityGeneric extends BloodMoonEntityMonster {

    public BloodMoonEntityGeneric(BloodMoon plugin, EntityInsentient nmsEntity, BloodMoonEntityType type) {
        super(plugin, nmsEntity, type);
    }
}
