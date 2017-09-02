package net.redstoneore.freshwilderness.integrations.griefprevention;

import org.bukkit.Chunk;

import me.ryanhamshire.GriefPrevention.GriefPrevention;
import net.redstoneore.freshwilderness.integrations.Engine;
import net.redstoneore.freshwilderness.store.Config;

public class GriefPreventionEngine extends Engine {
	
	@Override
	public Boolean shouldReset(Chunk chunk) {
		return ( ! this.isClaimAt(chunk));
	}

	@Override
	public Boolean runReset(Chunk chunk) {
		return true;
	}

	@Override
	public Boolean shouldLogAt(Chunk chunk) {
		if (this.isClaimAt(chunk)) return false;
		
		if (Config.get().dontLogIfClaimNearby) {
			for (int x = chunk.getX() - 1 ; x < 1 ; x = x + 15) {
				for (int z =  chunk.getZ() - 1 ; z < 1 ; z = z + 15) {
					if (Math.sqrt(x * x + z * z) < (double) 1) {
						Chunk nextChunk = chunk.getWorld().getChunkAt(x, z);
						if ( ! this.isClaimAt(nextChunk)) continue;
						
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
	public Boolean isClaimAt(Chunk chunk) {
		return GriefPrevention.instance.dataStore.getClaims(chunk.getX(), chunk.getZ()).stream()
			.filter(claim -> claim.getGreaterBoundaryCorner().getWorld().equals(chunk.getWorld()))
			.findFirst()
				.isPresent();
	}
	
}