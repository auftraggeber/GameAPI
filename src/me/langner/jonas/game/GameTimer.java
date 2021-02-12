package me.langner.jonas.game;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

/**
 * Handles easy timing.
 * @author Jonas Langner
 * @version Alpha
 * @since 12.02.2021
 */
public abstract class GameTimer {

    private Plugin plugin;
    private int ticks;
    private long ticksIdle;
    private int taskID;

    /**
     * Creates a new timer.
     * @param plugin The plugin which hosts the timer.
     * @param ticks Amount of ticks.
     * @param ticksIdle Amount of ticks until next call.
     */
    public GameTimer(Plugin plugin, int ticks, long ticksIdle) {
        this.plugin = plugin;
        this.ticks = ticks;
        this.ticksIdle = ticksIdle;
    }

    /**
     * Gets called, if timer ticks.
     * @param ticksLeft Amount of ticks that are left.
     */
    public abstract void onTick(int ticksLeft);

    /**
     * Gets called if timer ends.
     */
    public abstract void onEnd();


    /**
     * Starts the timer.
     */
    public void start() {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                // Event aufrufen, wenn noch genügend Ticks über. Wenn nicht: Abbruch
                if (ticks > 0)
                    onTick(ticks--);
                else {
                    onEnd();
                    cancel();
                }
            }
        }, 0, ticksIdle);
    }

    /**
     * Stops the timer.
     */
    public void cancel() {
        Bukkit.getScheduler().cancelTask(taskID);
    }

}
