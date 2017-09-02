package net.redstoneore.freshwilderness.integrations.legacyfactions;

import org.bukkit.Bukkit;

import net.redstoneore.freshwilderness.integrations.Engine;
import net.redstoneore.freshwilderness.integrations.Ignition;

public class LegacyFactionsIgnition extends Ignition {

	private static LegacyFactionsIgnition i = new LegacyFactionsIgnition();
	public static LegacyFactionsIgnition get() { return i; }
	
	private LegacyFactionsIgnition() {
		this.setPluginName("LegacyFactions");
	}
	
	private LegacyFactionsEngine engine = null;
	
	@Override
	public Boolean isEnabled() {
		return Bukkit.getPluginManager().isPluginEnabled(this.getPluginName());
	}

	@Override
	public Engine getEngine() {
		if (this.engine == null) {
			this.engine = new LegacyFactionsEngine();
		}
		
		return this.engine;
	}

}
