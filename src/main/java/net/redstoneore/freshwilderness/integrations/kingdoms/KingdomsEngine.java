package net.redstoneore.freshwilderness.integrations.kingdoms;

import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.scheduler.BukkitRunnable;
import org.kingdoms.constants.land.Land;
import org.kingdoms.constants.land.SimpleChunkLocation;
import org.kingdoms.events.LandUnclaimEvent;
import org.kingdoms.manager.game.GameManagement;

import net.redstoneore.freshwilderness.FreshWilderness;
import net.redstoneore.freshwilderness.integrations.Engine;
import net.redstoneore.freshwilderness.store.Config;

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
	
	@EventHandler
	public void onLandUnclaim(LandUnclaimEvent event) {
		if ( ! Config.get().logWhenUnclaimed) return;
		
		final Chunk chunk = event.getLand().getLoc().toChunk();

		// Run log later
		new BukkitRunnable() {
			@Override
			public void run() {
				FreshWilderness.get().logChunk(chunk);
			}
		}.runTaskLater(FreshWilderness.get(), 20);
	}

}