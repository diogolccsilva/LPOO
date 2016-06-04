package com.lpoo.project.logic;

/**
 * Interface that represents the possibility of a character being hit
 */
public interface Hitable {

    /**
     * Possibility of the character being hit
     * @param stats Character's properties
     */
    void hit(Stats stats);
}
