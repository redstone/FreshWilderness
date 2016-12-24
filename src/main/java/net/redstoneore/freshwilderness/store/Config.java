package net.redstoneore.freshwilderness.store;

import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.bukkit.Material;

import com.google.common.collect.Lists;

import net.redstoneore.freshwilderness.FreshWilderness;
import net.redstoneore.freshwilderness.Utils;
import net.redstoneore.rson.Rson;

public class Config extends Rson<Config> {
	
	// -------------------------------------------------- //
	// FIELDS  
	// -------------------------------------------------- //
	
	// How many seconds between resets? default: 3 days
	public long secondsBetweenResets = 259200L;
	
	// How many seconds between checks? default: 1 hour
	public long secondsBetweenChecks = 4600L;
	
	// Should the chunk reset even if there is a play in it 
	public boolean resetEvenIfPlayerInChunk = false;
	
	// What materials should we move from the chunk when it resets
	public List<String> removeMaterialsOnRegen = Utils.toList(
		Material.DIAMOND_ORE,
		Material.EMERALD_ORE,
		Material.MOB_SPAWNER,
		Material.CHEST
	);
	
	// Don't log if there is a faction in a chunk nearby
	public boolean dontLogIfClaimNearby = true;
	
	// Remove entities from the chunk when it is regenerated
	public boolean removeEntitiesOnRegen = true;

	// Exclude entities on regeneration 
	public List<String> excludeEntitiesOnRegen = Utils.toList(
		"PLAYER"
	);
	
	// Should we log a chunk when it is unclaimed
	public Boolean logWhenUnclaimed = true;
		
	// How long should be delay be before the chunk is reset
	public int secondsBeforeReset = 0;
	
	// Should we notify players in the chunk
	public boolean notifyChunkPlayersOfReset = false;
	
	// The message to send to players
	public String notifiyMessage = "This chunk is being reset! Please move away.";
	
	// -------------------------------------------------- //
	// PLUGIN SPECIFIC  
	// -------------------------------------------------- //
	// these fields only apply to certain plugins
	
	public List<String> worldGuardIgnoreRegions = Lists.newArrayList("__global__");
		
	// -------------------------------------------------- //
	// CONSTRUCT  
	// -------------------------------------------------- //
	
	private transient static Config i;
	public static Config get() {
		if (i == null) i = new Config();
		return i;
	}
	
	private transient Path dataPath;

	private Config() {
		this.dataPath = Paths.get(FreshWilderness.get().getDataFolder().toString(), "config.json");
		this.setup(this.dataPath, Charset.defaultCharset());
		try {
			this.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
