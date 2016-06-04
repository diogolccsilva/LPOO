package com.lpoo.project.logic;

/**
 * Class that creates the heroes
 * This class extends the superclass Character and it implements the Updatable, Movable and Hitable interfaces
 */
public class Hero extends Character implements Updatable, Movable, Hitable {

    /**
     * Enumeration for the heros status
     */
    public enum HeroStatus { STILL, ATTACK, MOVE_LEFT, MOVE_RIGHT, DEAD }
    /**
     * Hero's current status
     */
    private HeroStatus state;
    /**
     * Hero's next status
     */
    private HeroStatus nextState;
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
     * Enemy's velocity
     */
    private float velocity;
    private float deadTime = 3f;
    /**
     * @brief Constructor for the class Hero
     * @param game Game where the hero will be placed
     * @param x x position of the hero
     * @param y y position of the hero
     * @param health Hero's health
     * @param resistance Hero's resistance
     * @param strength Hero's strength
     */
    public Hero( Game game, int x, int y, int health, int resistance, int strength )  {
        super( game, x, y, 45, 88, health, resistance, strength );
        state = HeroStatus.STILL;
        nextState = state;
        move_speed = 1/3f;
        attack_speed = 0.7f;
        velocity = 80f;
        this.game = game;
    }

    /**
     * Getter for the hero's current status
     * @return the hero's current status
     */
    public HeroStatus getState () {
        return state;
    }

    /**
     * Getter for the hero's next status
     * @return the hero's next status
     */
    public HeroStatus getNextState () {
        return nextState;
    }

    /**
     * Function which allows the hero to move
     * @param state New status to be saved in the nextState variable
     */
    public void move(HeroStatus state) {
        if( state != HeroStatus.DEAD )
            nextState = state;
    }

    /**
     * Function which allows the hero to attack
     */
    public void attack() {
        if( state != HeroStatus.DEAD )
            nextState = HeroStatus.ATTACK;
    }

    /**
     *
     * @param screenX
     */
    public void touchDown( float screenX) {
        if( state != HeroStatus.DEAD )
            return ;
        if( screenX < 50 )
            nextState = HeroStatus.MOVE_LEFT;
        else if( screenX > 840 )
            nextState = HeroStatus.MOVE_RIGHT;
        else
            nextState = HeroStatus.ATTACK;
    }

    /**
     *
     */
    public void touchUp( ) {
        if( state != HeroStatus.DEAD )
            nextState = HeroStatus.STILL;
    }

    /**
     * Function which represents the hero's status' animation
     * @param stat hero's status
     */
    public void AnimationStatus( HeroStatus stat ) {
        if( state != HeroStatus.DEAD && stat != state ) {
            nextState = stat;
            state = stat;
            stateTime = 0;
        }
    }

    @Override
    /**
     * Updates the hero and current status
     */
    public void update(float delta) {
        float currTime = stateTime + delta;

        switch( state ) {
            case ATTACK:
                if( currTime >= attack_speed ) {
                    Projectile projectile = new Projectile(game, rect.x, rect.y + 46, 10, 3, 5);
                    game.addProjectile(projectile);
                    currTime -= attack_speed;
                }
                break;
            case MOVE_LEFT:
                move( -1, delta );
                break;
            case MOVE_RIGHT:
                move( 1, delta );
                break;
            case DEAD:
                if( stateTime <= deadTime && currTime >= deadTime ) {
                    state = HeroStatus.STILL;
                    stats.setHealth(stats.getMaxHealth());
                    stateTime = 0;
                    return ;
                }
                break;
        }
        stateTime = currTime;
    }

    /**
     * Verifies if the hero was hit by a enemy and if its life is 0 or less the enemy dies
     * @param stats Hero's properties
     */
    public void hit(Stats stats) {
        this.stats.applyDamage(stats);
        if(this.stats.getHealth()<=0) {
            this.stats.setHealth(0);
            state = HeroStatus.DEAD;
            nextState = HeroStatus.DEAD;
        }
    }

    /**
     * Allows the hero to move
     * @param dir Movement's direction
     * @param delta Increasing value
     */
    public void move( int dir, float delta ) {
        rect.x += velocity * delta * dir;
    }

    @Override
    /**
     * Creates a string with the hero's properties
     */
    public String toString(){
        return "Hero [stats=" + stats + "]";
    }
}
