package uk.co.jacekk.bukkit.bloodmoon.nms;

import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.GenericAttributes;
import net.minecraft.server.v1_16_R3.World;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntityGeneric;
import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntityLiving;
import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntityType;
public class EntityWitch extends net.minecraft.server.v1_16_R3.EntityWitch {
    private BloodMoon plugin;
    private BloodMoonEntityLiving bloodMoonEntity;

    public EntityWitch(World world) {
        super(EntityTypes.WITCH, world);

        Plugin gPlugin = Bukkit.getPluginManager().getPlugin("BloodMoon");

        if (!(gPlugin instanceof BloodMoon)) {
            this.killEntity();
            return;
        }

        this.plugin = (BloodMoon) gPlugin;
        this.bloodMoonEntity = new BloodMoonEntityGeneric(this.plugin, this, BloodMoonEntityType.WITCH);
    }

    protected void initAttributes() {
        this.getAttributeInstance(GenericAttributes.MAX_HEALTH).setValue(52D);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.4D);
        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(70D);
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
