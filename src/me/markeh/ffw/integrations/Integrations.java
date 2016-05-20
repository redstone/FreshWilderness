package me.markeh.ffw.integrations;

import java.util.ArrayList;
import java.util.List;

import me.markeh.ffw.exceptions.IntegrationNotAddedException;

public class Integrations {

	private static Integrations i = new Integrations();
	public static Integrations get() { return i; }
	
	private List<AbstractIgnition> enabledIntegrations = new ArrayList<AbstractIgnition>();
	private List<AbstractIgnition> disabledIntegrations = new ArrayList<AbstractIgnition>();
	
	public void addIntegration(AbstractIgnition iginition) {
		this.addIntegration(iginition, true);
	}
	
	public void addIntegration(AbstractIgnition iginition, Boolean autoEnable) {
		
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
	
	public void removeIntegration(AbstractIgnition iginition) {
		if (this.enabledIntegrations.contains(iginition)) this.enabledIntegrations.remove(iginition);
		if (this.disabledIntegrations.contains(iginition)) this.disabledIntegrations.remove(iginition);	
	}
	
	public void enable(AbstractIgnition iginition) throws IntegrationNotAddedException {
		if (this.disabledIntegrations.contains(iginition)) {
			this.disabledIntegrations.remove(iginition);
			this.enabledIntegrations.add(iginition);
			iginition.onIgnitionEnabled();
			return;
		}
		
		if (this.enabledIntegrations.contains(iginition)) {
			return;
		}
		
		throw new IntegrationNotAddedException(iginition);
	}
	
	public void disable(AbstractIgnition iginition) throws IntegrationNotAddedException {
		if (this.enabledIntegrations.contains(iginition)) {
			this.enabledIntegrations.remove(iginition);
			this.disabledIntegrations.add(iginition);
			iginition.onIgnitionDisabled();
			return;
		}
		
		if (this.disabledIntegrations.contains(iginition)) {
			return;
		}
		
		throw new IntegrationNotAddedException(iginition);
	}
	
	public List<AbstractIgnition> getEnabled() {
		return new ArrayList<AbstractIgnition>(this.enabledIntegrations);
	}
	
	public List<AbstractIgnition> getDisabled() {
		return new ArrayList<AbstractIgnition>(this.disabledIntegrations);
	}
	
}
