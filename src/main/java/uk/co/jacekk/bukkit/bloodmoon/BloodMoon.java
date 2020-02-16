package uk.co.jacekk.bukkit.bloodmoon;

import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.entity.Entity;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.metadata.MetadataValue;

import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntityType;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public final class BloodMoon extends JavaPlugin {
    public static BloodMoon instance;
    public static boolean DEBUG = false;

    @Override
    public void onEnable() {
        instance = this;

        try {
            BloodMoonEntityType.registerEntities();
        } catch (Exception e) {
            e.printStackTrace();
        }

        getServer().getPluginManager().registerEvents(new SpawnReasonListener(this), this);
        for (Feature feature : Feature.values()) {
            try {
                Class<? extends Listener> listener = feature.getListenerClass();

                if (listener != null) {
                    getServer().getPluginManager().registerEvents(listener.newInstance(), this);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Gets the reason that an entity spawned. Note that these reasons are reset
     * when the server restarts.
     *
     * @param entity The entity
     * @return The {@link SpawnReason}
     */
    public static SpawnReason getSpawnReason(Entity entity) {
        for (MetadataValue value : entity.getMetadata("spawn-reason")) {
            if (value.getOwningPlugin() instanceof BloodMoon) {
                return (SpawnReason) value.value();
            }
        }

        return SpawnReason.DEFAULT;
    }

}
