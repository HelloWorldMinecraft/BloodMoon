package uk.co.jacekk.bukkit.bloodmoon.feature.mob;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;

import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;

public class DaylightProofMobsListener implements Listener {
    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEntityCombust(EntityCombustEvent event) {
        EntityType type = event.getEntityType();

        if (type == EntityType.ZOMBIE || type == EntityType.SKELETON) {
            event.setCancelled(true);
        }
    }
}


//Class score 10/10
