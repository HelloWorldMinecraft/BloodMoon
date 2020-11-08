package uk.co.jacekk.bukkit.bloodmoon.nms;

import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.GenericAttributes;
import net.minecraft.server.v1_16_R3.World;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntityGhast;
import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntityLiving;
import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntityType;

public class EntityGhast extends net.minecraft.server.v1_16_R3.EntityGhast {

    private BloodMoon plugin;
    private BloodMoonEntityLiving bloodMoonEntity;

    public EntityGhast(World world) {
        super(EntityTypes.GHAST, world);

        Plugin gPlugin = Bukkit.getPluginManager().getPlugin("BloodMoon");

        if (!(gPlugin instanceof BloodMoon)) {
            this.killEntity();
            return;
        }

        this.plugin = (BloodMoon) gPlugin;
        this.bloodMoonEntity = new BloodMoonEntityGhast(this.plugin, this, BloodMoonEntityType.GHAST);
    }

    protected void initAttributes() {
        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(200.0D);
    }

    @Override
    protected void initPathfinder() {
        super.initPathfinder();
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
