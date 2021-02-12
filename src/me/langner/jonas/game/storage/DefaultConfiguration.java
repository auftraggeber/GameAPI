package me.langner.jonas.game.storage;

import org.bukkit.plugin.Plugin;

/**
 * Handles the storage of the default configuration.
 * @author Jonas Langner
 * @version Alpha
 * @since 12.02.2021 (Alpha)
 */
public abstract class DefaultConfiguration extends Configuration {

    /**
     * Creates new instance for configuration.
     * @param plugin The plugin, which uses the configuration.
     */
    public DefaultConfiguration(Plugin plugin) {
        super(plugin, "config.yml");
    }
}
