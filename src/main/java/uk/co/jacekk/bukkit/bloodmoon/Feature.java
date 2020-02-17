package uk.co.jacekk.bukkit.bloodmoon;

import org.bukkit.event.Listener;
import uk.co.jacekk.bukkit.bloodmoon.feature.mob.*;
import uk.co.jacekk.bukkit.bloodmoon.feature.player.SwordDamageListener;
import uk.co.jacekk.bukkit.bloodmoon.feature.spawning.MoreSpawningListener;

public enum Feature {
	
	// Mob features
	SUPER_CREEPERS(SuperCreepersListener.class),
	EQUIPMENT(EquipmentListener.class),
	DAYLIGHT_PROOF_MOBS(DaylightProofMobsListener.class),
	FIRE_ARROWS(FireArrowsListener.class),
	MORE_DROPS(MoreDropsListener.class),
	MORE_EXP(MoreExpListener.class),
	BREAK_BLOCKS(null), // handled in BloodMoonEntity*
	TARGET_DISTANCE(TargetDistanceListener.class),
	ARROW_RATE(null), // handled in BloodMoonPathfinderGoalArrowAttack
	MOVEMENT_SPEED(MovementSpeedListener.class),

	PHANTOM_DROPS(PhantomDropListener.class),
	MORE_SPAWNING(MoreSpawningListener.class),
	// Player features
	SWORD_DAMAGE(SwordDamageListener.class);

	//private Class<? extends BaseListener<BloodMoon>> listenerClass;
        private final Class<? extends Listener> listenerClass;
	
	/*private Feature(Class<? extends BaseListener<BloodMoon>> listenerClass, PluginConfigKey enabledConfigKey){
		this.listenerClass = listenerClass;
		this.enabledConfigKey = enabledConfigKey;
	}
	
	public Class<? extends BaseListener<BloodMoon>> getListenerClass(){
		return this.listenerClass;
	}*/
        
        private Feature(Class<? extends Listener> listenerClass){
		this.listenerClass = listenerClass;
	}
	
	public Class<? extends Listener> getListenerClass(){
		return this.listenerClass;
	}
	
}
