package me.markeh.ffw.integrations.factions;

import org.bukkit.Bukkit;

import me.markeh.ffw.integrations.AbstractEngine;
import me.markeh.ffw.integrations.AbstractIgnition;

public class FactionsEngine extends AbstractIgnition {

	public FactionsEngine() {
		this.setPluginName("Factions");
	}
	
	@Override
	public Boolean isEnabled() {
		return Bukkit.getPluginManager().isPluginEnabled("FactionsFramework");
	}

	@Override
	public AbstractEngine getEngine() {
		return null;
	}

}
