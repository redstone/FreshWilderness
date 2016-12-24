package net.redstoneore.freshwilderness.integrations.towny;

import org.bukkit.Bukkit;

import net.redstoneore.freshwilderness.integrations.Engine;
import net.redstoneore.freshwilderness.integrations.Ignition;

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
