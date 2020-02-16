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

public class EquipmentListener implements Listener {

    private final String[] ARMOR = new String[] {"DIAMOND", "GOLDEN", "IRON"};
    private Material[] WEAPONS = new Material[] { Material.IRON_SWORD, Material.GOLDEN_SWORD, Material.DIAMOND_SWORD };

    private void giveArmor(LivingEntity entity) {
        EntityEquipment equipment = entity.getEquipment();

        if (Math.random() < 0.9) equipment.setBoots(enchant(new ItemStack(Material.getMaterial(getRandom(ARMOR) + "_BOOTS")), 30));
        if (Math.random() < 0.9) equipment.setLeggings(enchant(new ItemStack(Material.getMaterial(getRandom(ARMOR) + "_LEGGINGS")), 30));
        if (Math.random() < 0.9) equipment.setChestplate(enchant(new ItemStack(Material.getMaterial(getRandom(ARMOR) + "_CHESTPLATE")), 30));
        if (Math.random() < 0.9) equipment.setHelmet(enchant(new ItemStack(Material.getMaterial(getRandom(ARMOR) + "_HELMET")), 30));

        float dropChance = 7 / 100.0f;

        equipment.setBootsDropChance(dropChance);
        equipment.setLeggingsDropChance(dropChance);
        equipment.setChestplateDropChance(dropChance);
        equipment.setHelmetDropChance(dropChance);

        if (Math.random() < 0.8) equipment.setItemInMainHand(enchant(new ItemStack(entity.getType() == EntityType.SKELETON ? Material.BOW : getRandom(WEAPONS)), 30));
    }

    public <T> T getRandom(T... t) {
        return t[(int) Math.floor(t.length * Math.random())];
    }
    public ItemStack enchant(ItemStack stack, int level) {
        return CraftItemStack.asBukkitCopy(EnchantmentManager.a(new Random(), CraftItemStack.asNMSCopy(stack), level, true));
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onCreatureSpawn(CreatureSpawnEvent event) {

            LivingEntity entity = event.getEntity();

            if (event.getSpawnReason() != SpawnReason.SPAWNER) {
                if (entity.getType() == EntityType.ZOMBIE || entity.getType() == EntityType.SKELETON) {
                    this.giveArmor(entity);
                }
            }

    }
}
