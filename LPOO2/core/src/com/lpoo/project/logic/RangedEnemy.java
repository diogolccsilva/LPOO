package com.lpoo.project.logic;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Vasco on 05/06/2016.
 */
public class RangedEnemy extends Enemy {

    public static final int width = 60;
    public static final int height = 124;

    private static final int range = 500;
    private Rectangle shotRange;

    public RangedEnemy(Game game, int x, int y, int health, int resistance, int damage) {
        super(game, x, y, width, height, health, resistance, damage);
        shotRange = new Rectangle( rect.x + rect.width / 2 - 5, rect.y, range, rect.height);
        attack_time = 0.3f;
        points = 10;
        stats.setAttSpeed(0.6f);
    }

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
                    stateTime = 0;
                    attacked = false;
                } else if( stateTime >= attack_time && !attacked ) {
                    Projectile projectile = new Projectile(game, rect.x + rect.width / 2 - 5, rect.y + 10, 10, 10, 5, range, false);
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
     * @param delta Increasing value
     */
    public void move(int dir, float delta) {
        float dist = stats.getMovSpeed() * dir * delta;
        rect.x += dist;
        shotRange.x += dist;
    }

}
