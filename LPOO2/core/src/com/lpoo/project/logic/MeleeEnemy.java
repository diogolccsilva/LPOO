package com.lpoo.project.logic;

public class MeleeEnemy extends Enemy {

    public static final int width = 80;
    public static final int height = 124;

    public MeleeEnemy(Game game, int x, int y, int health, int resistance, int damage) {
        super(game, x, y, width, height, health, resistance, damage);
        points = 5;
        attack_time = 0.4f;
        stats.setAttSpeed(1.4f);
    }

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
