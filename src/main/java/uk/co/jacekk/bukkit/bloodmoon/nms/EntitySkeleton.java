package uk.co.jacekk.bukkit.bloodmoon.nms;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import net.minecraft.server.v1_15_R1.*;
import net.minecraft.server.v1_15_R1.EntityCreeper;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntityGeneric;
import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntityLiving;
import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntityType;

//public class EntitySkeleton {
public class EntitySkeleton extends net.minecraft.server.v1_15_R1.EntitySkeleton implements IRangedEntity {

    private BloodMoon plugin;
    private BloodMoonEntityLiving bloodMoonEntity;

    public EntitySkeleton(net.minecraft.server.v1_15_R1.World world) {
        super(EntityTypes.SKELETON, world);

        Plugin gPlugin = Bukkit.getPluginManager().getPlugin("BloodMoon");

        if (!(gPlugin instanceof BloodMoon)) {
            this.killEntity();
            return;
        }

        this.plugin = (BloodMoon) gPlugin;
        this.bloodMoonEntity = new BloodMoonEntityGeneric(this.plugin, this, BloodMoonEntityType.SKELETON);

        try {
            clearData(goalSelector);
            clearData(targetSelector);

            this.goalSelector.a(1, new PathfinderGoalFloat(this));
            this.goalSelector.a(2, new PathfinderGoalRestrictSun(this));
            this.goalSelector.a(3, new PathfinderGoalFleeSun(this, 1.0d));
            // NOTE: See bJ() below
            this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, 1.0d));
            this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
            this.goalSelector.a(6, new PathfinderGoalRandomLookaround(this));

            //this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, false));
            this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, false, true));
        } catch (Exception e) {
            e.printStackTrace();
            killEntity();
        }
    }

    private void clearData(PathfinderGoalSelector selector) throws NoSuchFieldException, IllegalAccessException {
        Field field = selector.getClass().getDeclaredField("d");
        field.setAccessible(true);
        ((Collection) field.get(selector)).clear();
    }
}
