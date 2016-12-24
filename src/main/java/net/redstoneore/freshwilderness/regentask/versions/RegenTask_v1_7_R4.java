package net.redstoneore.freshwilderness.regentask.versions;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import net.minecraft.server.v1_7_R4.Block;
import net.redstoneore.freshwilderness.regentask.RegenTask;

public class RegenTask_v1_7_R4 extends RegenTask {
	
	public RegenTask_v1_7_R4(Chunk chunk) {
		super(chunk);
	}

	@Override
	public void setBlockFast(World world, int x, int y, int z, int blockId, byte data) {
		net.minecraft.server.v1_7_R4.World w = ((CraftWorld) world).getHandle();
		net.minecraft.server.v1_7_R4.Chunk chunk = w.getChunkAt(x >> 4, z >> 4);
		
		chunk.a(x & 0x0f, y, z & 0x0f, Block.getById(blockId), data);
		 
		w.notify(x, y, z);
	}

	@Override
	public void regenerateChunk(World world, int x, int z) {
		world.regenerateChunk(x, z);
		
	}

}
