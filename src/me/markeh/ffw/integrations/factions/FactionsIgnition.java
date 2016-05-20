package me.markeh.ffw.integrations.factions;

import org.bukkit.Chunk;

import me.markeh.ffw.integrations.AbstractEngine;

public class FactionsIgnition extends AbstractEngine {

	private static FactionsIgnition i = new FactionsIgnition();
	public static FactionsIgnition get() { return i; }
	
	@Override
	public Boolean shouldReset(Chunk chunk) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean runReset(Chunk chunk) {
		// TODO Auto-generated method stub
		return null;
	}

}
