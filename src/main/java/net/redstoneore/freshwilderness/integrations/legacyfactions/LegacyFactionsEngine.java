package net.redstoneore.freshwilderness.integrations.legacyfactions;

import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;

import net.redstoneore.freshwilderness.FreshWilderness;
import net.redstoneore.freshwilderness.integrations.Engine;
import net.redstoneore.freshwilderness.store.Config;
import net.redstoneore.legacyfactions.entity.Board;
import net.redstoneore.legacyfactions.entity.Faction;
import net.redstoneore.legacyfactions.event.EventFactionsLandChange;
import net.redstoneore.legacyfactions.event.EventFactionsLandChange.LandChangeCause;
import net.redstoneore.legacyfactions.locality.Locality;

public class LegacyFactionsEngine extends Engine {

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
						Faction nextFaction = Board.get().getFactionAt(Locality.of(chunk));
						if (nextFaction == null) continue;
						if (nextFaction.isWilderness()) continue;
						
						// We found a faction, stop here 
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
	public Boolean isNone(Chunk chunk) {
		Faction faction = Board.get().getFactionAt(Locality.of(chunk));
		if (faction == null) return false;
		return (faction.isWilderness());
	}
	
	@EventHandler
	public void onUnclaim(EventFactionsLandChange event) {
		if ( ! Config.get().logWhenUnclaimed) return;
		
		if (event.getCause() != LandChangeCause.Unclaim) return;
		
		event.transactions((location, faction) -> {
			if (!faction.isWilderness()) return;
			FreshWilderness.get().logChunk(location.getChunk());
		});
	}

}
