package me.langner.jonas.game.objects;

import java.util.*;

/**
 * Handles games.
 * @author Jonas Langner
 * @version Alpha
 * @since 12.02.2021 (Alpha)
 */
public abstract class Game {

    private LinkedList<GameState> states = new LinkedList<>();
    private GameState state;

    private String name;
    private int minPlayers, maxPlayers;
    private Set<GObject> gameObjects = new HashSet<>();

    public Game(String name, int minPlayers, int maxPlayers) {
        this.name = name;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
    }

    /**
     * Gets called if the game should start.
     * @param action Action, how the game started.
     * @param players Amount of players.
     * @return If this method returns true, the game starts.
     */
    public abstract boolean onStartGame(StartAction action, int players);

    /**
     * Gets called, if the state of the game changes.
     * @param newState The new state of the game.
     */
    public abstract void onNextState(GameState newState);

    /**
     * Changes the state of the game to the next one.
     * @return Returns if state was changed.
     */
    public boolean nextState() {
        int currentIndex = states.indexOf(state);

        /* ermitteln, ob es ein nächstes objekt gibt */
        if (states.size() > currentIndex) {
            // gibt nächstes objekt -> status ändern
            state = states.get(currentIndex + 1);
            /* event aufrufen */
            onNextState(state);

            return true;
        }

        return false;
    }

    /**
     * Starts the game.
     * @return Returns if game was started.
     */
    public boolean start() {
        return start(StartAction.PLUGIN);
    }

    /**
     * Starts the game.
     * @param action Action that called the start-method.
     * @return Returns if game was started.
     */
    private boolean start(StartAction action) {
        /* überprüfen, ob gestartet werden kann */
        if (getGameObjects(GPlayer.class).size() < minPlayers)
            return false; // zu wenig spieler

        /* event übergeben */
        return onStartGame(action, getGameObjects(GPlayer.class).size());
    }

    /**
     * Adds a object to registered objects.
     * @param object The object to add.
     */
    protected void addGameObject(GObject object) {
        /* hinzufügen, wenn nicht schon hinzugefügt */
        if (!gameObjects.contains(object))
            gameObjects.add(object);

        /* Event aufrufen */
        object.onGetAddedToGame(this);
    }

    /**
     * Removes a object from registered objects.
     * @param object The object to remove.
     */
    protected void removeObject(GObject object) {
        /* solange entfernen, bis nicht mehr enthalten */
        while (gameObjects.contains(object))
            gameObjects.remove(object);

        /* Event aufrufen */
        object.onGetsRemovedFromGame(this);
    }

    /**
     * Gets all game objects of a specific type.
     * @param clazz The class of the object.
     * @param <T> The type of the object.
     * @return Returns collection of all registered objects.
     */
    public <T extends GObject> Set<T> getGameObjects(Class<T> clazz) {
        Set<T> objects = new HashSet<>();

        for (GObject object : getGameObjects()) {

            /* ermitteln, ob eine instanz des gesuchten */
            if (clazz.isInstance(object)) {
                // ist instanz -> hinzufügen

                objects.add((T) object);
            }
        }

        return Collections.unmodifiableSet(objects);
    }

    public Set<GObject> getGameObjects() {
        return Collections.unmodifiableSet(gameObjects);
    }

    /**
     * Selects random object which was registered.
     * @param clazz The class of the object.
     * @param <T> The type of the object.
     * @return Returns selected object.
     */
    public <T extends GObject> T getRandomGameObject(Class<T> clazz) {
        return getRandomElement(getGameObjects(clazz));
    }

    /**
     * Selects a random element from a set.
     * @param set The set.
     * @param <T> The type of the element.
     * @return The element.
     */
    private static <T> T getRandomElement(Set<T> set) {
        /* Irgend eine Nummer lesen */
        Random random = new Random();
        int randomNumber = random.nextInt(set.size());

        /* so lange iterieren, bis zähler auf nummer ist */
        int index = 0;

        for (T element : set) {

            /* überprüfen, ob zahl erreicht wurde */
            if (index == randomNumber)
                // wurde erreicht -> element ausgeben
                return element;

            index++;
        }

        return null; // Standardausgabe
    }

    public static enum StartAction {
        COMMAND, PLAYER_JOINED, PLUGIN
    }

}
