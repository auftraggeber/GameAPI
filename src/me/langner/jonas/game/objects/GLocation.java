package me.langner.jonas.game.objects;

import com.sun.istack.internal.NotNull;
import org.bukkit.Location;

/**
 * Handles locations.
 * @author Jonas Langner
 * @version Alpha
 * @since 13.02.2021 (Alpha)
 */
public abstract class GLocation extends GObject {

    private Location location;
    private double distance;
    private GMap map;

    /**
     * Creates a new location.
     * @param map The locations map.
     * @param location
     * @param distance
     */
    public GLocation(@NotNull GMap map, Location location, double distance) {
        super(map.getGame());
        this.location = location;
        this.distance = distance;
        this.map = map;
    }

    public GMap getMap() {
        return map;
    }

    public Location getLocation() {
        return location;
    }

    public double getDistance() {
        return distance;
    }
}
