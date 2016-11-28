package me.markeh.ffw.integrations.towny;

import org.bukkit.Bukkit;

import me.markeh.ffw.integrations.Engine;
import me.markeh.ffw.integrations.Ignition;

public class TownyIgnition extends Ignition {

	private static TownyIgnition i = new TownyIgnition();
	public static TownyIgnition get() { return i; }
	
	public TownyIgnition() {
		this.setPluginName("Towny");
	}
	
	private TownyEngine engine = null;
	
	@Override
	public Boolean isEnabled() {
		return Bukkit.getPluginManager().isPluginEnabled("Towny");
	}

	@Override
	public Engine getEngine() {
		if (this.engine == null) {
			this.engine = new TownyEngine();
		}
		
		return this.engine;
	}

}
