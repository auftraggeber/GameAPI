package me.langner.jonas.game.objects;

import me.langner.jonas.game.Game;

/**
 * Interface for all objects of the gameAPI.
 * @author Jonas Langner
 * @version Alpha
 * @since 12.02.2021 (Alpha)
 */
public interface GObject {

    /**
     * Gets called if this object gets added to a game.
     * @param game The game.
     */
    void onGetAddedToGame(Game game);

    /**
     * Gets called if object get remove from game.
     * @param game The game.
     */
    void onGetsRemovedFromGame(Game game);



}
