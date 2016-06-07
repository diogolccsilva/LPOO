package com.lpoo.project.logic;

/**
 * Class that creates the enemy of type melee
 * This class extends the superclass Enemy
 */
public class MeleeEnemy extends Enemy {

    /**
     * Melee's width
     */
    public static final int width = 80;
    /**
     * Melee's height
     */
    public static final int height = 124;

    /**
     * Constructor for the enemy of type melee
     * @param game Game where the melee will be placed
     * @param x Melee's x coordinate
     * @param y Melee's y coordinate
     * @param health Melee's health
     * @param resistance Melee's resistance
     * @param damage Melee's damage
     */
    public MeleeEnemy(Game game, int x, int y, int health, int resistance, int damage) {
        super(game, x, y, width, height, health, resistance, damage);
        points = 5;
        attack_time = 0.4f;
        stats.setAttSpeed(1.4f);
    }

    /**
     * Updates the enemy of type melee and current status
     * @param delta Difference between the last time of call and the current time
     */
    public void update(float delta) {
        stateTime += delta;

        switch (state) {
            case DEAD:
                break;
            case MOVE_RIGHT:
                move(1, delta);
                break;
            case ATTACK:
                if (stateTime >= attack_time && !attacked) {
                    attacked = true;
                    if (rect.overlaps(game.getHero().getRect()))
                        game.getHero().hit(stats);
                } else if (stateTime >= stats.getAttSpeed()) {
                    stateTime -= stats.getAttSpeed();
                    attacked = false;
                }
                break;
        }

        //Change state
        if (state != EnemyStatus.DEAD) {
            if (game.getHero().getState() != Hero.HeroStatus.DEAD && rect.overlaps(game.getHero().getRect()))
                nextState = EnemyStatus.ATTACK;
            else
                nextState = EnemyStatus.MOVE_RIGHT;
        }
    }
}
