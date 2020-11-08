package uk.co.jacekk.bukkit.bloodmoon.nms;

import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.entity.*;

public class EntityEnderman extends net.minecraft.server.v1_16_R3.EntityEnderman {


    private BloodMoon plugin;
    private BloodMoonEntityLiving bloodMoonEntity;

    public EntityEnderman(World world) {
        super(EntityTypes.ENDERMAN, world);

        Plugin gPlugin = Bukkit.getPluginManager().getPlugin("BloodMoon");

        if (!(gPlugin instanceof BloodMoon)) {
            this.killEntity();
            return;
        }

        this.plugin = (BloodMoon) gPlugin;
        this.bloodMoonEntity = new BloodMoonEntityEndermen(this.plugin, this, BloodMoonEntityType.ENDERMAN);
    }

    @Override
    protected void initPathfinder() {
        super.initPathfinder();
        this.targetSelector.a(0, new PathfinderGoalNearestAttackableTarget(this, EntityPlayer.class, true));
    }

    protected void initAttributes() {
        this.getAttributeInstance(GenericAttributes.MAX_HEALTH).setValue(30.0D);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.3D);
        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(2.0D);
        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(128.0D);
    }

    @Override
    public void tick() {
        try {
            this.bloodMoonEntity.onTick();
            super.tick();
        } catch (Exception e) {
            plugin.getLogger().warning("Exception caught while ticking entity");
            e.printStackTrace();
        }
    }

    @Override
    protected void mobTick() {
        super.mobTick();

        if (getGoalTarget() != null && Math.random() < 0.0025) {
            BlockPosition.MutableBlockPosition position = new BlockPosition.MutableBlockPosition(getGoalTarget().locX() + Math.random() * 8 - 4,
                    getGoalTarget().locY() + Math.random() * 8 - 4,
                    getGoalTarget().locZ() + Math.random() * 8 - 4);

            while(position.getY() > 0 && !this.world.getType(position).getMaterial().isSolid()) {
                position.c(EnumDirection.DOWN);
            }

            if (position.getY() <= 0) return;
            enderTeleportTo(position.getX(), position.getY(), position.getZ());
        }
    }
}
