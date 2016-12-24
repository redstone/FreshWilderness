package net.redstoneore.freshwilderness.integrations.kingdoms;

import org.bukkit.Bukkit;

import net.redstoneore.freshwilderness.integrations.Engine;
import net.redstoneore.freshwilderness.integrations.Ignition;

public class KingdomsIgnition extends Ignition {

	private static KingdomsIgnition i = new KingdomsIgnition();
	public static KingdomsIgnition get() { return i; }
	
	public KingdomsIgnition() {
		this.setPluginName("Kingdoms");
	}
	
	private KingdomsEngine engine = null;
	
	@Override
	public Boolean isEnabled() {
		return Bukkit.getPluginManager().isPluginEnabled("Kingdoms");
	}

	@Override
	public Engine getEngine() {
		if (this.engine == null) {
			this.engine = new KingdomsEngine();
		}
		
		return this.engine;
	}

}

