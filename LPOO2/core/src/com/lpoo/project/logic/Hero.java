package com.lpoo.project.logic;

/**
 * Class that creates the heroes
 * This class extends the superclass Character and it implements the Updatable, Movable and Hitable interfaces
 */
public class Hero extends Character {

    /**
     * Enumeration for the heros status
     */
    public enum HeroStatus {
        STILL, ATTACK, MOVE_LEFT, MOVE_RIGHT, DEAD
    }

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

    private float deadTime = 3f;

    /**
     * Constructor for the class Hero
     *
     * @param game       Game where will be placed the hero
     * @param x          Hero's x position
     * @param y          Hero's y position
     * @param health     Hero's health
     * @param resistance Hero's resistance
     * @param damage     Hero's damage
     */
    public Hero(Game game, int x, int y, int health, int resistance, int damage) {
        super(game, x, y, 45, 88);
        state = HeroStatus.STILL;
        nextState = state;
        float movSpeed = 80f;
        float attSpeed = 0.7f;
        this.stats = new CharacterStats(health, resistance, movSpeed, attSpeed, damage);
        this.game = game;
    }

    /**
     *
     * @param game
     * @param x
     * @param y
     * @param stats
     */
    public Hero(Game game, int x, int y, CharacterStats stats){
        super(game,x,y,45,88);
        this.stats = stats;
        state = HeroStatus.STILL;
        nextState = state;
    }

    /**
     * Getter for the hero's current status
     *
     * @return the hero's current status
     */
    public HeroStatus getState() {
        return state;
    }

    /**
     * Getter for the hero's next status
     *
     * @return the hero's next status
     */
    public HeroStatus getNextState() {
        return nextState;
    }

    /**
     * Function which allows the hero to move
     *
     * @param state New status to be saved in the nextState variable
     */
    public void move(HeroStatus state) {
        if( this.state != HeroStatus.DEAD )
            nextState = state;
    }

    /**
     * Function which allows the hero to attack
     */
    public void attack() {
        if (state != HeroStatus.DEAD)
            nextState = HeroStatus.ATTACK;
    }

    /**
     * @param screenX
     */
    public void touchDown(float screenX) {
        if (state == HeroStatus.DEAD)
            return;
        if (screenX < 50)
            nextState = HeroStatus.MOVE_LEFT;
        else if (screenX > 840)
            nextState = HeroStatus.MOVE_RIGHT;
        else
            nextState = HeroStatus.ATTACK;
    }

    /**
     *
     */
    public void touchUp() {
        if (state != HeroStatus.DEAD)
            nextState = HeroStatus.STILL;
    }

    /**
     * Function which represents the hero's status' animation
     *
     * @param stat hero's status
     */
    public void animationStatus(HeroStatus stat) {
        if (state != HeroStatus.DEAD && stat != state) {
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

        switch (state) {
            case ATTACK:
                if (currTime >= stats.getAttSpeed()) {
                    Projectile projectile = new Projectile(game, rect.x, rect.y + 46, 10, 3, getStats().getAttDamage(), 550, true);
                    game.addProjectile(projectile, true);
                    currTime -= stats.getAttSpeed();
                }
                break;
            case MOVE_LEFT:
                move(-1, delta);
                break;
            case MOVE_RIGHT:
                move(1, delta);
                break;
            case DEAD:
                if( currTime >= deadTime ) {
                    stateTime = 0;
                    rect.x = 3700;
                    state = HeroStatus.STILL;
                    nextState = HeroStatus.STILL;
                    stats.setHealth(stats.getMaxHealth());
                    return;
                }
                break;
        }
        stateTime = currTime;
    }

    /**
     * Verifies if the hero was hit by a enemy and if its life is 0 or less the enemy dies
     *
     * @param stats Hero's properties
     */
    public void hit(Stats stats) {
        this.stats.applyDamage(stats);
        if (this.stats.getHealth() <= 0) {
            this.stats.setHealth(0);
            stateTime = 0;
            state = HeroStatus.DEAD;
            nextState = HeroStatus.DEAD;
        }
    }

    /**
     * Allows the hero to move
     *
     * @param dir   Movement's direction
     * @param delta Increasing value
     */
    public void move(int dir, float delta) {
        float x = rect.x + getVelocity() * delta * dir;
        if (x >= 200 && x <= 3700)
            rect.x += getVelocity() * delta * dir;
    }

    @Override
    /**
     * Creates a string with the hero's properties
     */
    public String toString() {
        return "Hero [stats=" + stats + "]";
    }

    public void reset( int x, int y, int health, int resistance, int damage ) {
        super.reset( x, y, 45, 88 );
        stats.setHealth(health);
        stats.setMaxHealth(health);
        stats.setResistance(resistance);
        stats.setAttDamage(damage);
        stateTime = 0;
        state = HeroStatus.STILL;
        nextState = HeroStatus.STILL;
    }

    /**
     * Clones the hero's animation
     */
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
