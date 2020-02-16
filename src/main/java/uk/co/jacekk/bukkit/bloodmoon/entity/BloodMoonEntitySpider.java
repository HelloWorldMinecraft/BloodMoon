package uk.co.jacekk.bukkit.bloodmoon.entity;

import net.minecraft.server.v1_15_R1.EntityHuman;
import net.minecraft.server.v1_15_R1.EntityMonster;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
public class BloodMoonEntitySpider extends BloodMoonEntityMonster {

    public BloodMoonEntitySpider(BloodMoon plugin, EntityMonster nmsEntity, BloodMoonEntityType type) {
        super(plugin, nmsEntity, type);
    }

    @Override
    public void onTick() {
        if (nmsEntity.getGoalTarget() instanceof EntityHuman && nmsEntity.world.getTime() % 20 == 0 && nmsEntity.world.worldData.getName().equals(nmsEntity.getGoalTarget().world.worldData.getName())) {
            this.attemptBreakBlock(this.getBreakableTargetBlock());
        }
    }

}
