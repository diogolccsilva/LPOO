package com.lpoo.project.logic;

/**
 * Created by Vasco on 05/06/2016.
 */
public class MeleeEnemy extends Enemy {

    public MeleeEnemy(Game game, int x, int y, int health, int resistance, int damage) {
        super(game, x, y, health, resistance, damage);
    }

    public void update(float delta) {
        stateTime += delta;

        switch( state ) {
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
                    attacked = true;
                    game.getHero().hit(stats);
                }
                break;
        }

        //Change state
        if( state != EnemyStatus.DEAD ) {
            if ( game.getHero().getState() != Hero.HeroStatus.DEAD && rect.overlaps( game.getHero().getRect()) )
                nextState = EnemyStatus.ATTACK;
            else
                nextState = EnemyStatus.MOVE_RIGHT;
        }
    }
}
