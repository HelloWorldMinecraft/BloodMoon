package uk.co.jacekk.bukkit.bloodmoon.feature.mob;

import org.bukkit.entity.Creature;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDeathEvent;

import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
public class MoreExpListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEntityDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();

        if (entity instanceof Creature) {
            if (BloodMoon.getSpawnReason(entity) != SpawnReason.SPAWNER) {
                event.setDroppedExp(event.getDroppedExp() * 2);
            }
        }
    }

}
