package com.lpoo.project.logic;

/**
 * Created by Vasco on 12/05/2016.
 */
public class Enemy extends Character {

    public enum EnemyStatus { ATTACK, MOVE_RIGHT, DEAD }
    private int move_speed, attack_speed;
    private EnemyStatus state;
    private EnemyStatus nextState;
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
        super( x, y, 140, 124, health, resistance, strength );
        stateTime = 0;
        state = EnemyStatus.MOVE_RIGHT;
    }

    public void update( float delta ) {
        stateTime += delta;
        position.x += 30 * delta;
    }
}
