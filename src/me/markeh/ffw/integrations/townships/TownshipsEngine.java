package me.markeh.ffw.integrations.townships;

import org.bukkit.Chunk;

import com.herocraftonline.townships.Townships;
import com.herocraftonline.townships.regions.RegionCoords;

import me.markeh.ffw.integrations.Engine;
import me.markeh.ffw.store.Config;

public class TownshipsEngine extends Engine {

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
	
	private boolean isClaimed(Chunk chunk) {		
		RegionCoords regionCoords = new RegionCoords(chunk.getX(), chunk.getZ(), chunk.getWorld().getName());
		
		return Townships.regionManager.getRegion(regionCoords).hasTown();
	}

}
