package uk.co.jacekk.bukkit.bloodmoon.nms;

import net.minecraft.server.v1_15_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_15_R1.block.CraftEnchantingTable;
import org.bukkit.plugin.Plugin;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntityGeneric;
import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntityLiving;
import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntityType;

import java.lang.reflect.Field;
import java.util.Collection;

public class EntityZombie extends net.minecraft.server.v1_15_R1.EntityZombie {
    private BloodMoon plugin;
    private BloodMoonEntityLiving bloodMoonEntity;

    public EntityZombie(World world) {
        super(EntityTypes.ZOMBIE, world);

        Plugin gPlugin = Bukkit.getPluginManager().getPlugin("BloodMoon");

        if (!(gPlugin instanceof BloodMoon)) {
            this.killEntity();
            return;
        }

        this.plugin = (BloodMoon) gPlugin;
        this.bloodMoonEntity = new BloodMoonEntityGeneric(this.plugin, this, BloodMoonEntityType.ZOMBIE);

        try {
            clearData(goalSelector);
            clearData(targetSelector);

            this.goalSelector.a(4, new PathfinderGoalRemoveBlock(Blocks.CHEST, this, 1.0D, 3) {
                public double h() {
                    return 1.14D;
                }
            });
            this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
            this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
            this.goalSelector.a(2, new PathfinderGoalZombieAttack(this, 1.0D, false));
            this.goalSelector.a(6, new PathfinderGoalMoveThroughVillage(this, 1.0D, true, 4, this::ey));
            this.goalSelector.a(7, new PathfinderGoalRandomStrollLand(this, 1.0D));
            this.targetSelector.a(1, (new PathfinderGoalHurtByTarget(this, new Class[0])).a(new Class[]{EntityPigZombie.class}));
            this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true));
            if (this.world.spigotConfig.zombieAggressiveTowardsVillager) {
                this.targetSelector.a(3, new PathfinderGoalNearestAttackableTarget(this, EntityVillagerAbstract.class, false));
            }

            this.targetSelector.a(3, new PathfinderGoalNearestAttackableTarget(this, EntityIronGolem.class, true));
        } catch (Exception e) {
            e.printStackTrace();
            killEntity();
        }
    }

    @Override
    protected void initPathfinder() {

    }

    private void clearData(PathfinderGoalSelector selector) throws NoSuchFieldException, IllegalAccessException {
        Field field = selector.getClass().getDeclaredField("d");
        field.setAccessible(true);
        ((Collection) field.get(selector)).clear();
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
}
