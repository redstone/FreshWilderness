package me.markeh.ffw.integrations.worldguard;

import java.util.Random;

import org.bukkit.Chunk;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import me.markeh.ffw.integrations.Engine;
import me.markeh.ffw.store.Config;

public class WorldGuardEngine extends Engine  {

	@Override
	public Boolean shouldReset(Chunk chunk) {
		return ! this.regionAt(chunk);
	}

	@Override
	public Boolean runReset(Chunk chunk) {
		return true;
	}

	@Override
	public Boolean shouldLogAt(Chunk chunk) {
		return ! this.regionAt(chunk);
	}
	
	public Boolean regionAt(Chunk chunk) {
		String regionName = this.randomRegionName();
		
		int x1 = chunk.getX() << 4,
			z1 = chunk.getZ() << 4,
			y1 = 0;
		
		int x2 = x1 + 15,
			z2 = z1 + 15,
			y2 = chunk.getWorld().getMaxHeight();
		
		BlockVector point1 = new BlockVector(x1, y1, z1);
		BlockVector point2 = new BlockVector(x2, y2, z2);
		
		ProtectedCuboidRegion chunkRegion = new ProtectedCuboidRegion(regionName, point1, point2);
		
		// Check over the regions, and ensure that none are in the configuration to ignore
		
		ApplicableRegionSet regions = WGBukkit.getRegionManager(chunk.getWorld()).getApplicableRegions(chunkRegion);
		for (ProtectedRegion region : regions) {
			if (Config.get().worldGuardIgnoreRegions.contains(region.getId())) continue;
			return true;
		}
		
		return false;
		
	}
	
	private Random random = new Random();

	private String randomRegionName() {
		return "FreshWilderness_" + this.random.nextInt((99999 - 10000) + 1) + 10000;
	}
}
