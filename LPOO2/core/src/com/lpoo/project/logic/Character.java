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
     * Character's velocity
     */
    protected float velocity;

    /**
     * Constructor for the class Character
     * @param game Game where the character will be placed
     * @param x Character's x position
     * @param y Character's y position
     * @param width Character's width
     * @param height Character's height
     * @param health Character's health
     * @param resistance Character's resistance
     * @param strength Character's strength
     */
    public Character(Game game, int x, int y, int width, int height, int health, int resistance, int strength) {
        super(game, x, y, width, height);
        stats = new Stats(health, resistance, strength,1,0);
    }

    /**
     * Constructor for the class Character
     * @param game Game where the character will be placed
     * @param x Character's x position
     * @param y Character's y position
     * @param width Character's width
     * @param height Character's height
     * @param stats Character's properties
     */
    public Character(Game game, int x, int y, int width, int height, Stats stats) {
        super(game, x, y, width, height);
        this.stats = stats;
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
        return velocity;
    }
}
