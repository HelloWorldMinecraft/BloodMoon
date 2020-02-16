package uk.co.jacekk.bukkit.bloodmoon.feature.mob;

import org.bukkit.entity.Creature;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.ItemStack;

import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
public class MoreDropsListener implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();

        if (entity instanceof Creature) {
            if (BloodMoon.getSpawnReason(entity) != SpawnReason.SPAWNER) {
                for (ItemStack drop : event.getDrops()) {
                    drop.setAmount(drop.getAmount() * 2);
                }
            }
        }
    }
}
