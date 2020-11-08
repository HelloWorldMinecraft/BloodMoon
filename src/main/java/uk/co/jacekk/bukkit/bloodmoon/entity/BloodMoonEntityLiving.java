package uk.co.jacekk.bukkit.bloodmoon.entity;

import java.util.Random;
import java.util.UUID;

import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Location;
import org.bukkit.World;

import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;

public abstract class BloodMoonEntityLiving {

    private static final UUID followRangeUID = UUID.fromString("1737400d-3c18-41ba-8314-49a158481e1e");

    protected BloodMoon plugin;
    protected EntityLiving nmsEntity;
    protected Entity bukkitEntity;
    protected BloodMoonEntityType type;

    protected Random rand = new Random();

    public BloodMoonEntityLiving(BloodMoon plugin, EntityLiving nmsEntity, BloodMoonEntityType type) {
        this.plugin = plugin;
        this.nmsEntity = nmsEntity;
        this.bukkitEntity = nmsEntity.getBukkitEntity();
        this.type = type;
    }

    public static BloodMoonEntityLiving getBloodMoonEntity(EntityLiving nmsEntity) {
        try {
            return (BloodMoonEntityLiving) nmsEntity.getClass().getField("bloodMoonEntity").get(nmsEntity);
        } catch (Exception e) {
            throw new IllegalArgumentException(nmsEntity.getClass().getName() + " not supported");
        }
    }

    public void setFollowRangeMultiplier(double multiplier) {
        AttributeModifiable attributes = this.nmsEntity.getAttributeInstance(GenericAttributes.FOLLOW_RANGE);
        AttributeModifier modifier = new AttributeModifier(followRangeUID, "BloodMoon follow range multiplier", multiplier, AttributeModifier.Operation.MULTIPLY_BASE);

        attributes.removeModifier(modifier);
        attributes.addModifier(modifier);
    }

    public void setSpeedMultiplier(double multiplier) {
        try {
            AttributeModifiable theAttribute = this.nmsEntity.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED);
            if (theAttribute == null) {
                System.err.println("That was null, wierd");
                return;
            }
            double oldValue = theAttribute.getValue();
            theAttribute.setValue(oldValue * multiplier);
            if (BloodMoon.DEBUG) {
                System.err.println("Set speed attribute for mob " + this.nmsEntity.getName() + " was " + oldValue + " and is now " + theAttribute.getValue());
            }
        } catch (NullPointerException e) {
            System.err.println("Null Exception");
        }

        //AttributeInstance attributes = this.nmsEntity.getAttributeInstance(GenericAttributes.d);
        //AttributeModifier modifier = new AttributeModifier(movementSpeedUID, "BloodMoon movement speed multiplier", multiplier, 1);
        //attributes.c(modifier);
        //attributes.b(modifier);
    }

    public void onTick() {}
}
