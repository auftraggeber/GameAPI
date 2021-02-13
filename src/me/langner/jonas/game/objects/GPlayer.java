package me.langner.jonas.game.objects;


import com.sun.istack.internal.NotNull;
import org.bukkit.entity.Player;

import java.util.Set;

/**
 * Handles players for the game.
 * @author Jonas Langner
 * @version Alpha
 * @since 12.02.2021 (Alpha)
 */
public abstract class GPlayer extends GEntity {

    private Player player;
    private GKit kit;

    /**
     * Creates a new player.
     * @param game The game of the player.
     * @param player The bukkit player.
     */
    public GPlayer(@NotNull Game game, Player player) {
        super(game, player);

        this.player = player;
    }

    /**
     * Gets called when player buys a kit.
     * @param kit The kit to buy.
     */
    public abstract void onBuyKit(GKit kit);

    /**
     * Gets called when player sells a kit.
     * @param kit The kit to sell.
     */
    public abstract void onSellKit(GKit kit);

    /**
     * Gets called to check if a player has this kit.
     * @param kit The kit.
     * @return Returns if player has this kit.
     */
    public abstract boolean hasBoughtKit(GKit kit);

    @Override
    public void addToGame(Game game) {
        super.addToGame(game);

        /* überprüfen, ob Kit existiert */
        if (kit != null)
            kit.addToGame(game);
    }

    /**
     * Sets the players kit for this round.
     * @param kit The players kit.
     * @param checkIfBought Says if method should check, if player owns this kit.
     */
    public void setKit(GKit kit, boolean checkIfBought) {
        if (!checkIfBought || hasBoughtKit(kit))
            this.kit = kit;
    }

    /**
     * Sets the players kit without checking, if player owns this kit.
     * @param kit The players kit.
     */
    public void setKit(GKit kit) {
        setKit(kit, false);
    }

    public GKit getKit() {
        return kit;
    }

    public Player getPlayer() {
        return player;
    }

    public GTeam getTeam() {

        /* ermitteln, ob Teil eines Spiels */
        if (getGame() != null) {
            // Teil eines Spiels - alle Teams auslesen
            Set<GTeam> allTeams = getGame().getGameObjects(GTeam.class);

            /* für jedes Team ermitteln, ob Teil des Teams */
            for (GTeam tempTeam : allTeams) {

                /* ermitteln, ob Teil */
                if (tempTeam.getPlayers().contains(this))
                    // Teil des Teams - ausgeben
                    return tempTeam;

            }
        }

        return null;
    }
}
