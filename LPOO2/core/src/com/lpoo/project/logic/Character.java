package com.lpoo.project.logic;

import java.awt.Rectangle;

/**
 * Created by Vasco on 10/05/2016.
 */
public class Character extends Entity {

    /**
     * @brief Maximum velocity per second
     */
    protected static final int max_velocity = 20;

    protected Stats stats;

    /**
     *
     * @param game
     * @param x
     * @param y
     * @param width
     * @param height
     * @param stats
     */
    public Character(Game game, int x, int y, int width, int height, Stats stats) {
        super(game, x, y, width, height);
        this.stats = stats;
    }

    /**
     *
     * @param game
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public Character(Game game, int x, int y, int width, int height) {
        super(game, x, y, width, height);
    }

    /**
     * @return character's current stats
     * @brief Getter for stats
     */
    public Stats getStats() {
        return stats;
    }

    /**
     * @return character's velocity
     * @brief Getter for velocity
     */
    public float getVelocity() {
        return stats.getVelocity();
    }
}
