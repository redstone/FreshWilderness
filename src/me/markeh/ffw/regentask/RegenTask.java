package me.markeh.ffw.regentask;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;

import me.markeh.ffw.Config;
import me.markeh.ffw.regentask.versions.RegenTask_v1_7_R4;
import me.markeh.ffw.regentask.versions.RegenTask_v1_8_R3;
import me.markeh.ffw.regentask.versions.RegenTask_v1_9_R1;

public abstract class RegenTask {
	
	public static RegenTask get(Chunk chunk) {
		try {
			Class.forName("org.bukkit.craftbukkit.v1_9_R1.CraftWorld");
			return new RegenTask_v1_9_R1(chunk);
		} catch (Exception e) {}
		
		try {
			Class.forName("org.bukkit.craftbukkit.v1_8_R3.CraftWorld");
			return new RegenTask_v1_8_R3(chunk);
		} catch (Exception e) {}
		
		try {
			Class.forName("org.bukkit.craftbukkit.v1_7_R4.CraftWorld");
			return new RegenTask_v1_7_R4(chunk);
		} catch (Exception e) {}
		
		return null;
	}
	
	public RegenTask(Chunk chunk) {
		this.chunk = chunk;
	}
	
	private Chunk chunk;
	
	@SuppressWarnings("deprecation")
	public void go() {
		int bx = this.chunk.getX() << 4;
		int bz = this.chunk.getZ() << 4;
		 
		World world = this.chunk.getWorld();
		 
		for (int xx = bx; xx < bx+16; xx++) {
			for (int zz = bz; zz < bz+16; zz++) {
				for (int yy = 0; yy < 128; yy++) {
					Block block = world.getBlockAt(xx, yy, zz);
					if (Config.get().removeMaterialsOnRegen.contains(block.getType())) {
						setBlockFast(world, xx, yy, zz, 0, (byte) 0);
					}
				}
			}
		}
		
		world.refreshChunk(chunk.getX(), chunk.getZ());
	}

	public abstract void setBlockFast(World world, int x, int y, int z, int blockId, byte data);
	
}
