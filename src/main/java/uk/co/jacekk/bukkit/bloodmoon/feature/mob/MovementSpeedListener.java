package uk.co.jacekk.bukkit.bloodmoon.feature.mob;

import java.util.Random;

import org.bukkit.World;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntityLiving;
public class MovementSpeedListener implements Listener {

    private final Random random = new Random();

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        LivingEntity entity = event.getEntity();
        World world = entity.getWorld();


        try {
            BloodMoonEntityLiving bloodMoonEntity = BloodMoonEntityLiving.getBloodMoonEntity(((CraftLivingEntity) entity).getHandle());
            //EntityInsentient sam = (EntityInsentient)((CraftLivingEntity) entity).getHandle();
            //System.err.println(bloodMoonEntity.toString() + "le mob");
            double multiplier = random.nextDouble() * ((this.random.nextInt(100) < 15) ? 1.3 : 1.2);
            bloodMoonEntity.setSpeedMultiplier(multiplier);
        } catch (IllegalArgumentException e) {
            // This means the entity is not supported *shrug*
        }
    }

}
