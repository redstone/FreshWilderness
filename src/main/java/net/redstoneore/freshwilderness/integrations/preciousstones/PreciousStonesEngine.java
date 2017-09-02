package net.redstoneore.freshwilderness.integrations.preciousstones;

import org.bukkit.Chunk;

import net.redstoneore.freshwilderness.integrations.Engine;
import net.redstoneore.freshwilderness.store.Config;
import net.sacredlabyrinth.Phaed.PreciousStones.PreciousStones;
import net.sacredlabyrinth.Phaed.PreciousStones.field.FieldFlag;

public class PreciousStonesEngine extends Engine {

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
		if (Config.get().preciousStonesClearIfFieldsNotRented) {
			return PreciousStones.API().getChunkFields(chunk, FieldFlag.ALL).stream()
				.filter(field -> field.isRented())
				.findFirst()
				.isPresent();
		} else {
			return !PreciousStones.API().getChunkFields(chunk, FieldFlag.ALL).isEmpty();
		}
	}
		
}
