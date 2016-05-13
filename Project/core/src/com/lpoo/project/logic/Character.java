package com.lpoo.project.logic;

/**
 * Created by Vasco on 10/05/2016.
 */
public class Character extends Entity {

    private Stats stats;

    /**
     * @brief Constructor for the class Character
     * @param x
     * @param y
     * @param health
     * @param resistance
     * @param strength
     */
    public Character( int x, int y, int health, int resistance, int strength ) {

        super(x, y);
        stats = new Stats( health, resistance, strength );
    }

    /**
     * Getter for stats
     * @return character's current stats
     */
    public Stats getStats() {
        return stats;
    }
}
