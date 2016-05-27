package com.lpoo.project.logic;

import java.awt.Rectangle;

/**
 * Created by Vasco on 10/05/2016.
 */
public class Character extends Entity implements Move {

    /**
     * @brief Maximum velocity per second
     */
    protected static final int max_velocity = 20;

    private Stats stats;
    protected float velocity;

    /**
     * @brief Constructor for the class Character
     * @param x
     * @param y
     * @param health
     * @param resistance
     * @param strength
     */
    public Character(int x, int y, int width, int height, int health, int resistance, int strength ) {

        super(x, y, width, height);
        stats = new Stats( health, resistance, strength );
    }

    /**
     * @brief Constructor for the class Character
     * @param x
     * @param y
     * @param stats
     */
    public Character(int x, int y, int width, int height, Stats stats) {

        super(x, y, width, height);
        this.stats = stats;
    }

    /**
     * @brief Getter for stats
     * @return character's current stats
     */
    public Stats getStats() {
        return stats;
    }

    /**
     * @brief Getter for velocity
     * @return character's velocity
     */
    public float getVelocity () {
        return velocity;
    }

    @Override
    public void setDir( int dir ) {

        //dir = -1 while moving left, dir = 0 while not moving, dir = 1 while moving
        if( dir < -1 || dir > 1)
            return ;

        //Character's can only move left or right
        velocity = max_velocity * dir;

    }

    @Override
    public void update(float delta) {

        position.x += velocity * delta;
    }
}
