package com.lpoo.project.logic;

/**
 * Class that creates objects of type Stats (properties of a character)
 */
public abstract class Stats {

    /**
     * Constructor for the class Stats
     */
    public Stats() {

    }

    /**
     * Getter for the damage
     * @return The damage
     */
    public int getAttDamage() {
        return 0;
    }

    /**
     * Getter for the movement speed
     * @return The movement speed
     */
    public float getMovSpeed() {
        return 0;
    }

    @Override
    /**
     * Creates a string with the character's properties
     */
    public String toString() {
        return super.toString();
    }
}
