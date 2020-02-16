package uk.co.jacekk.bukkit.bloodmoon.feature.mob;

import java.util.Random;
import java.util.logging.Level;

import net.minecraft.server.v1_15_R1.EnchantmentManager;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.EntityEquipment;

import org.bukkit.inventory.ItemStack;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
public class ZombieWeaponListener implements Listener {
    private Material[] WEAPONS = new Material[] { Material.IRON_SWORD, Material.GOLDEN_SWORD, Material.DIAMOND_SWORD };


    private void giveWeapon(LivingEntity entity) {
        Material type = WEAPONS[(int) Math.floor(Math.random() * WEAPONS.length)];

        EntityEquipment equipment = entity.getEquipment();
        equipment.setItemInHand(CraftItemStack.asBukkitCopy(EnchantmentManager.a(new Random(), CraftItemStack.asNMSCopy(new ItemStack(type)), 30, true)));
        equipment.setItemInHandDropChance(25 / 100.0f);
    }


    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        World world = event.getLocation().getWorld();
            LivingEntity entity = event.getEntity();

            if ( event.getSpawnReason() != SpawnReason.SPAWNER) {
                if (entity.getType() == EntityType.ZOMBIE && Math.random() < 0.6) {
                    this.giveWeapon(entity);
                }
            }
        }
    }



