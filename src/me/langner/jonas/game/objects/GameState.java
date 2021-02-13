package me.langner.jonas.game.objects;

/**
 * Handles different states of a game.
 * @author Jonas Langner
 * @version Alpha
 * @since 12.02.2021 (Alpha)
 */
public class GameState implements Comparable<GameState> {

    private String name;
    private int position;

    /**
     * Creates a new state of the game.
     * @param name The name of the state.
     * @param stateIndex The position of the state.
     */
    public GameState(String name, int stateIndex) {
        this.name = name;
        this.position = stateIndex;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public int compareTo(GameState other) {
        if (this.position < other.position)
            return -1;
        else if (this.position > other.position)
            return 1;

        return 0;
    }
}
