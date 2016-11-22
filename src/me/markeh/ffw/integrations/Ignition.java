package me.markeh.ffw.integrations;

public abstract class Ignition {

	// ----------------------------------------
	// FIELDS
	// ----------------------------------------
	
	private String pluginName;
	
	// ----------------------------------------
	// METHODS
	// ----------------------------------------
	
	/**
	 * Set the plugin name
	 * @param name of plugin
	 */
	public final void setPluginName(String name) {
		this.pluginName = name;
	}
	
	/**
	 * Get plugin name
	 * @return plugin name
	 */
	public final String getPluginName() {
		return this.pluginName;
	}
	
	// ----------------------------------------
	// ABSTRACT METHODS
	// ----------------------------------------
	
	/**
	 * check if plugin is enabled
	 * @return true if enabled
	 */
	public abstract Boolean isEnabled();
	
	/**
	 * get engine for this integration 
	 * @return the engine
	 */
	public abstract Engine getEngine();
	
	// ----------------------------------------
	// OPTIONAL OVERRIDES
	// ----------------------------------------
	
	/**
	 * Optional override, called when integrations ignition is enabled
	 */
	public void onIgnitionEnabled() { }
	
	/**
	 * Optional override, called when integrations ignition is disabled
	 */
	public void onIgnitionDisabled() { }
	
}
