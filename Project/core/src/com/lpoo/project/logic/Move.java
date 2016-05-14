package com.lpoo.project.logic;

/**
 * Created by Vasco on 14/05/2016.
 */
public interface Move {

    /**
     * @brief Changes the direction. Entities that move can only do so to the left or right
     * @param dir
     */
    void setDir( int dir );

    void update( float delta );
}
