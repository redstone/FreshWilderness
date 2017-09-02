package net.redstoneore.freshwilderness.integrations.griefprevention;

import org.bukkit.Bukkit;

import net.redstoneore.freshwilderness.integrations.Engine;
import net.redstoneore.freshwilderness.integrations.Ignition;

public class GriefPreventionIgnition extends Ignition {

	private static GriefPreventionIgnition i = new GriefPreventionIgnition();
	public static GriefPreventionIgnition get() { return i; }
	
	public GriefPreventionIgnition() {
		this.setPluginName("GriefPrevention");
	}
	
	private GriefPreventionEngine engine = null;
	
	@Override
	public Boolean isEnabled() {
		return Bukkit.getPluginManager().isPluginEnabled(this.getPluginName());
	}

	@Override
	public Engine getEngine() {
		if (this.engine == null) {
			this.engine = new GriefPreventionEngine();
		}
		
		return this.engine;
	}
}
