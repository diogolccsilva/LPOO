package com.lpoo.project.logic;

/**
 * Interface that changes the direction of a character
 */
public interface Movable {

    /**
     * Changes the direction
     * Entities that move can only do so to the left or right
     * @param dir Direction to move
     * @param delta Difference between the last time of call and the current time
     */
    void move( int dir, float delta );
}
