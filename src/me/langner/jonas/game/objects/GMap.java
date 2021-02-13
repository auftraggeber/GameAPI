package me.langner.jonas.game.objects;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Handles maps for games.
 * @author Jonas Langner
 * @version Alpha
 * @since 12.02.2021 (Alpha)
 */
public class GMap extends GObject {

    private String worldName;
    private World world;

    /**
     * Creates a new map for a game.
     * @param worldName The name of the map (has to be the name of the bukkit world).
     */
    public GMap(Game game, String worldName) {
        super(game);
        this.worldName = worldName;
    }

    /**
     * Gets called if map gets added to a game.
     * @param game The game.
     */
    @Override
    public void onGetAddedToGame(Game game) {
        /* Locations ebenso setzen */
        getLocations().forEach(location -> {location.addToGame(game);});
    }

    /**
     * Gets called if map gets removed from a game.
     * @param game The game.
     */
    @Override
    public void onGetsRemovedFromGame(Game game) {
        /* Locations auch entfernen */
        getLocations().forEach(location -> {location.removeFromGame(game);});
    }

    /**
     * Loads the bukkit world.<br/>
     * Loads world like this: {@link Bukkit#createWorld(WorldCreator)}
     */
    public void load() {
        /* ermitteln, ob schon geladen */
        if (Bukkit.getWorld(worldName) != null) {
            // schon geladen
            world = Bukkit.getWorld(worldName);
        }
        else {
            // sonst selbst laden
            world = Bukkit.createWorld(new WorldCreator(worldName));
        }
    }

    public String getWorldName() {
        return worldName;
    }

    public Set<GLocation> getLocations() {
        Set<GLocation> locations = new HashSet<>();

        /* ermitteln, ob Spiel existiert */
        if (getGame() != null) {
            // existiert -> alle locations auslesen
            Set<GLocation> allLocations = getGame().getGameObjects(GLocation.class);

            /* für jeder ermitteln, ob zugehörig */
            for (GLocation location : allLocations) {

                /* ermitteln, ob gleiches Spiel und diese Map */
                if (location.getGame() != null && location.getGame().equals(getGame()) && location.getMap().equals(this))
                    // gehört hierzu - speichern
                    locations.add(location);
            }
        }

        /* ausgabe */
        return Collections.unmodifiableSet(locations);
    }

    public World getWorld() {
        return world;
    }
}
