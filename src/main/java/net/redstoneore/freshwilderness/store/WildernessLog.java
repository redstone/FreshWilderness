package net.redstoneore.freshwilderness.store;

import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.World;

import net.redstoneore.freshwilderness.FreshWilderness;
import net.redstoneore.rson.Rson;

public class WildernessLog extends Rson<WildernessLog> {

	// -------------------------------------------------- //
	// CONFIG OPTIONS  
	// -------------------------------------------------- //
	
	// uid of world
	public String uid;
	
	// chunk ids and their last update times
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
	
	private WildernessLog(String worldId) {
		this.worldId = worldId;
		
		Path warpsFolder = Paths.get(FreshWilderness.get().getDataFolder().toString(), "data");
		
		dataPath = Paths.get(warpsFolder.toString(), this.worldId + ".json");
		this.setup(this.dataPath, Charset.defaultCharset());
		try {
			this.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// -------------------------------------------------- //
	// FIELDS  
	// -------------------------------------------------- //
	
	private transient Path dataPath;
	private transient String worldId;

	
}
