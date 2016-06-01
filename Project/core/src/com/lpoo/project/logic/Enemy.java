package com.lpoo.project.logic;


/**
 * Created by Vasco on 12/05/2016.
 */
public class Enemy extends Character {

    public enum EnemyStatus { ATTACK, MOVE_RIGHT, DEAD }
    private float move_speed, attack_speed;
    private EnemyStatus state, nextState;
    private float stateTime;

    /**
     * @brief Constructor for the class Enemy
     * @param x
     * @param y
     * @param health
     * @param resistance
     * @param strength
     */
    public Enemy( int x, int y, int health, int resistance, int strength )  {
        super( x, y, 70, 124, health, resistance, strength );
        stateTime = 0;
        state = EnemyStatus.MOVE_RIGHT;
        nextState = EnemyStatus.MOVE_RIGHT;
        move_speed = 1/3f;
        attack_speed = 1/5f;
    }

    public float getSpeed( EnemyStatus stat ) {
        switch(stat) {
            case ATTACK:
                return attack_speed;
            case MOVE_RIGHT:
                return move_speed;
        }
        return 0;
    }

    public EnemyStatus getState() {
        return state;
    }

    public EnemyStatus getNextState () {
        return nextState;
    }

    public void AnimationStatus( EnemyStatus stat ) {
        if( stat != state) {
            nextState = stat;
            state = stat;
            stateTime = 0;
        }
    }

    public void update( float delta, Hero hero ) {
        stateTime += delta;

        //Change state
        if( state != EnemyStatus.DEAD /* && stateTime >= getSpeed( state )*/ ) {
            if ( rect.overlaps( hero.getRect()) )
                nextState = EnemyStatus.ATTACK;
            else
                nextState = EnemyStatus.MOVE_RIGHT;
        }

        if( state == EnemyStatus.MOVE_RIGHT )
            rect.x += 30 * delta;
    }

    public void bulletHit( Projectile p ) {
        stats.setHealth(p.getDamage());
        if(stats.getHealth()<=0)
            state = EnemyStatus.DEAD;
    }

    public void setHealth(int health) {

    }
}
