package me.markeh.ffw;

import java.util.ArrayList;
import java.util.HashMap;
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

public class FreshWilderness extends JavaPlugin implements Listener {

	private static FreshWilderness i;
	public static FreshWilderness get() { return i; }
	public FreshWilderness() { i = this; }
	
	private int taskid;
	
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
		
		String key = chunk.getX() + ":" + chunk.getZ();
		
		WildernessLog log = WildernessLog.get(chunk.getWorld());
				
		log.chunks.put(key, System.currentTimeMillis());
	}
	
	public final void checkChunks() {
		for (WildernessLog log : new ArrayList<WildernessLog>(WildernessLog.getAll())) {
			for (Entry<String, Long> chunk : new HashMap<String, Long>(log.chunks).entrySet()) {
				Long secs = (System.currentTimeMillis() * 1000) - (chunk.getValue() * 1000);
				
				if (secs > Config.get().secondsBetweenResets) {
					String[] split = chunk.getKey().split(":");
					World world = Bukkit.getWorld(UUID.fromString(log.uid));
					Chunk ch = world.getChunkAt(Integer.valueOf(split[0]), Integer.valueOf(split[1]));

					if ( ! Config.get().resetEvenIfPlayerInChunk) {
						Boolean foundPlayer = false;
						
						for (Player chPlayer : ch.getWorld().getPlayers()) {
							if (chPlayer.getLocation().getChunk() == ch) {
								foundPlayer = true;
								continue;
							}
						}
						
						if (foundPlayer) continue;
					}
					
					world.regenerateChunk(
						Integer.valueOf(split[0]),
						Integer.valueOf(split[1])
					);
					
					log.chunks.remove(chunk.getKey());
					
					if ( ! Config.get().removeMaterialsOnRegen.isEmpty()) {
						RegenTask.get(ch).go();
					}
				}
			}
			
			log.save();
		}
	}
	
}