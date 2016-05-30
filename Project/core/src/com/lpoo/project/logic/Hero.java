package com.lpoo.project.logic;

/**
 * Created by Vasco on 12/05/2016.
 */
public class Hero extends Character {

    public enum HeroStatus { STILL, ATTACK, MOVE_LEFT, MOVE_RIGHT, DEAD }
    private int move_speed, attack_speed;
    private HeroStatus state;
    private HeroStatus nextState;
    private float stateTime;

    /**
     * @brief Constructor for the class Hero
     * @param x
     * @param y
     * @param health
     * @param resistance
     * @param strength
     */
    public Hero( int x, int y, int health, int resistance, int strength )  {
        super( x, y, 45, 88, health, resistance, strength );
    }

    public void update ( float delta ) {
        stateTime += delta;
    }

    public void attack( ) {
        nextState = HeroStatus.ATTACK;
    }

    public void move( int dir ) {
        if( dir < 0 )
            nextState = HeroStatus.MOVE_LEFT;
        else
            nextState = HeroStatus.MOVE_RIGHT;
    }

    public void update( int delta ) {
        int speed = 0;
        if( state == HeroStatus.STILL ) {
            if( nextState != HeroStatus.STILL ) {
                stateTime = 0;
                state = nextState;
                nextState = HeroStatus.STILL;
            }
            return ;
        }

        stateTime += delta;
        switch ( state ) {
            case ATTACK:
                speed = attack_speed;
                break;
            case MOVE_LEFT:
            case MOVE_RIGHT:
                speed = move_speed;
                break;
        }

        if( stateTime > speed ) {
            stateTime = 0;
            state = nextState;
            nextState = HeroStatus.STILL;
        }
    }
}
