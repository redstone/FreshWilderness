package me.markeh.ffw.integrations;

public abstract class AbstractIgnition {

	private String pluginName;
	
	public final void setPluginName(String name) {
		this.pluginName = name;
	}
	
	public final String getPluginName() {
		return this.pluginName;
	}
	
	public abstract Boolean isEnabled();
	
	public abstract AbstractEngine getEngine();
	
	public void onIgnitionEnabled() { }
	
	public void onIgnitionDisabled() { }
	
}
