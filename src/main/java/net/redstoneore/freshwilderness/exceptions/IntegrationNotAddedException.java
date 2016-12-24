package net.redstoneore.freshwilderness.exceptions;

import net.redstoneore.freshwilderness.integrations.Ignition;

public class IntegrationNotAddedException extends Exception {
	
	private static final long serialVersionUID = 288580222293888226L;
	
	public IntegrationNotAddedException(Ignition ignition) {
		this.ignition = ignition;
	}
	
	private Ignition ignition;

	public Ignition getIgnition() {
		return this.ignition;
	}
	
}
