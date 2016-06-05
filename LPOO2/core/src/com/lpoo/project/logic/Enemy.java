package com.lpoo.project.logic;


/**
 * Class that creates the enemies
 * This class extends the superclass Character and it implements the Updatable, Movable and Hitable interfaces
 */
public class Enemy extends Character {

    /**
     * Enumeration for the enemy's status
     */
    public enum EnemyStatus { ATTACK, MOVE_RIGHT, DEAD }

    /**
     * Enemy's current status
     */
    protected EnemyStatus state;

    /**
     * Enemy's next status
     */
    protected EnemyStatus nextState;

    /**
     * Status "time of life"
     */
    protected float stateTime;

    /**
     * Attack's time
     */
    protected float attack_time;

    /**
     * Boolean which represents if the enemy was attacked or not
     */
    protected boolean attacked;

    /**
     * Constructor for the class Enemy
     * @param game Game where will be placed the enemy
     * @param x Enemy's x position
     * @param y Enemy's y position
     * @param health Enemy's health
     * @param resistance Enemy's resistance
     * @param damage Enemy's damage
     */
    public Enemy( Game game, int x, int y, int health, int resistance, int damage )  {
        super( game, x, y, 80, 124);
        stateTime = 0;
        state = EnemyStatus.MOVE_RIGHT;
        nextState = EnemyStatus.MOVE_RIGHT;
        attack_time = 0.6f;
        attacked = false;
        stats = new Stats(health, resistance, 40f, 1.4f,damage);
    }

    /**
     * Getter for the status
     * @return enemy's currents status
     */
    public EnemyStatus getState() {
        return state;
    }

    public void setStates( EnemyStatus state ) {
        setState(state);
        setNextState(state);
        stateTime = 0;
    }

    public void setState(EnemyStatus state) {
        this.state = state;
        stateTime = 0;
    }

    public void setNextState(EnemyStatus nextState) {
        this.nextState = nextState;
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
    public void animationStatus( EnemyStatus stat ) {
        if( stat != state ) {
            nextState = stat;
            state = stat;
            stateTime = 0;
        }
    }

    /**
     * Updates the enemy and current status
     */
    public void update(float delta) { }

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
}
