package com.lpoo.project.logic;

/**
 * Created by Vasco on 14/05/2016.
 */
public interface Movable {

    /**
     * @brief Changes the direction. Entities that move can only do so to the left or right
     * @param dir
     */
    void move( int dir, float delta );
}
