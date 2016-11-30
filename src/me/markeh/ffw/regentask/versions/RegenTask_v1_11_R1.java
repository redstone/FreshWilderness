package me.markeh.ffw.regentask.versions;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;

import me.markeh.ffw.regentask.RegenTask;
import net.minecraft.server.v1_11_R1.BlockPosition;
import net.minecraft.server.v1_11_R1.IBlockData;

public class RegenTask_v1_11_R1 extends RegenTask {

	public RegenTask_v1_11_R1(Chunk chunk) {
		super(chunk);
	}

	@Override
	public void setBlockFast(World world, int x, int y, int z, int blockId, byte data) {
		net.minecraft.server.v1_11_R1.World w = ((CraftWorld) world).getHandle();
		net.minecraft.server.v1_11_R1.Chunk chunk = w.getChunkAt(x >> 4, z >> 4);
		
		BlockPosition bp = new BlockPosition(x, y, z);
		
		int combined = blockId + (data << 12);
		
		IBlockData ibd = net.minecraft.server.v1_11_R1.Block.getByCombinedId(combined);
		chunk.a(bp, ibd);
	}

	@Override
	public void regenerateChunk(World world, int x, int z) {
		world.regenerateChunk(x, z);
	}
	
}