package uk.co.jacekk.bukkit.bloodmoon.feature.spawning;

import java.util.Random;

import org.bukkit.craftbukkit.v1_15_R1.util.CraftMagicNumbers;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntityType;

public final class MoreSpawningListener implements Listener {

    private final Random random = new Random();

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason() != SpawnReason.NATURAL) { //TODO: Should check for Custom instead of checking against natural?
            return;
        }

        EntityType type = event.getEntityType();
        for (int i = 0; i < 5; ++i) {
            for (BloodMoonEntityType bloodMoonEntity : BloodMoonEntityType.values()) {
                if (type == EntityType.valueOf(bloodMoonEntity.name())) {
                    bloodMoonEntity.spawnEntity(event.getLocation().add((random.nextDouble() * 3) - 1.5, (random.nextDouble() * 3) - 1.5, (random.nextDouble() * 3) - 1.5));
                    return;
                }
            }
        }

    }

}
