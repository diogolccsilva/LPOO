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
    protected int width = 82, height = 124;

    /**
     * @brief Constructor for the class Character
     * @param name
     * @param x
     * @param y
     * @param health
     * @param resistance
     * @param strength
     */
    public Character(String name, int x, int y, int health, int resistance, int strength ) {

        super(name, x, y);
        stats = new Stats( health, resistance, strength );
    }

    /**
     * @brief Constructor for the class Character
     * @param x
     * @param y
     * @param stats
     */
    public Character(String name, int x, int y, Stats stats) {

        super(name, x, y);
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

    public boolean collision(Rectangle rect){
        return position.x < rect.x + rect.width && position.x + width > rect.x && position.y < rect.y + rect.height && position.y + height > rect.y;
    }
}
