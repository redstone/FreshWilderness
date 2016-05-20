package me.markeh.ffw.exceptions;

import me.markeh.ffw.integrations.AbstractIgnition;

public class IntegrationNotAddedException extends Exception {
	
	private static final long serialVersionUID = 288580222293888226L;
	
	public IntegrationNotAddedException(AbstractIgnition ignition) {
		this.ignition = ignition;
	}
	
	private AbstractIgnition ignition;

	public AbstractIgnition getIgnition() {
		return this.ignition;
	}
	
}
