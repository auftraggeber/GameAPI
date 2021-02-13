package me.langner.jonas.game.objects;


import com.sun.istack.internal.NotNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Handles teams for games.
 * @author Jonas Langner
 * @version Alpha
 * @since 12.02.2021 (Alpha)
 */
public class GTeam extends GObject {

    private String name;
    private int index;
    private int maxPlayers;
    private Set<GPlayer> players = new HashSet<>();

    /**
     * Creates a new team.
     * @param game The game of the team.
     * @param name The name of the team.
     * @param index The teams index.
     * @param maxPlayers The max. amount of players which can part of the team
     */
    public GTeam(@NotNull Game game, String name, int index, int maxPlayers) {
        super(game);
        this.name = name;
        this.index = index;
        this.maxPlayers = maxPlayers;
    }

    /**
     * Adds a player to the team. The player and this team have to be part of the same game.
     * @param player The player to add.
     * @return Returns if player was successfully added.
     */
    public boolean addPlayer(GPlayer player) {

        /* überprüfen, ob selbes Spiel und genügend Platz */
        if (getGame() != null && player.getGame() != null && player.getGame().equals(getGame()) &&
                getPlayers().size() < maxPlayers) {
            // selbes Spiel -> hinzufügen

            /* erst ermitteln, ob Spieler noch ein Team hat */
            if (player.getTeam() == null || player.getTeam().removePlayer(player)) {
                // Spieler hat nun kein Team mehr
                if (!players.contains(player))
                    players.add(player);

                return true;
            }

        }

        return false;
    }

    /**
     * Removes player from this team.
     * @param player The player to remove.
     * @return Returns if player was successfully removed.
     */
    public boolean removePlayer(GPlayer player) {

        while (players.contains(player))
            players.remove(player);

        return !getPlayers().contains(player);
    }

    /**
     * Removes all players of an other game.
     */
    private void removeAllOfOtherGame() {

        for (GPlayer player : players) {

            /* ermitteln, ob zu anderem Spiel */
            if (getGame() == null || player.getGame() == null || !getGame().equals(player.getGame()))
                // gehört nicht mehr zu diesem Team
                removePlayer(player);
        }
    }

    /**
     * Gets called if team gets add to a new game.
     * @param game The game.
     */
    @Override
    public void onGetAddedToGame(Game game) {
        removeAllOfOtherGame();
    }

    /**
     * Gets called if team gets removed from a game.
     * @param game The game.
     */
    @Override
    public void onGetsRemovedFromGame(Game game) {
        removeAllOfOtherGame();
    }

    public Set<GPlayer> getPlayers() {
        return Collections.unmodifiableSet(players);
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }
}


