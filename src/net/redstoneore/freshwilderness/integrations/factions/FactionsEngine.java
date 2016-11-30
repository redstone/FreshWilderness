package net.redstoneore.freshwilderness.integrations.factions;

import java.util.Set;

import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.scheduler.BukkitRunnable;

import me.markeh.factionsframework.entities.Faction;
import me.markeh.factionsframework.entities.Factions;
import me.markeh.factionsframework.event.EventFactionsChunksChange;
import net.redstoneore.freshwilderness.FreshWilderness;
import net.redstoneore.freshwilderness.integrations.Engine;
import net.redstoneore.freshwilderness.store.Config;

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
	
	@EventHandler
	public void onUnclaim(EventFactionsChunksChange event) {
		if ( ! Config.get().logWhenUnclaimed) return;
		
		if ( ! event.getNewFaction().isNone()) return;
		
		final Set<Chunk> chunks = event.getChunks();
		
		// Run log later
		new BukkitRunnable() {
			@Override
			public void run() {
				for (Chunk chunk : chunks) {
					FreshWilderness.get().logChunk(chunk);
				}
				
			}
		}.runTaskLater(FreshWilderness.get(), 20);
	}

}
