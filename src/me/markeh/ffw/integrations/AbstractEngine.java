package me.markeh.ffw.integrations;

import org.bukkit.Chunk;

public abstract class AbstractEngine {

	public abstract Boolean shouldReset(Chunk chunk);
	
	public abstract Boolean runReset(Chunk chunk);
	
}
