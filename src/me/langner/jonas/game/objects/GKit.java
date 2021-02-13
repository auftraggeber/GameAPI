package me.langner.jonas.game.objects;

import com.sun.istack.internal.NotNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Handles kits which can be used by players.
 * @author Jonas Langner
 * @version Alpha
 * @since 12.02.2021 (Alpha)
 */
public abstract class GKit extends GObject {

    private String name;

    /**
     * Creates a new kit.
     * @param game The game of the kit.
     * @param name The name of the kit.
     */
    public GKit(@NotNull Game game, String name) {
        super(game);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Set<GPlayer> getUsingPlayers() {
        Set<GPlayer> usingPlayers = new HashSet<>();

        /* ermitteln, ob zu einem spiel zugehörig */
        if (getGame() != null) {
            // zu einem spiel zugehörig
            Set<GPlayer> allPlayers = getGame().getGameObjects(GPlayer.class);

            /* für jeden überprüfen, ob er das Kit nutzt */
            for (GPlayer player : allPlayers) {

                if (player.getKit() != null && player.getKit().equals(this))
                    // nutzt dieses Kit -> zur Sammlung hinzufügen
                    usingPlayers.add(player);
            }
        }

        return Collections.unmodifiableSet(usingPlayers);
    }
}
