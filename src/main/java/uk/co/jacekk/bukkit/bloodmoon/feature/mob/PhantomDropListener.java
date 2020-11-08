package uk.co.jacekk.bukkit.bloodmoon.feature.mob;

import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.craftbukkit.v1_16_R3.block.CraftBlockState;
import org.bukkit.craftbukkit.v1_16_R3.block.CraftCreatureSpawner;
import org.bukkit.craftbukkit.v1_16_R3.block.data.CraftBlockData;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftFallingBlock;
import org.bukkit.craftbukkit.v1_16_R3.persistence.CraftPersistentDataContainer;
import org.bukkit.craftbukkit.v1_16_R3.util.CraftMagicNumbers;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.minecart.SpawnerMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class PhantomDropListener implements Listener {
    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        if (event.getEntityType() != EntityType.PHANTOM) return;
        Location location = event.getEntity().getLocation();

        int times = (int) (Math.random() * 10);
        for (int i = 0; i < times; i++) {
            drop(location.add(Math.random() * 4 - 2, Math.random() * 4 - 2, Math.random() * 4 - 2));
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager().getType() != EntityType.PHANTOM && event.getEntityType() != EntityType.PHANTOM) return;
        Location location = event.getDamager().getLocation();

        int times = (int) Math.ceil(Math.random() * 4);
        for (int i = 0; i < times; i++) {
            drop(location.add(Math.random() * 4 - 2, Math.random() * 4 - 2, Math.random() * 4 - 2));
        }
    }

    public MobSpawnerData mobSpawner(String string, int weight) {
        NBTTagCompound entity = new NBTTagCompound();
        entity.setString("id", "minecraft:" + string);

        NBTTagCompound compound = new NBTTagCompound();
        compound.set("Entity", entity);
        compound.setInt("Weight", weight);
        return new MobSpawnerData(compound);
    }

    public void drop(Location location) {
        if (location.getBlock().getType() != Material.AIR) location = location.getWorld().getHighestBlockAt(location).getLocation().add(0,1,0);
        if (Math.random() < 0.1) {
            CraftFallingBlock block = (CraftFallingBlock) location.getWorld().spawnFallingBlock(location, Material.SPAWNER.createBlockData());

            TileEntityMobSpawner spawner = new TileEntityMobSpawner();
            spawner.getSpawner().maxNearbyEntities = 20;
            spawner.getSpawner().spawnRange = 20;
            spawner.getSpawner().requiredPlayerRange = 10;
            spawner.getSpawner().spawnCount = 10;
            spawner.getSpawner().spawnDelay = 0;

            spawner.getSpawner().mobs.add(mobSpawner("creeper", 1));
            spawner.getSpawner().mobs.add(mobSpawner("enderman", 1));
            spawner.getSpawner().mobs.add(mobSpawner("zombie", 3));
            spawner.getSpawner().mobs.add(mobSpawner("spider", 2));
            spawner.getSpawner().mobs.add(mobSpawner("skeleton", 3));
            spawner.getSpawner().spawnData = mobSpawner("skeleton", 3);

            block.getHandle().tileEntityData = spawner.save(new NBTTagCompound());
        } else {
            double random = Math.random();

            EntityType type = EntityType.ENDER_CRYSTAL;
            if (random > 0.05 && random < 0.2) type = EntityType.CREEPER;
            else if (random < 0.5) type = EntityType.SKELETON;
            else if (random >= 0.5) type = EntityType.ZOMBIE;

            location.getWorld().spawnEntity(location, type);
        }
    }
}
