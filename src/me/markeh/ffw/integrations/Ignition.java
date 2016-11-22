package me.markeh.ffw.integrations;

public abstract class Ignition {

	private String pluginName;
	
	public final void setPluginName(String name) {
		this.pluginName = name;
	}
	
	public final String getPluginName() {
		return this.pluginName;
	}
	
	public abstract Boolean isEnabled();
	
	public abstract Engine getEngine();
	
	public void onIgnitionEnabled() { }
	
	public void onIgnitionDisabled() { }
	
}
