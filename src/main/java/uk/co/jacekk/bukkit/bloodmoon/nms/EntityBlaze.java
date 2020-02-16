package uk.co.jacekk.bukkit.bloodmoon.nms;

import net.minecraft.server.v1_15_R1.EntityTypes;
import net.minecraft.server.v1_15_R1.World;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntityGeneric;
import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntityType;

public class EntityBlaze extends net.minecraft.server.v1_15_R1.EntityBlaze {

    private BloodMoon plugin;
    private BloodMoonEntityGeneric bloodMoonEntity;

    public EntityBlaze(World world) {
        super(EntityTypes.BLAZE, world);

        Plugin gPlugin = Bukkit.getPluginManager().getPlugin("BloodMoon");

        if (!(gPlugin instanceof BloodMoon)) {
            this.killEntity();
            return;
        }

        this.plugin = (BloodMoon) gPlugin;
        this.bloodMoonEntity = new BloodMoonEntityGeneric(this.plugin, this, BloodMoonEntityType.BLAZE);
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
