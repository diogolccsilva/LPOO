package com.lpoo.project.logic;

/**
 * Class that creates the heroes
 * This class extends the superclass Character and it implements the Updatable, Movable and Hitable interfaces
 */
public class Hero extends Character {

    /**
     * Hero's time to be dead
     */
    private static final float deadTime = 3f;

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

    /**
     * Constructor for the class Hero
     * @param game       Game where the hero will be placed
     * @param x          Hero's x position
     * @param y          Hero's y position
     * @param health     Hero's health
     * @param resistance Hero's resistance
     * @param damage     Hero's damage
     */
    /*public Hero(Game game, int x, int y, int health, int resistance, int damage) {
        super(game, x, y, 45, 88);
        state = HeroStatus.STILL;
        nextState = state;
        float movSpeed = 80f;
        float attSpeed = 0.7f;
        this.stats = new CharacterStats(health, resistance, movSpeed, attSpeed, damage);
        this.game = game;
    }*/

    /**
     * Constructor for the class Hero
     *
     * @param game  Game where the hero will be placed
     * @param x     Hero's x position
     * @param y     Hero's y position
     * @param stats Hero's properties
     */
    public Hero(Game game, int x, int y, CharacterStats stats) {
        super(game, x, y, 45, 88);

        this.stats = new CharacterStats(stats.getHealth(), stats.getResistance(), stats.getMovSpeed(), stats.getAttSpeed(), stats.getAttDamage());

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
     * @param dir the direction in which the hero will be moving (negative - Moves left, positive - Moves right)
     */
    public void move(int dir) {
        if (this.state != HeroStatus.DEAD) {
            if (dir < 0)
                nextState = HeroStatus.MOVE_LEFT;
            else
                nextState = HeroStatus.MOVE_RIGHT;
        }
    }

    /**
     * Function which allows the hero to attack
     */
    public void attack() {
        if (state != HeroStatus.DEAD)
            nextState = HeroStatus.ATTACK;
    }

    /**
     * Function that signals that the next state will be STILL
     */
    public void stop() {
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
            state = stat;
            stateTime = 0;
        }
    }

    /**
     * Updates the hero and current status
     *
     * @param delta Difference between the last time of call and the current time
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
                if (currTime >= deadTime) {
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
     * @param delta Difference between the last time of call and the current time
     */
    public void move(int dir, float delta) {
        float x = rect.x + getVelocity() * delta * dir;
        if (x >= 200 && x <= 3800)
            rect.x += getVelocity() * delta * dir;
    }

    @Override
    /**
     * Creates a string with the hero's properties
     */
    public String toString() {
        return "Hero [stats=" + stats + "]";
    }
}
