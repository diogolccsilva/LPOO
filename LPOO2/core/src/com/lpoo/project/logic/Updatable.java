package com.lpoo.project.logic;

/**
 * Interface that allows to update a entity
 */
public interface Updatable {
    /**
     * Updates a entity
     * @param delta Difference between the last time of call and the current time
     */
    void update( float delta );
}
