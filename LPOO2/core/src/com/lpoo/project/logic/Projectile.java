package com.lpoo.project.logic;

import java.util.LinkedList;

/**
 * Class that creates the game's projectiles
 * This class extends the superclass Entity and implements the interfaces Updatable, Movable
 */
public class Projectile extends Entity implements Updatable, Movable {

    /**
     * Enumeration of the projectile's status
     */
    public enum ProjectileStatus { TRAVELLING, HIT_TARGET}

    /**
     * Projectile's status
     */
    private ProjectileStatus state;

    /**
     * Projectile's properties
     */
    private Stats stats;
    /**
     * Projectile's velocity
     */
    private float velocity;

    /**
     * Constructor for the class Projectile
     * @param game Game where will be placed the projectile
     * @param x Projectile's x position
     * @param y Projectile's y position
     * @param width Projectile's width
     * @param height Projectile's height
     * @param damage Projectile's damage
     */
    public Projectile( Game game, float x, float y, int width, int height, int damage ) {
        super(game, x, y, width, height);
        state = ProjectileStatus.TRAVELLING;
        this.stats = new Stats(100,0,0,damage,0);
        velocity = 100f;
    }

    /**
     * Getter for the projectile's status
     * @return the projectile's status
     */
    public ProjectileStatus getState() {
        return state;
    }

    /**
     * Updates the projectile (movement and collisions)
     * @param delta Increasing values
     */
    public void update( float delta ) {
        if( state != ProjectileStatus.HIT_TARGET) {
            move( -1, delta );
            collision();
        }
    }

    /**
     * Function which treats the collisions between the projectiles and the enemies
     */
    public void collision( ) {
        LinkedList<Enemy> enemies = game.getEnemies();
        for( Enemy e : enemies ) {
            if(rect.overlaps(e.getRect())) {
                state = ProjectileStatus.HIT_TARGET;
                e.hit(stats);
                return ;
            }
        }
    }

    /**
     * Function that moves the projectile
     * @param dir Direction to move
     * @param delta Increasing value
     */
    public void move( int dir, float delta ) {
        rect.x += dir * velocity * delta;
    }
}
