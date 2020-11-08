package uk.co.jacekk.bukkit.bloodmoon.feature.mob;

import org.bukkit.craftbukkit.v1_16_R3.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntityLiving;
public class TargetDistanceListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        LivingEntity entity = event.getEntity();

        try {
            //((CraftLivingEntity)entity).setfol
            BloodMoonEntityLiving bloodMoonEntity = BloodMoonEntityLiving.getBloodMoonEntity(((CraftLivingEntity) entity).getHandle());
            bloodMoonEntity.setFollowRangeMultiplier(2);
        } catch (IllegalArgumentException e) {
            // This means the entity is not supported *shrug*
        }
    }

}
