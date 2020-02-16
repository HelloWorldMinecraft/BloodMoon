package uk.co.jacekk.bukkit.bloodmoon.feature.player;

import java.util.Random;
import org.bukkit.World;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

public class SwordDamageListener implements Listener {

    private final Random random = new Random();

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (event.getCause() == DamageCause.ENTITY_ATTACK) {
            if (entity instanceof Creature) {
                Creature creature = (Creature) entity;
                LivingEntity target = creature.getTarget();

                if (target instanceof Player) {
                    Player player = (Player) target;
                    ItemStack item = player.getItemInHand();
                    String itemName = item.getType().name().toUpperCase();

                    if (entity instanceof Monster && itemName.endsWith("_SWORD") && this.random.nextInt(100) <= 10) {
                        short damage = item.getDurability();
                        short remove = (short) (item.getType().getMaxDurability() / 50);

                        item.setDurability((short) ((damage > remove) ? damage - remove : 1));
                    }
                }
            }
        }
    }

}
