package uk.co.jacekk.bukkit.bloodmoon.entity;

import net.minecraft.server.v1_15_R1.EntityHuman;
import net.minecraft.server.v1_15_R1.EntityMonster;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
public class BloodMoonEntityEndermen extends BloodMoonEntityMonster {

    public BloodMoonEntityEndermen(BloodMoon plugin, EntityMonster nmsEntity, BloodMoonEntityType type) {
        super(plugin, nmsEntity, type);
    }

    @Override
    public void onTick() {
        if (nmsEntity.getGoalTarget() instanceof EntityHuman && nmsEntity.world.getTime() % 20 == 0 && nmsEntity.world.worldData.getName().equals(nmsEntity.getGoalTarget().world.worldData.getName())) {
            Block[] blocks = new Block[3];

            blocks[0] = this.getBreakableTargetBlock();
            blocks[1] = blocks[0].getRelative(BlockFace.DOWN);
            blocks[2] = blocks[1].getRelative(BlockFace.DOWN);

            for (Block block : blocks) {
                this.attemptBreakBlock(block);
            }
        }
    }

}
