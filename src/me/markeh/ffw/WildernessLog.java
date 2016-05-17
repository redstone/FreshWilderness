package me.markeh.ffw;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.World;

import me.markeh.factionsframework.jsonconf.JSONConf;

public class WildernessLog extends JSONConf<WildernessLog> {

	// -------------------------------------------------- //
	// FIELDS  
	// -------------------------------------------------- //
	
	public String uid;
	public Map<String, Long> chunks = new HashMap<String, Long>();
	
	// -------------------------------------------------- //
	// CONSTRUCT  
	// -------------------------------------------------- //
	
	private transient static Map<String, WildernessLog> logsMap = new HashMap<String, WildernessLog>();
	public static WildernessLog get(World world) {
		String uid = world.getUID().toString();
		
		if ( ! logsMap.containsKey(uid)) {
			WildernessLog log = new WildernessLog(uid);
			log.uid = uid;
			
			logsMap.put(uid, log);
		}
		
		return logsMap.get(uid);
	}
	
	public static List<WildernessLog> getAll() {
		return new ArrayList<WildernessLog>(logsMap.values());
	}
	
	private transient Path dataPath;
	private transient String worldId;
	private WildernessLog(String worldId) {
		this.worldId = worldId;
		
		Path warpsFolder = Paths.get(FreshWilderness.get().getDataFolder().toString(), "data");
		
		dataPath = Paths.get(warpsFolder.toString(), this.worldId + ".json");
		
		try { 
			this.loadFrom(dataPath);
			this.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void save() {
		if (this.dataPath == null) return;
		
		try {
			this.saveTo(dataPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
