package me.langner.jonas.game.objects;

import org.bukkit.plugin.Plugin;

/**
 * Handles the default timers.
 * @author Jonas Langner
 * @version Alpha
 * @since 12.02.2021
 */
public abstract class StartTimer extends GameTimer {

    /**
     * Creates a new timer of 30 secs.
     * @param plugin The plugin which handles the timer.
     */
    public StartTimer(Plugin plugin) {
        super(plugin,30,20);
    }

}
