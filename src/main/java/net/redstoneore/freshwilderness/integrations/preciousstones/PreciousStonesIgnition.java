package net.redstoneore.freshwilderness.integrations.preciousstones;

import org.bukkit.Bukkit;

import net.redstoneore.freshwilderness.integrations.Engine;
import net.redstoneore.freshwilderness.integrations.Ignition;

public class PreciousStonesIgnition extends Ignition {

	private static PreciousStonesIgnition i = new PreciousStonesIgnition();
	public static PreciousStonesIgnition get() { return i; }
	
	private PreciousStonesIgnition() {
		this.setPluginName("PreciousStones");
	}
	
	private PreciousStonesEngine engine = null;
	
	@Override
	public Boolean isEnabled() {
		return Bukkit.getPluginManager().isPluginEnabled(this.getPluginName());
	}

	@Override
	public Engine getEngine() {
		if (this.engine == null) {
			this.engine = new PreciousStonesEngine();
		}
		
		return this.engine;
	}

}
