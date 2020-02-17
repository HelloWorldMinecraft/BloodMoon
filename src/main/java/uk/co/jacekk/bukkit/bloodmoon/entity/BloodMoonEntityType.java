package uk.co.jacekk.bukkit.bloodmoon.entity;

import net.minecraft.server.v1_15_R1.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Map;

public enum BloodMoonEntityType {

    CREEPER(EntityTypes.CREEPER, uk.co.jacekk.bukkit.bloodmoon.nms.EntityCreeper.class),
    ENDERMAN(EntityTypes.ENDERMAN, uk.co.jacekk.bukkit.bloodmoon.nms.EntityEnderman.class),
    SKELETON(EntityTypes.SKELETON, uk.co.jacekk.bukkit.bloodmoon.nms.EntitySkeleton.class),
    SPIDER(EntityTypes.SPIDER, uk.co.jacekk.bukkit.bloodmoon.nms.EntitySpider.class),
    ZOMBIE(EntityTypes.ZOMBIE, uk.co.jacekk.bukkit.bloodmoon.nms.EntityZombie.class),
    GHAST(EntityTypes.GHAST, uk.co.jacekk.bukkit.bloodmoon.nms.EntityGhast.class),
    BLAZE(EntityTypes.BLAZE, uk.co.jacekk.bukkit.bloodmoon.nms.EntityBlaze.class),
    WITCH(EntityTypes.WITCH, uk.co.jacekk.bukkit.bloodmoon.nms.EntityWitch.class);

    private final EntityTypes entityType;
    private final Class<? extends EntityInsentient> bloodMoonClass;

    private static boolean registered = false;

    private BloodMoonEntityType(EntityTypes entityType, Class<? extends EntityInsentient> bloodMoonClass) {
        this.entityType = entityType;
        this.bloodMoonClass = bloodMoonClass;
    }

    public static void registerEntities() {
        if (registered) {
            throw new RuntimeException("Already registered.");
        }

        for (BloodMoonEntityType type : values()) {
            try {
                //Replace mob spawn code
                EntityTypes<?> types = (EntityTypes<?>) EntityTypes.class.getField(type.name()).get(null);
                Field field = types.getClass().getDeclaredField("ba");
                field.setAccessible(true);

                if (Modifier.isFinal(field.getModifiers())) {
                    Field mutable = field.getClass().getDeclaredField("modifiers");
                    mutable.setAccessible(true);
                    mutable.setInt(field, field.getModifiers() & ~Modifier.FINAL);
                }

                field.set(types, (EntityTypes.b<?>) (paramTypes, world) -> {
                    try {
                        return (Entity) type.bloodMoonClass.getConstructors()[0].newInstance(world);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        registered = true;
    }

    public Class<? extends EntityInsentient> getBloodMoonClass() {
        return this.bloodMoonClass;
    }

    private EntityInsentient createEntity(World world) {
        try {
            return this.getBloodMoonClass().getConstructor(World.class).newInstance(world);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void spawnEntity(Location location) {
        World world = ((CraftWorld) location.getWorld()).getHandle();

        EntityInsentient entity = this.createEntity(world);
        entity.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        world.addEntity(entity, SpawnReason.CUSTOM);
    }
}
