package me.markeh.ffw.integrations.factions;

import org.bukkit.Bukkit;

import me.markeh.ffw.integrations.Engine;
import me.markeh.ffw.integrations.Ignition;

public class FactionsIgnition extends Ignition {

	private static FactionsIgnition i = new FactionsIgnition();
	public static FactionsIgnition get() { return i; }
	
	public FactionsIgnition() {
		this.setPluginName("Factions");
	}
	
	private FactionsEngine engine = null;
	
	@Override
	public Boolean isEnabled() {
		return Bukkit.getPluginManager().isPluginEnabled("FactionsFramework");
	}

	@Override
	public Engine getEngine() {
		if (this.engine == null) {
			this.engine = new FactionsEngine();
		}
		
		return this.engine;
	}

}
