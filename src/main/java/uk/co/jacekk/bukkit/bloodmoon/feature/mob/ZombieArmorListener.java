package uk.co.jacekk.bukkit.bloodmoon.feature.mob;

import java.util.Random;
import java.util.logging.Level;

import net.minecraft.server.v1_15_R1.EnchantmentManager;
import org.bukkit.Material;
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
public class ZombieArmorListener implements Listener {

    private final String ARMOR[] = new String[] {"DIAMOND", "GOLDEN", "IRON"};

    private void giveArmor(LivingEntity entity) {
        String name = ARMOR[(int) Math.floor(Math.random() * ARMOR.length)];


        EntityEquipment equipment = entity.getEquipment();

        equipment.setBoots(CraftItemStack.asBukkitCopy(EnchantmentManager.a(new Random(), CraftItemStack.asNMSCopy(new ItemStack(Material.getMaterial(name + "_BOOTS"))), 30, true)));
        equipment.setLeggings(CraftItemStack.asBukkitCopy(EnchantmentManager.a(new Random(), CraftItemStack.asNMSCopy(new ItemStack(Material.getMaterial(name + "_LEGGINGS"))), 30, true)));
        equipment.setChestplate(CraftItemStack.asBukkitCopy(EnchantmentManager.a(new Random(), CraftItemStack.asNMSCopy(new ItemStack(Material.getMaterial(name + "_CHESTPLATE"))), 30, true)));
        equipment.setHelmet(CraftItemStack.asBukkitCopy(EnchantmentManager.a(new Random(), CraftItemStack.asNMSCopy(new ItemStack(Material.getMaterial(name + "_HELMET"))), 30, true)));

        float dropChance = 7 / 100.0f;

        equipment.setBootsDropChance(dropChance);
        equipment.setLeggingsDropChance(dropChance);
        equipment.setChestplateDropChance(dropChance);
        equipment.setHelmetDropChance(dropChance);
    }


    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onCreatureSpawn(CreatureSpawnEvent event) {

            LivingEntity entity = event.getEntity();

            if (event.getSpawnReason() != SpawnReason.SPAWNER) {
                if (entity.getType() == EntityType.ZOMBIE && Math.random() < 0.6) {
                    this.giveArmor(entity);
                }
            }

    }
}
