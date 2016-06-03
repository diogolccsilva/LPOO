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
    protected float velocity;

    /**
     * @param x
     * @param y
     * @param health
     * @param resistance
     * @param strength
     * @brief Constructor for the class Character
     */
    public Character(Game game, int x, int y, int width, int height, int health, int resistance, int strength) {
        super(game, x, y, width, height);
        stats = new Stats(health, resistance, strength,1,0);
    }

    /**
     * @param x
     * @param y
     * @param stats
     * @brief Constructor for the class Character
     */
    public Character(Game game, int x, int y, int width, int height, Stats stats) {
        super(game, x, y, width, height);
        this.stats = stats;
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
        return velocity;
    }
}
