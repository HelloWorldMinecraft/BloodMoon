package uk.co.jacekk.bukkit.bloodmoon.nms;

import net.minecraft.server.v1_15_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntityGeneric;
import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntityLiving;
import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntityType;

import java.lang.reflect.Field;
import java.util.Collection;

public class EntityPhantom extends net.minecraft.server.v1_15_R1.EntityPhantom {
    private BloodMoon plugin;
    private BloodMoonEntityLiving bloodMoonEntity;

    public EntityPhantom(World world) {
        super(EntityTypes.PHANTOM, world);

        Plugin gPlugin = Bukkit.getPluginManager().getPlugin("BloodMoon");

        if (!(gPlugin instanceof BloodMoon)) {
            this.killEntity();
            return;
        }

        this.plugin = (BloodMoon) gPlugin;
        this.bloodMoonEntity = new BloodMoonEntityGeneric(this.plugin, this, BloodMoonEntityType.PHANTOM);
    }

    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(100D);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.3D);
        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(12.0D);
        this.getAttributeInstance(GenericAttributes.ARMOR).setValue(8.0D);
        this.getAttributeInstance(GenericAttributes.MAX_HEALTH).setValue(30.0D);
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
