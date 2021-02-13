package me.langner.jonas.game.objects;

import com.sun.istack.internal.NotNull;
import org.bukkit.entity.Entity;

/**
 * Handles entities for the game.
 * @author Jonas Langner
 * @version Alpha
 * @since 12.02.2021 (Alpha)
 */
public abstract class GEntity extends GObject {

    private Entity entity;

    /**
     * Creates a new entity.
     * @param game The game of the entity.
     * @param entity The bukkit entity.
     */
    public GEntity(@NotNull Game game, Entity entity) {
        super(game);
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }
}
