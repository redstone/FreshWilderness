package me.markeh.ffw;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.bukkit.Material;

import me.markeh.factionsframework.jsonconf.JSONConf;

public class Config extends JSONConf<Config> {
	
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
	public List<Material> removeMaterialsOnRegen = Utils.toList(
		Material.DIAMOND_ORE,
		Material.EMERALD_ORE,
		Material.MOB_SPAWNER,
		Material.CHEST
	);

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
	}
	
	public final void load() {
		if (this.dataPath == null) return;

		try { 
			this.loadFrom(this.dataPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public final void save() {
		if (this.dataPath == null) return;
		
		try {
			this.saveTo(dataPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
