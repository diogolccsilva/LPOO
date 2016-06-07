package com.lpoo.project.logic;

import com.badlogic.gdx.math.Vector2;
import java.util.LinkedList;

/**
 * Class that creates the game's projectiles
 * This class extends the superclass Entity and it implements the Updatable and Movable interfaces
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

    /**
     * Boolean that represents the hero's side
     */
    private boolean heroSide;

    /**
     * Projectile's initial position
     */
    private Vector2 initPosition;

    /**
     * Projectile's max range
     */
    private final int maxRange;

    /**
     * Constructor for the class Projectile
     * @param game   Game where the projectile will be placed
     * @param x      Projectile's x position
     * @param y      Projectile's y position
     * @param width  Projectile's width
     * @param height Projectile's height
     * @param damage Projectile's damage
     * @param maxRange Projectile's maximum range
     * @param side   Which side the projectile's on (true if it on the hero's side)
     */
    public Projectile(Game game, float x, float y, int width, int height, int damage, int maxRange, boolean side) {
        super(game, x, y, width, height);
        initPosition = new Vector2(x, y);
        state = ProjectileStatus.TRAVELLING;
        this.stats = new ProjectileStats(damage, 300f);
        heroSide = side;
        this.maxRange = maxRange;
    }

    /**
     * Getter for the projectile's status
     * @return the projectile's status
     */
    public ProjectileStatus getState() {
        return state;
    }

    /**
     * Setter for the projectile's state
     * @param state new value for state
     */
    public void setState(ProjectileStatus state) {
        this.state = state;
    }

    /**
     * Setter for the projectile's heroSide
     * @param heroSide new value for heroSide
     */
    public void setHeroSide(boolean heroSide) {
        this.heroSide = heroSide;
    }

    /**
     * Updates the projectile (movement and collisions)
     * @param delta Difference between the last time of call and the current time
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
     * @param dir   Direction to move
     * @param delta Difference between the last time of call and the current time
     */
    public void move(int dir, float delta) {
        rect.x += dir * stats.getMovSpeed() * delta;
    }
}
