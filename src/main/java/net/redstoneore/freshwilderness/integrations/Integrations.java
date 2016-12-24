package net.redstoneore.freshwilderness.integrations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.event.HandlerList;

import net.redstoneore.freshwilderness.FreshWilderness;
import net.redstoneore.freshwilderness.exceptions.IntegrationNotAddedException;

public class Integrations {

	private static Integrations i = new Integrations();
	public static Integrations get() { return i; }
	
	private List<Ignition> enabledIntegrations = new ArrayList<Ignition>();
	private List<Ignition> disabledIntegrations = new ArrayList<Ignition>();
	
	public void addIntegration(Ignition iginition) {
		this.addIntegration(iginition, true);
	}
	
	public void addIntegration(Ignition iginition, Boolean autoEnable) {
		if (autoEnable) {
			if (iginition.isEnabled()) {
				this.enabledIntegrations.add(iginition);
			} else {
				this.disabledIntegrations.add(iginition);
			}
		} else {
			this.disabledIntegrations.add(iginition);
		}
	}
	
	public void removeIntegration(Ignition iginition) {
		if (this.enabledIntegrations.contains(iginition)) this.enabledIntegrations.remove(iginition);
		if (this.disabledIntegrations.contains(iginition)) this.disabledIntegrations.remove(iginition);	
	}
	
	public void enable(Ignition iginition) throws IntegrationNotAddedException {
		if (this.disabledIntegrations.contains(iginition)) {
			this.disabledIntegrations.remove(iginition);
			this.enabledIntegrations.add(iginition);
			Bukkit.getServer().getPluginManager().registerEvents(iginition.getEngine(), FreshWilderness.get());
			iginition.onIgnitionEnabled();
			return;
		}
		
		if (this.enabledIntegrations.contains(iginition)) {
			return;
		}
		
		throw new IntegrationNotAddedException(iginition);
	}
	
	public void disable(Ignition iginition) throws IntegrationNotAddedException {
		if (this.enabledIntegrations.contains(iginition)) {
			this.enabledIntegrations.remove(iginition);
			this.disabledIntegrations.add(iginition);
			HandlerList.unregisterAll(iginition.getEngine());
			iginition.onIgnitionDisabled();
			return;
		}
		
		if (this.disabledIntegrations.contains(iginition)) {
			return;
		}
		
		throw new IntegrationNotAddedException(iginition);
	}
	
	public List<Ignition> getEnabled() {
		return Collections.unmodifiableList(new ArrayList<Ignition>(this.enabledIntegrations));
	}
	
	public List<Ignition> getDisabled() {
		return Collections.unmodifiableList(new ArrayList<Ignition>(this.disabledIntegrations));
	}
	
	public Boolean shouldLogAt(Chunk chunk) {
		for (Ignition integration : this.enabledIntegrations) {
			if (integration.getEngine().shouldLogAt(chunk)) {
				return true;
			}
		}
		
		return false;
	}
	
}
