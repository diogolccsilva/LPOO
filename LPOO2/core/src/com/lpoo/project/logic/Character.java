package com.lpoo.project.logic;

/**
 * Class that creates the game's characters
 * This class extends the superclass Entity
 */
public class Character extends Entity {

    /**
     * Maximum velocity per second
     */
    protected static final int max_velocity = 20;

    /**
     * Character's properties
     */
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
     * Getter for stats
     * @return character's current stats
     */
    public Stats getStats() {
        return stats;
    }

    /**
     * Getter for velocity
     * @return character's velocity
     */
    public float getVelocity() {
        return stats.getMovSpeed();
    }
}
