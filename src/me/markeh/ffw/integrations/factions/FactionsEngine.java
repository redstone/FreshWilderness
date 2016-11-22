package me.markeh.ffw.integrations.factions;

import org.bukkit.Chunk;

import me.markeh.factionsframework.entities.Faction;
import me.markeh.factionsframework.entities.Factions;
import me.markeh.ffw.integrations.Engine;
import me.markeh.ffw.store.Config;

public class FactionsEngine extends Engine {

	@Override
	public Boolean shouldReset(Chunk chunk) {
		return this.isNone(chunk);
	}

	@Override
	public Boolean runReset(Chunk chunk) {
		return true;
	}

	@Override
	public Boolean shouldLogAt(Chunk chunk) {
		if ( ! this.isNone(chunk)) return false;
		
		if (Config.get().dontLogIfClaimNearby) {
			for (int x = chunk.getX() - 1 ; x < 1 ; x = x + 15) {
				for (int z =  chunk.getZ() - 1 ; z < 1 ; z = z + 15) {
					if (Math.sqrt(x * x + z * z) < (double) 1) {
						Faction nextFaction = Factions.getFactionAt(chunk.getWorld().getChunkAt(x, z));
						if (nextFaction == null) continue;
						if (nextFaction.isNone()) continue;
						
						// We found a faction, stop here 
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
	public Boolean isNone(Chunk chunk) {
		Faction faction = Factions.getFactionAt(chunk);
		if (faction == null) return false;
		return (faction.isNone());
	}

}
