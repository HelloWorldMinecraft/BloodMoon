package uk.co.jacekk.bukkit.bloodmoon.feature.mob;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.metadata.FixedMetadataValue;

import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
public class MaxHealthListener implements Listener {

    private final String metaKey = "Bloodmoon-" + getClass().getSimpleName();

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        LivingEntity entity = event.getEntity();
        doEntity(entity);

    }

    private void doEntity(LivingEntity entity) {
        double newMaxHealth = entity.getMaxHealth() * 2;
        double damage = entity.getMaxHealth() - entity.getHealth();

        entity.setMaxHealth(newMaxHealth);
        entity.setHealth(Math.min(newMaxHealth - damage, newMaxHealth));

        entity.setMetadata(metaKey, new FixedMetadataValue(BloodMoon.instance, true));
    }
}
