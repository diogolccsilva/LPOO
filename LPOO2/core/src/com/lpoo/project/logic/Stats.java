package com.lpoo.project.logic;

/**
 * Class that creates objects of type Stats (properties of a character)
 */
public abstract class Stats {

    public Stats() {

    }

    public int getAttDamage() {
        return 0;
    }

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
