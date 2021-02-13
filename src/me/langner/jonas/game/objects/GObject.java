package me.langner.jonas.game.objects;

/**
 * Class for all objects of the gameAPI.
 * @author Jonas Langner
 * @version Alpha
 * @since 12.02.2021 (Alpha)
 */
public abstract class GObject {

    private Game game;

    public GObject(Game game) {
        addToGame(game);
    }

    /**
     * Gets called if this object gets added to a game.
     * @param game The game.
     */
    public abstract void onGetAddedToGame(Game game);

    /**
     * Gets called if object get remove from game.
     * @param game The game.
     */
    public abstract void onGetsRemovedFromGame(Game game);

    /**
     * Adds this object to a game.
     * @param game The game.
     */
    public void addToGame(Game game) {
        /* entfernen, wenn noch anderes Spiel */
        if (this.game != null)
            removeFromGame(this.game);

        game.addGameObject(this);
        this.game = game;
    }

    /**
     * Remove this object from a game.
     * @param game The game.
     */
    public void removeFromGame(Game game) {
        game.removeObject(this);
        this.game = null;
    }

    public Game getGame() {
        return game;
    }
}
