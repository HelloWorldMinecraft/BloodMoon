package uk.co.jacekk.bukkit.bloodmoon.nms;

import net.minecraft.server.v1_15_R1.EntityTypes;
import net.minecraft.server.v1_15_R1.GenericAttributes;
import net.minecraft.server.v1_15_R1.World;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntityGeneric;
import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntityType;

public class EntityCreeper extends net.minecraft.server.v1_15_R1.EntityCreeper {

    private BloodMoon plugin;
    private BloodMoonEntityGeneric bloodMoonEntity;

    public EntityCreeper(World world) {
        super(EntityTypes.CREEPER, world);

        Plugin gPlugin = Bukkit.getPluginManager().getPlugin("BloodMoon");

        if (!(gPlugin instanceof BloodMoon)) {
            this.killEntity();
            return;
        }

        this.plugin = (BloodMoon) gPlugin;
        this.bloodMoonEntity = new BloodMoonEntityGeneric(this.plugin, this, BloodMoonEntityType.CREEPER);
    }

    @Override
    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.3D);
        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(100D);
        this.getAttributeInstance(GenericAttributes.MAX_HEALTH).setValue(25D);
        this.getAttributeInstance(GenericAttributes.ARMOR).setValue(6D);
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
