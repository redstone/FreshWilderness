package net.redstoneore.freshwilderness.regentask.versions;

import org.bukkit.Chunk;
import org.bukkit.World;

import net.redstoneore.freshwilderness.regentask.RegenTask;

public class RegenTask_v1_7_R4_Thermos extends RegenTask {
	
	public RegenTask_v1_7_R4_Thermos(Chunk chunk) {
		super(chunk);
	}

	@Override
	public void setBlockFast(World world, int x, int y, int z, int blockId, byte data) {
	}

	@Override
	public void regenerateChunk(World world, int x, int z) {
		
	}

}
