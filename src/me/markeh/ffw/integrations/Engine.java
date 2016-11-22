package me.markeh.ffw.integrations;

import org.bukkit.Chunk;

public abstract class Engine {

	public abstract Boolean shouldReset(Chunk chunk);
	
	public abstract Boolean runReset(Chunk chunk);
	
	public abstract Boolean shouldLogAt(Chunk chunk);
	
}
