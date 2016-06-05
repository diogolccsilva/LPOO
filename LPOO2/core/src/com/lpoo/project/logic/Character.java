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
    protected CharacterStats stats;

    /**
     * Constructor for the class Constructor
     * @param game Game where will be placed the character
     * @param x Character's x position
     * @param y Character's y position
     * @param width Character's width
     * @param height Character's height
     * @param stats Character's properties
     */
    public Character(Game game, int x, int y, int width, int height, CharacterStats stats) {
        super(game, x, y, width, height);
        this.stats = stats;
    }

    /**
     * Constructor for the class Constructor
     * @param game Game where will be placed the character
     * @param x Character's x position
     * @param y Character's y position
     * @param width Character's width
     * @param height Character's height
     */
    public Character(Game game, int x, int y, int width, int height) {
        super(game, x, y, width, height);
    }

    /**
     * Getter for stats
     * @return character's current stats
     */
    public CharacterStats getStats() {
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
