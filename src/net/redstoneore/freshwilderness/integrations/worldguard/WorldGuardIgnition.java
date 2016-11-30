package net.redstoneore.freshwilderness.integrations.worldguard;

import org.bukkit.Bukkit;

import net.redstoneore.freshwilderness.integrations.Engine;
import net.redstoneore.freshwilderness.integrations.Ignition;

public class WorldGuardIgnition extends Ignition {

	private static WorldGuardIgnition i = new WorldGuardIgnition();
	public static WorldGuardIgnition get() { return i; }
	
	public WorldGuardIgnition() {
		this.setPluginName("WorldGuard");
	}
	
	private WorldGuardEngine engine = null;
	
	@Override
	public Boolean isEnabled() {
		return Bukkit.getPluginManager().isPluginEnabled("WorldGuard");
	}

	@Override
	public Engine getEngine() {
		if (this.engine == null) {
			this.engine = new WorldGuardEngine();
		}
		
		return this.engine;
	}

}
