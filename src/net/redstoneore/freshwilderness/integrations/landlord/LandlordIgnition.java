package net.redstoneore.freshwilderness.integrations.landlord;

import org.bukkit.Bukkit;

import net.redstoneore.freshwilderness.integrations.Engine;
import net.redstoneore.freshwilderness.integrations.Ignition;

public class LandlordIgnition extends Ignition {

	private static LandlordIgnition i = new LandlordIgnition();
	public static LandlordIgnition get() { return i; }
	
	public LandlordIgnition() {
		this.setPluginName("Landlord");
	}
	
	private LandlordEngine engine = null;
	
	@Override
	public Boolean isEnabled() {
		return Bukkit.getPluginManager().isPluginEnabled(this.getPluginName());
	}

	@Override
	public Engine getEngine() {
		if (this.engine == null) {
			this.engine = new LandlordEngine();
		}
		
		return this.engine;
	}

}
