package uk.co.jacekk.bukkit.bloodmoon.nms;

import net.minecraft.server.v1_15_R1.EntityTypes;
import net.minecraft.server.v1_15_R1.World;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntitySpider;
import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntityType;

public class EntitySpider extends net.minecraft.server.v1_15_R1.EntitySpider {

    private BloodMoon plugin;
    private BloodMoonEntitySpider bloodMoonEntity;

    public EntitySpider(World world) {
        super(EntityTypes.SPIDER, world);

        Plugin gPlugin = Bukkit.getPluginManager().getPlugin("BloodMoon");

        if (!(gPlugin instanceof BloodMoon)) {

this.killEntity();            return;
        }

        this.plugin = (BloodMoon) gPlugin;
        this.bloodMoonEntity = new BloodMoonEntitySpider(this.plugin, this, BloodMoonEntityType.SPIDER);
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
