package me.markeh.ffw.integrations.kingdoms;

import org.bukkit.Chunk;
import org.kingdoms.constants.land.Land;
import org.kingdoms.constants.land.SimpleChunkLocation;
import org.kingdoms.manager.game.GameManagement;

import me.markeh.ffw.integrations.Engine;
import me.markeh.ffw.store.Config;

public class KingdomsEngine extends Engine {
	
	@Override
	public Boolean shouldReset(Chunk chunk) {
		return ( ! this.isKingdomAt(chunk));
	}

	@Override
	public Boolean runReset(Chunk chunk) {
		return true;
	}

	@Override
	public Boolean shouldLogAt(Chunk chunk) {
		if (this.isKingdomAt(chunk)) return false;
		
		if (Config.get().dontLogIfClaimNearby) {
			for (int x = chunk.getX() - 1 ; x < 1 ; x = x + 15) {
				for (int z =  chunk.getZ() - 1 ; z < 1 ; z = z + 15) {
					if (Math.sqrt(x * x + z * z) < (double) 1) {
						Chunk nextChunk = chunk.getWorld().getChunkAt(x, z);
						if ( ! this.isKingdomAt(nextChunk)) continue;
						
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
	public Boolean isKingdomAt(Chunk chunk) {
		SimpleChunkLocation schunk = new SimpleChunkLocation(chunk);
		
		Land land = GameManagement.getLandManager().getOrLoadLand(schunk);
		
		if (land == null || land.getOwner() == null) {
			return false;
		}
		
		return true;
	}
	
	// TODO: log unclaimed chunks, pending developer events

}