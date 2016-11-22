package me.markeh.ffw.regentask;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

import me.markeh.ffw.FreshWilderness;
import me.markeh.ffw.regentask.versions.*;
import me.markeh.ffw.store.Config;

public abstract class RegenTask implements Runnable {
	
	// -------------------------------------------------- //
	// CONSTRUCT  
	// -------------------------------------------------- //
	
	public static RegenTask get(Chunk chunk) {
		try {
			Class.forName("net.minecraft.server.v1_11_R1.World");
			return new RegenTask_v1_11_R1(chunk);
		} catch (Exception e) {}
		
		try {
			Class.forName("net.minecraft.server.v1_10_R1.World");
			return new RegenTask_v1_10_R1(chunk);
		} catch (Exception e) {}
		
		try {
			Class.forName("org.bukkit.craftbukkit.v1_9_R2.CraftWorld");
			return new RegenTask_v1_9_R2(chunk);
		} catch (Exception e) {}
		
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
	
	protected RegenTask(Chunk chunk) {
		this.chunk = chunk;
	}
	
	// -------------------------------------------------- //
	// FIELDS  
	// -------------------------------------------------- //

	private Chunk chunk;
	
	// -------------------------------------------------- //
	// METHODS  
	// -------------------------------------------------- //
	
	@SuppressWarnings("deprecation")
	@Override
	public final void run() {  
		// Set some variables that we will use later
		World world = this.chunk.getWorld();
		
		// Regenerate the chunk
		world.regenerateChunk(this.chunk.getX(), this.chunk.getZ());
		
		// If required, go through the chunks and remove any blocks
		if ( ! Config.get().removeMaterialsOnRegen.isEmpty()) {
			int bx = this.chunk.getX() << 4;
			int bz = this.chunk.getZ() << 4;
			
			for (int xx = bx; xx < bx+16; xx++) {
				for (int zz = bz; zz < bz+16; zz++) {
					for (int yy = 0; yy < 128; yy++) {
						Block block = world.getBlockAt(xx, yy, zz);
						if ( ! Config.get().removeMaterialsOnRegen.contains(block.getType().name())) continue;
						
						// Run this task asynchronously and on the minecraft API - do not call bukkit api here
						FreshWilderness.get().getServer().getScheduler().runTaskAsynchronously(FreshWilderness.get(), new Runnable() {
							private RegenTask task;
							private World world;
							private int x;
							private int y;
							private int z;
							private int blockId;
							private byte data;
							
							@Override
							public void run() {
								// call the setBlockFast method 
								this.task.setBlockFast(this.world, this.x, this.y, this.z, this.blockId, data);
							}
							
							public Runnable prepareBlockFast(RegenTask task, World world, int x, int y, int z, int blockId, byte data) {
								this.task = task;
								this.world = world;
								this.x = x;
								this.y = y;
								this.z = z;
								this.blockId = blockId;
								this.data = data;
								
								return this;
							}
						}.prepareBlockFast(this,world, xx, yy, zz, 0, (byte) 0));
						
						world.refreshChunk(chunk.getX(), chunk.getZ());
					}
				}
			}
			
			// Remove entities if required
			if (Config.get().removeEntitiesOnRegen) {
				for (Entity entity : this.chunk.getEntities()) {
					if (Config.get().excludeEntitiesOnRegen.contains(entity.getType().getName())) continue;
					
					entity.remove();
				}  
			}
		}
	}

	public abstract void setBlockFast(World world, int x, int y, int z, int blockId, byte data);
	
}
