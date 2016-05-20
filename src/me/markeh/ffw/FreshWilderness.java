package me.markeh.ffw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

import me.markeh.factionsframework.entities.Faction;
import me.markeh.factionsframework.entities.Factions;
import me.markeh.ffw.regentask.RegenTask;
import me.markeh.ffw.store.Config;
import me.markeh.ffw.store.WildernessLog;

public class FreshWilderness extends JavaPlugin implements Listener {

	// -------------------------------------------------- //
	// SINGLETON  
	// -------------------------------------------------- //
	
	private static FreshWilderness i;
	public static FreshWilderness get() { return i; }
	public FreshWilderness() { i = this; }
	
	// -------------------------------------------------- //
	// FIELDS  
	// -------------------------------------------------- //
	
	private int taskid;
	
	// -------------------------------------------------- //
	// METHODS  
	// -------------------------------------------------- //
	
	@Override
	public void onEnable() {
		Config.get().load();
		Config.get().save();
		
		this.getServer().getPluginManager().registerEvents(this, this);
		
		this.taskid = this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				checkChunks();
			}
		}, 0L, 20L * Config.get().secondsBetweenResets);
	}
	
	@Override
	public void onDisable() {
		this.getServer().getScheduler().cancelTask(this.taskid);
		
		for (WildernessLog log : WildernessLog.getAll()) {
			log.save();
		}
		
		HandlerList.unregisterAll((Listener) this); 
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (event.getPlayer().hasPermission("freshwilderness.ignore")) return;
		
		Chunk chunk = event.getBlock().getChunk();
		
		Faction faction = Factions.getFactionAt(chunk);
		if (faction == null) return;
		if ( ! faction.isNone()) return;
		
		if (Config.get().dontLogIfFactionNearby) {
			for (int x = chunk.getX() - 1 ; x < 1 ; x = x + 15) {
				for (int z =  chunk.getZ() - 1 ; z < 1 ; z = z + 15) {
					if (Math.sqrt(x*x + z*z) < (double) 1) {
						Faction nextFaction = Factions.getFactionAt(chunk.getWorld().getChunkAt(x, z));
						if (nextFaction == null) continue;
						if (nextFaction.isNone()) continue;
						
						// We found a faction, stop here 
						return;
					}
				}
			}
		}
		
		String key = chunk.getX() + ":" + chunk.getZ();
		
		WildernessLog log = WildernessLog.get(chunk.getWorld());
				
		log.chunks.put(key, System.currentTimeMillis());
	}
	
	public final void checkChunks() {
		for (WildernessLog log : new ArrayList<WildernessLog>(WildernessLog.getAll())) {
			for (Entry<String, Long> chunk : new HashMap<String, Long>(log.chunks).entrySet()) {
				// See how old the chunk is  
				Long secs = (System.currentTimeMillis() * 1000) - (chunk.getValue() * 1000);
				
				// Check if the chunk needs to be reset
				if (secs < Config.get().secondsBetweenResets) continue;
				
				// Determine the chunk using the key
				String[] coords = chunk.getKey().split(":");
				
				World world = Bukkit.getWorld(UUID.fromString(log.uid));
				Chunk ch = world.getChunkAt(Integer.valueOf(coords[0]), Integer.valueOf(coords[1]));
				
				// Find all players in the chunk
				List<Player> chPlayers = new ArrayList<Player>();
				for (Player chPlayer : ch.getWorld().getPlayers()) {
					if (chPlayer.getLocation().getChunk() != ch) continue;
					chPlayers.add(chPlayer);
				}
				
				// Some servers will want chunks to reset even if a player is in the chunk
				if ( ! Config.get().resetEvenIfPlayerInChunk) {
					// found players, so lets skip this chunk for now - we'll check in the next round
					if (chPlayers.size() > 0) continue;
				}
				
				// Remove it from the log
				log.chunks.remove(chunk.getKey());
				
				// Run our regeneration task a tick later (and a few seconds later if we have to)
				RegenTask task = RegenTask.get(ch);
				FreshWilderness.get().getServer().getScheduler().runTaskLater(FreshWilderness.get(), task, 1 + (20 * Config.get().secondsBeforeReset));
			}
			
			log.save();
		}
	}
	
}
