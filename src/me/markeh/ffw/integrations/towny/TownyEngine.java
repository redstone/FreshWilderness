package me.markeh.ffw.integrations.towny;

import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.scheduler.BukkitRunnable;

import com.palmergames.bukkit.towny.event.TownUnclaimEvent;
import com.palmergames.bukkit.towny.object.WorldCoord;

import me.markeh.ffw.FreshWilderness;
import me.markeh.ffw.integrations.Engine;
import me.markeh.ffw.store.Config;

public class TownyEngine extends Engine {

	@Override
	public Boolean shouldReset(Chunk chunk) {
		return ( ! this.isClaimed(chunk));
	}

	@Override
	public Boolean runReset(Chunk chunk) {
		return true;
	}

	@Override
	public Boolean shouldLogAt(Chunk chunk) {
		if (this.isClaimed(chunk)) return false;
		
		if (Config.get().dontLogIfClaimNearby) {
			for (int x = chunk.getX() - 1 ; x < 1 ; x = x + 15) {
				for (int z =  chunk.getZ() - 1 ; z < 1 ; z = z + 15) {
					if (Math.sqrt(x * x + z * z) < (double) 1) {
						Chunk nextChunk = chunk.getWorld().getChunkAt(x, z);
						if ( ! this.isClaimed(nextChunk)) continue;
						
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
	public Boolean isClaimed(Chunk chunk) {
		String world = chunk.getWorld().getName();
		Integer chunkX = chunk.getX();
		Integer chunkZ = chunk.getZ();
		
		WorldCoord worldcoord = new WorldCoord(world, chunkX, chunkZ);
		
		try {
			return worldcoord.getTownBlock().hasTown();
		} catch (Exception e) {
			return false;
		}
	}
	
	@EventHandler
	public void onUnclaim(TownUnclaimEvent event) {
		if ( ! Config.get().logWhenUnclaimed) return;
		
		WorldCoord coord = event.getWorldCoord();
		final Chunk chunk = coord.getBukkitWorld().getChunkAt(coord.getX(), coord.getZ());
		
		// Run log later
		new BukkitRunnable() {
			@Override
			public void run() {
				FreshWilderness.get().logChunk(chunk);
			}
		}.runTaskLater(FreshWilderness.get(), 20);
	}
	
}
