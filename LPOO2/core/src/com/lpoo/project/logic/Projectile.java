package com.lpoo.project.logic;

import java.util.LinkedList;
import java.util.Random;

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
     *
     * @param game
     * @param x
     * @param y
     * @param width
     * @param height
     * @param damage
     */
    public Projectile( Game game, float x, float y, int width, int height, int damage ) {
        super(game, x, y, width, height);
        state = ProjectileStatus.TRAVELLING;
        this.stats = new Stats(100,10,100f,0,damage);
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
                Random rand = new Random();
                /*if (rand.nextInt(100)>=stats.getPenetration()) {
                    state = ProjectileStatus.HIT_TARGET;
                }*/
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
        rect.x += dir * stats.getMovSpeed() * delta;
    }
}
