package net.redstoneore.freshwilderness.integrations.residence;

import org.bukkit.Bukkit;

import net.redstoneore.freshwilderness.integrations.Engine;
import net.redstoneore.freshwilderness.integrations.Ignition;

public class ResidenceIgnition extends Ignition {

	private static ResidenceIgnition i = new ResidenceIgnition();
	public static ResidenceIgnition get() { return i; }
	
	private ResidenceIgnition() {
		this.setPluginName("Residence");
	}
	
	private ResidenceEngine engine = null;
	
	@Override
	public Boolean isEnabled() {
		return Bukkit.getPluginManager().isPluginEnabled(this.getPluginName());
	}

	@Override
	public Engine getEngine() {
		if (this.engine == null) {
			this.engine = new ResidenceEngine();
		}
		
		return this.engine;
	}

}
