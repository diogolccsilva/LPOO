package com.lpoo.project.logic;

import com.badlogic.gdx.math.Vector2;

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
    public enum ProjectileStatus { TRAVELLING, HIT_TARGET }

    /**
     * Projectile's status
     */
    private ProjectileStatus state;

    /**
     * Projectile's properties
     */
    private ProjectileStats stats;

    private boolean heroSide;

    private Vector2 initPosition;

    private final int maxRange;

    /**
     * Constructor for the class Projectile
     *
     * @param game   Game where will be placed the projectile
     * @param x      Projectile's x position
     * @param y      Projectile's y position
     * @param width  Projectile's width
     * @param height Projectile's height
     * @param damage Projectile's damage
     */
    public Projectile(Game game, float x, float y, int width, int height, int damage, int maxRange, boolean side) {
        super(game, x, y, width, height);
        initPosition = new Vector2(x, y);
        state = ProjectileStatus.TRAVELLING;
        this.stats = new ProjectileStats(damage, 150f);
        heroSide = side;
        this.maxRange = maxRange;
    }

    /**
     * Getter for the projectile's status
     *
     * @return the projectile's status
     */
    public ProjectileStatus getState() {
        return state;
    }

    /**
     * Updates the projectile (movement and collisions)
     *
     * @param delta Increasing values
     */
    public void update(float delta) {
        //The projectiles move horizontally so the only difference will only be in x
        if (!heroSide && rect.x - initPosition.x >= maxRange ||
                heroSide && initPosition.x - rect.x >= maxRange)
            state = ProjectileStatus.HIT_TARGET;
        else if (state != ProjectileStatus.HIT_TARGET) {
            move(heroSide ? -1 : 1, delta);
            collision();
        }
    }

    /**
     * Function which treats the collisions between the projectiles and the enemies
     */
    public void collision() {
        if (heroSide) {
            LinkedList<Enemy> enemies = game.getEnemies();
            for (Enemy e : enemies) {
                if (rect.overlaps(e.getRect())) {
                 /* Random rand = new Random();
                    if (rand.nextInt(100)>=stats.getPenetration()) {
                        state = ProjectileStatus.HIT_TARGET;
                }*/
                    state = ProjectileStatus.HIT_TARGET;
                    e.hit(stats);
                    return;
                }
            }
        } else {
            Hero hero = game.getHero();
            if( hero.getState() != Hero.HeroStatus.DEAD && rect.overlaps(hero.getRect()) ) {
                state = ProjectileStatus.HIT_TARGET;
                hero.hit(stats);
            }
        }
    }

    /**
     * Function that moves the projectile
     *
     * @param dir   Direction to move
     * @param delta Increasing value
     */
    public void move(int dir, float delta) {
        rect.x += dir * stats.getMovSpeed() * delta;
    }

    public void reset( int x, int y, int width, int height, boolean side ) {
        super.reset( x, y, width, height );
        if( heroSide != side ) {
            stats.movSpeed *= -1;
            heroSide = side;
        }
        state = ProjectileStatus.TRAVELLING;
        initPosition.x = x;
        initPosition.y = y;
    }

    /**
     * Clones the hero's animation
     */
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
