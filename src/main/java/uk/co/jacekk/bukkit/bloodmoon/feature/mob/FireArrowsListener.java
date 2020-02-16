package uk.co.jacekk.bukkit.bloodmoon.feature.mob;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftEntity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import uk.co.jacekk.bukkit.bloodmoon.nms.EntitySkeleton;

public class FireArrowsListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onProjectileHit(ProjectileHitEvent event) {
        Projectile projectile = event.getEntity();

        LivingEntity shooter = (LivingEntity) projectile.getShooter();
        if (shooter.getType() != EntityType.SKELETON) { //Not sure why this is here, or why fire arrows even work with it here
            return;
        }

        if (((CraftEntity) shooter).getHandle() instanceof EntitySkeleton && projectile.getFireTicks() > 0) {
            //if (shooter != null && ((CraftEntity)shooter).getHandle() instanceof EntitySkeleton && projectile.getFireTicks() > 0){
            Block block = projectile.getWorld().getBlockAt(projectile.getLocation());
            if (block.getType() == Material.AIR) {
                block.setType(Material.FIRE);
            }
        }

    }

}
