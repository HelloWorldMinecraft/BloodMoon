package uk.co.jacekk.bukkit.bloodmoon.entity;

import net.minecraft.server.v1_15_R1.EntityHuman;
import net.minecraft.server.v1_15_R1.EntityMonster;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;

import java.util.Collections;

public abstract class BloodMoonEntityMonster extends BloodMoonEntityLiving {

    protected EntityMonster nmsEntity;

    public BloodMoonEntityMonster(BloodMoon plugin, EntityMonster nmsEntity, BloodMoonEntityType type) {
        super(plugin, nmsEntity, type);

        this.nmsEntity = nmsEntity;
    }

    protected Block getBreakableTargetBlock() {
        return ((LivingEntity) nmsEntity.getBukkitEntity()).getTargetBlock(Collections.singleton(Material.AIR), 10);
    }

    protected void attemptBreakBlock(Block block) {
        Material type = block.getType();

        if (type != Material.AIR) {
            if (Math.random() < 0.001 && this.rand.nextDouble() * 10 > (block.getType() == Material.OBSIDIAN ? 10 : block.getType().getHardness())) {
                EntityChangeBlockEvent event = new EntityChangeBlockEvent(bukkitEntity, block, Material.AIR.createBlockData());
                plugin.getServer().getPluginManager().callEvent(event);

                if (!event.isCancelled()) {
                    block.breakNaturally();
                }
            }
        }
    }

    public void onTick() {
        Block[] blocks = new Block[2];

        blocks[0] = this.getBreakableTargetBlock();
        blocks[1] = blocks[0].getRelative(BlockFace.DOWN);

        for (Block block : blocks) {
            this.attemptBreakBlock(block);
        }
    }
}
