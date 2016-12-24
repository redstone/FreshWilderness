package net.redstoneore.freshwilderness.integrations.townships;

import org.bukkit.Bukkit;

import net.redstoneore.freshwilderness.integrations.Engine;
import net.redstoneore.freshwilderness.integrations.Ignition;

public class TownshipsIgnition extends Ignition {

	private static TownshipsIgnition i = new TownshipsIgnition();
	public static TownshipsIgnition get() { return i; }
	
	public TownshipsIgnition() {
		this.setPluginName("Townships");
	}
	
	private TownshipsEngine engine = null;
	
	@Override
	public Boolean isEnabled() {
		return Bukkit.getPluginManager().isPluginEnabled("Townships");
	}

	@Override
	public Engine getEngine() {
		if (this.engine == null) {
			this.engine = new TownshipsEngine();
		}
		
		return this.engine;
	}

}
