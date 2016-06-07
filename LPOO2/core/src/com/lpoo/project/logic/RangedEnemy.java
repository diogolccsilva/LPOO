package com.lpoo.project.logic;

import com.badlogic.gdx.math.Rectangle;

/**
 * Class that creates the enemy of type ranged
 * This class extends the superclass Enemy
 */
public class RangedEnemy extends Enemy {

    /**
     * Ranged's width
     */
    public static final int width = 33;
    /**
     * Ranged's height
     */
    public static final int height = 124;

    /**
     * Ranged's range
     */
    private static final int range = 500;
    /**
     * Rectangle that represents the shot range
     */
    private Rectangle shotRange;

    /**
     * Constructor for the enemy of type ranged
     * @param game Game where the melee will be placed
     * @param x Ranged's x coordinate
     * @param y Ranged's y coordinate
     * @param stats Character's stats
     */
    public RangedEnemy(Game game, int x, int y, CharacterStats stats) {
        super(game, x, y, width, height, stats);
        shotRange = new Rectangle( rect.x + width, rect.y, range, rect.height);
        attack_time = 0.3f;
        points = 10;
    }

    /**
     * Updates the enemy of type ranged and current status
     * @param delta Difference between the last time of call and the current time
     */
    public void update(float delta) {
        stateTime += delta;

        switch (state) {
            case DEAD:
                break;
            case MOVE_RIGHT:
                move( 1, delta );
                break;
            case ATTACK:
                if( stateTime >= stats.getAttSpeed() ) {
                    stateTime -= stats.getAttSpeed();
                    attacked = false;
                } else if( stateTime >= attack_time && !attacked ) {
                    Projectile projectile = new Projectile(game, rect.x + width, rect.y + 10, 10, 10, 5, range, false);
                    game.addProjectile(projectile, false);
                    attacked = true;
                }
                break;
        }

        //Change state
        if (state != EnemyStatus.DEAD) {
            if (game.getHero().getState() != Hero.HeroStatus.DEAD && shotRange.overlaps(game.getHero().getRect()))
                nextState = EnemyStatus.ATTACK;
            else
                nextState = EnemyStatus.MOVE_RIGHT;
        }
    }

    @Override
    /**
     * Represents the way the enemy moves
     * @param dir Movement's direction
     * @param delta Difference between the last time of call and the current time
     */
    public void move(int dir, float delta) {
        float dist = stats.getMovSpeed() * dir * delta;
        rect.x += dist;
        shotRange.x += dist;
    }

}
