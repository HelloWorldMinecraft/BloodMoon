package uk.co.jacekk.bukkit.bloodmoon.feature.client;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import uk.co.jacekk.bukkit.baseplugin.config.PluginConfig;
import uk.co.jacekk.bukkit.baseplugin.event.BaseListener;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.Config;
import uk.co.jacekk.bukkit.bloodmoon.Feature;
import uk.co.jacekk.bukkit.bloodmoon.event.BloodMoonEndEvent;
import uk.co.jacekk.bukkit.bloodmoon.event.BloodMoonStartEvent;

public class TexturePackListener extends BaseListener<BloodMoon> {

    public TexturePackListener(BloodMoon plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onBloodMoonStart(BloodMoonStartEvent event) {
        final World world = event.getWorld();

        if (plugin.isFeatureEnabled(world, Feature.TEXTURE_PACK)) {
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

                @Override
                public void run() {
                    sendResourcePack(world, true);
                }
            });

        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onBloodMoonEnd(BloodMoonEndEvent event) {
        final World world = event.getWorld();

        if (plugin.isFeatureEnabled(world, Feature.TEXTURE_PACK)) {
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

                @Override
                public void run() {
                    sendResourcePack(world, false);
                }
            });

        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        World from = event.getFrom();
        World to = player.getWorld();
        PluginConfig worldConfig = plugin.getConfig(to);

        if (plugin.isFeatureEnabled(to, Feature.TEXTURE_PACK)) {
            if (!plugin.isActive(from) && plugin.isActive(to)) {
                player.setResourcePack(worldConfig.getString(Config.FEATURE_TEXTURE_PACK_BLOODMOON));
            } else if (plugin.isActive(from) && !plugin.isActive(to)) {
                player.setResourcePack(worldConfig.getString(Config.FEATURE_TEXTURE_PACK_NORMAL));
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        PluginConfig worldConfig = plugin.getConfig(world);

        if (plugin.isActive(world) && plugin.isFeatureEnabled(world, Feature.TEXTURE_PACK)) {
            player.setResourcePack(worldConfig.getString(Config.FEATURE_TEXTURE_PACK_BLOODMOON));
        }
    }

    /**
     * Send the resource pack to all players in the specified world True
     *
     * @param world
     * @param special true:TEXTURE_PACK_BLOODMOON false:TEXTURE_PACK_NORMAL
     */
    public void sendResourcePack(World world, boolean special) {
        PluginConfig worldConfig = plugin.getConfig(world);
        String resource;
        if (special) {
            resource = worldConfig.getString(Config.FEATURE_TEXTURE_PACK_BLOODMOON);
        } else {
            resource = worldConfig.getString(Config.FEATURE_TEXTURE_PACK_NORMAL);
        }

        for (Player player : world.getPlayers()) {
            player.setResourcePack(resource);
        }
    }
}
