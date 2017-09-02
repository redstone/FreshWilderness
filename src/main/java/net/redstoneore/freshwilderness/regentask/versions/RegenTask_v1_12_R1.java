package net.redstoneore.freshwilderness.regentask.versions;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;

import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.IBlockData;
import net.redstoneore.freshwilderness.regentask.RegenTask;

public class RegenTask_v1_12_R1 extends RegenTask {

	public RegenTask_v1_12_R1(Chunk chunk) {
		super(chunk);
	}

	@Override
	public void setBlockFast(World world, int x, int y, int z, int blockId, byte data) {
		net.minecraft.server.v1_12_R1.World w = ((CraftWorld) world).getHandle();
		net.minecraft.server.v1_12_R1.Chunk chunk = w.getChunkAt(x >> 4, z >> 4);
		
		BlockPosition bp = new BlockPosition(x, y, z);
		
		int combined = blockId + (data << 12);
		
		IBlockData ibd = net.minecraft.server.v1_12_R1.Block.getByCombinedId(combined);
		chunk.a(bp, ibd);
	}

	@Override
	public void regenerateChunk(World world, int x, int z) {
		world.regenerateChunk(x, z);
	}
	
}