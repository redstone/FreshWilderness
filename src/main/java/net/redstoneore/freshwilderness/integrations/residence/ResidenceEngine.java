package net.redstoneore.freshwilderness.integrations.residence;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.event.ResidenceDeleteEvent;
import com.bekvon.bukkit.residence.protection.CuboidArea;

import net.redstoneore.freshwilderness.integrations.Engine;
import net.redstoneore.freshwilderness.store.Config;

public class ResidenceEngine extends Engine {

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
		int x1 = chunk.getX() << 4,
				z1 = chunk.getZ() << 4,
				y1 = 0;
			
			int x2 = x1 + 15,
				z2 = z1 + 15,
				y2 = chunk.getWorld().getMaxHeight();
			
		Location lowLocation = chunk.getWorld().getBlockAt(x1, y1, z1).getLocation();
		Location highLocation = chunk.getWorld().getBlockAt(x2, y2, z2).getLocation();
			
		CuboidArea area = new CuboidArea();
		area.setLowLocation(lowLocation);
		area.setHighLocation(highLocation);

		
		// TODO: is there a utility method that exists to do this already?
		return Residence.getInstance().getResidenceManager().getFromAllResidences(Config.get().residencesIncludeHidden, false, chunk.getWorld()).stream()
			.filter(claim -> 
				claim.checkCollision(area)
				
			/*
				claim.getAreaMap().values().stream()
					.filter(claimArea -> area.isAreaWithinArea(claimArea))
					.findFirst()
						.isPresent()
			 */
			)
			.findFirst()
				.isPresent();
	}
	
	@EventHandler
	public void onResidenceDelete(ResidenceDeleteEvent event) {
		event.getResidence().getAreaMap().values().forEach(area -> {
			area.getChunks().forEach(chunkRef -> {
				
			});
		});
	}
		
}