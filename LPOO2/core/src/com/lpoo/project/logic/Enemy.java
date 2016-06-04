package com.lpoo.project.logic;


/**
 * Class that creates the enemies
 * This class extends the superclass Character and it implements the Updatable, Movable and Hitable interfaces
 */
public class Enemy extends Character implements Updatable, Movable, Hitable {

    /**
     * Enumeration for the enemy's status
     */
    public enum EnemyStatus { ATTACK, MOVE_RIGHT, DEAD }

    /**
     * Enemy's current status
     */
    private EnemyStatus state;
    /**
     * Enemy's next status
     */
    private EnemyStatus nextState;
    /**
     * Status "time of life"
     */
    private float stateTime;

    /**
     * Moving speed
     */
    private float move_speed;
    /**
     * Attack's speed
     */
    private float attack_speed;
    /**
     * Attack's time
     */
    private float attack_time;
    /**
     * Enemy's velocity
     */
    private float velocity;
    /**
     * Boolean which represents if the enemy was attacked or not
     */
    private boolean attacked;

    /**
     * Constructor for the class Enemy
     * @param game Game where the enemy will be placed
     * @param x Enemy's x position
     * @param y Enemy's y position
     * @param health Enemy's health
     * @param resistance Enemy's resistance
     * @param strength Enemy's strength
     */
    public Enemy( Game game, int x, int y, int health, int resistance, int strength )  {
        super( game, x, y, 80, 124, health, resistance, strength );
        stateTime = 0;
        state = EnemyStatus.MOVE_RIGHT;
        nextState = EnemyStatus.MOVE_RIGHT;
        move_speed = 1/4f;
        attack_speed = 1.4f;
        attack_time = 0.6f;
        attacked = false;
        velocity = 50f;
    }

    /**
     * Getter for the status
     * @return enemy's currents status
     */
    public EnemyStatus getState() {
        return state;
    }

    /**
     * Getter for the status
     * @return enemy's next status
     */
    public EnemyStatus getNextState () {
        return nextState;
    }

    /**
     * Function which represents the enemy's status' animation
     * @param stat Enemy's status
     */
    public void AnimationStatus( EnemyStatus stat ) {
        if( stat != state ) {
            nextState = stat;
            state = stat;
            stateTime = 0;
        }
    }

    @Override
    /**
     * Updates the enemy and current status
     */
    public void update(float delta) {
        stateTime += delta;

        switch( state ) {
            case DEAD:
                break;
            case MOVE_RIGHT:
                move( 1, delta );
                break;
            case ATTACK:
                if( stateTime >= attack_speed ) {
                    stateTime -= attack_speed;
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

    /**
     * Verifies if the enemy was hit by a projectile and if its life is 0 or less the enemy dies
     * @param stats Enemy's properties
     */
    public void hit(Stats stats) {
        this.stats.applyDamage(stats);
        if(this.stats.getHealth()<=0) {
            state = EnemyStatus.DEAD;
            nextState = EnemyStatus.DEAD;
        }
    }

    @Override
    /**
     * Represents the way the enemy moves
     * @param dir Movement's direction
     * @param delta Increasing value
     */
    public void move(int dir, float delta) {
        rect.x += velocity * dir * delta;
    }
}
