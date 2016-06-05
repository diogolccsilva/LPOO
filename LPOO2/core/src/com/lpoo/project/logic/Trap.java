package com.lpoo.project.logic;

import java.util.LinkedList;

/**
 * Class that creates the game's traps
 * This class extends the superclass Entity and it implements the interfaces Updatable
 */
public class Trap extends Entity implements Updatable {

    /**
     * Enumeration that creates the trap's status
     */
    public enum TrapStatus {
        WAIT, HEATUP, ATTACK, RECHARGE
    }

    ;
    /**
     * Trap's status
     */
    private TrapStatus currStatus;

    /**
     * Trap's properties
     */
    private TrapStats stats;

    /**
     * Trap's number of attacks
     */
    private int nAttacks;

    /**
     * Trap's status' "time of life"
     */
    private float stateTime;

    /**
     * Constructor for the class Trap
     *
     * @param game   Game where will be placed the trap
     * @param x      Trap's x position
     * @param y      Trap's y position
     * @param width  Trap's width
     * @param height Trap's height
     * @param damage Trap's damage
     */
    public Trap(Game game, int x, int y, int width, int height, int damage) {
        super(game, x, y, width, height);
        currStatus = TrapStatus.WAIT;
        float attackSpeed = 1f;
        float rechargeSpeed = 3f;
        float heatUpSpeed = 1 / 10f;
        nAttacks = 1;
        float timeAttack = attackSpeed / 3f;
        this.stats = new TrapStats(damage,attackSpeed, rechargeSpeed, timeAttack, timeAttack);
    }

    /**
     * Getter for the current trap's status
     *
     * @return Current trap'sstatus
     */
    public TrapStatus getState() {
        return currStatus;
    }

    @Override
    /**
     * Updates the trap and treats the possible collision
     * @param delta Increasing value
     */
    public void update(float delta) {
        float tmp = stateTime + delta;

        switch (currStatus) {
            case ATTACK:
                float aT = stats.getTimeAttack() * nAttacks;
                if (stateTime <= stats.getAttackSpeed() && tmp >= stats.getAttackSpeed()) {
                    currStatus = TrapStatus.RECHARGE;
                    stateTime = 0.0f;
                    nAttacks = 1;
                    return;
                } else if (stateTime <= aT && tmp >= aT) {
                    nAttacks++;
                    collision();
                }

                stateTime = tmp;
                break;
            case RECHARGE:
                if (stateTime <= stats.getRechargeSpeed() && tmp >= stats.getRechargeSpeed()) {
                    currStatus = TrapStatus.WAIT;
                    stateTime = 0.0f;
                    return;
                }

                stateTime = tmp;
                break;
            case WAIT:
                stateTime = tmp;
                collision();
                break;
            case HEATUP:
                if (stateTime <= stats.getHeatUpSpeed() && tmp >= stats.getHeatUpSpeed()) {
                    currStatus = TrapStatus.ATTACK;
                    stateTime = 0.0f;
                    return;
                }

                stateTime = tmp;
                break;
        }
    }

    /**
     * Function which analyzes the collisions between the trap and the enemies
     */
    public void collision() {
        LinkedList<Enemy> enemies = game.getEnemies();
        for (Enemy e : enemies) {
            if (currStatus == TrapStatus.ATTACK && rect.overlaps(e.getRect()))
                e.hit(stats);
            else if (currStatus == TrapStatus.WAIT && e.getRect().getX() >= rect.getX() &&
                    e.getRect().getX() + e.getRect().getWidth() <= rect.getX() + rect.getWidth()) {
                //In heatUpMode the trap doesn't attack, it just gets ready to attack
                currStatus = TrapStatus.HEATUP;
                stateTime = 0;
                return;
            }
        }
    }
}
