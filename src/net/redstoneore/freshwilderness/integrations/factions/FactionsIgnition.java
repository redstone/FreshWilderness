package net.redstoneore.freshwilderness.integrations.factions;

import org.bukkit.Bukkit;

import net.redstoneore.freshwilderness.integrations.Engine;
import net.redstoneore.freshwilderness.integrations.Ignition;

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
