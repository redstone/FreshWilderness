package me.markeh.ffw.regentask.versions;

import org.bukkit.Chunk;
import org.bukkit.World;

import org.bukkit.craftbukkit.v1_9_R2.CraftWorld;

import me.markeh.ffw.regentask.RegenTask;
import net.minecraft.server.v1_9_R2.BlockPosition;
import net.minecraft.server.v1_9_R2.IBlockData;

public class RegenTask_v1_9_R2 extends RegenTask {

	public RegenTask_v1_9_R2(Chunk chunk) {
		super(chunk);
	}

	@Override
	public void setBlockFast(World world, int x, int y, int z, int blockId, byte data) {
		net.minecraft.server.v1_9_R2.World w = ((CraftWorld) world).getHandle();
		net.minecraft.server.v1_9_R2.Chunk chunk = w.getChunkAt(x >> 4, z >> 4);
		
		BlockPosition bp = new BlockPosition(x, y, z);
		
		int combined = blockId + (data << 12);
		
		IBlockData ibd = net.minecraft.server.v1_9_R2.Block.getByCombinedId(combined);
		chunk.a(bp, ibd);
	}

	@Override
	public void regenerateChunk(World world, int x, int z) {
		world.regenerateChunk(x, z);
	}
	
}
