package net.redstoneore.freshwilderness.integrations.landlord;

import org.bukkit.Chunk;

import com.jcdesimp.landlord.persistantData.OwnedLand;

import net.redstoneore.freshwilderness.integrations.Engine;
import net.redstoneore.freshwilderness.store.Config;

public class LandlordEngine extends Engine {

	@Override
	public Boolean shouldReset(Chunk chunk) {
		return ! this.isOwned(chunk);
	}

	@Override
	public Boolean runReset(Chunk chunk) {
		return true;
	}

	@Override
	public Boolean shouldLogAt(Chunk chunk) {
		if (this.isOwned(chunk)) return false;
		
		if (Config.get().dontLogIfClaimNearby) {
			for (int x = chunk.getX() - 1 ; x < 1 ; x = x + 15) {
				for (int z =  chunk.getZ() - 1 ; z < 1 ; z = z + 15) {
					if (Math.sqrt(x * x + z * z) < (double) 1) {
						Chunk nextChunk = chunk.getWorld().getChunkAt(x, z);
						if ( ! this.isOwned(nextChunk)) continue;
						
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
	public Boolean isOwned(Chunk chunk) {
		return OwnedLand.getLandFromDatabase(chunk.getX(), chunk.getZ(), chunk.getWorld().getName()) != null;
	}
	
	// TODO: log unclaimed chunks, pending issue https://github.com/jcdesimp/Landlord/issues/51
	
}
