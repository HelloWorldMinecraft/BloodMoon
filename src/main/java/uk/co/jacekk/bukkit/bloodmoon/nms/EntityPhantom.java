package uk.co.jacekk.bukkit.bloodmoon.nms;

import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntityGeneric;
import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntityLiving;
import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntityType;

import java.lang.reflect.Field;
import java.util.Collection;

public class EntityPhantom extends net.minecraft.server.v1_16_R3.EntityPhantom {
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
        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(100D);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.6D);
        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(6.0D);
        this.getAttributeInstance(GenericAttributes.ARMOR).setValue(8.0D);
        this.getAttributeInstance(GenericAttributes.MAX_HEALTH).setValue(10.0D);
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
