package com.lpoo.project.logic;

import java.util.LinkedList;

/**
 * Class that creates the game's traps
 * This class extends the superclass Entity and implements the interfaces Updatable
 */
public class Trap extends Entity implements Updatable {

    /**
     * Enumeration that creates the trap's status
     */
    public enum TrapStatus { WAIT, HEATUP, ATTACK, RECHARGE };
    /**
     * Trap's status
     */
    private TrapStatus currStatus;
    /**
     * Trap's properties
     */
    private Stats stats;

    private int nAttacks;
    private float attackSpeed, rechargeSpeed, heatUpSpeed, timeAttack;
    private float stateTime;

    /**
     * Constructor for the class Trap
     * @param game Game where will be placed the trap
     * @param x Trap's x position
     * @param y Trap's y position
     * @param width Trap's width
     * @param height Trap's height
     * @param damage Trap's damage
     */
    public Trap ( Game game, int x, int y, int width, int height, int damage ) {
        super( game, x, y, width, height );
        currStatus = TrapStatus.WAIT;
        this.stats = new Stats(0,0,0,damage,0);
        attackSpeed = 1f;
        rechargeSpeed = 3f;
        heatUpSpeed = 1/10f;
        nAttacks = 1;
        timeAttack = attackSpeed / 3;
    }

    /**
     * Getter for the current trap's status
     * @return
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
                float aT = timeAttack * nAttacks;
                if( stateTime <= attackSpeed && tmp >= attackSpeed ) {
                    currStatus = TrapStatus.RECHARGE;
                    stateTime = 0.0f;
                    nAttacks = 1;
                    return ;
                }
                else if( stateTime <= aT && tmp >= aT ) {
                    nAttacks++;
                    collision();
                }

                stateTime = tmp;
                break;
            case RECHARGE:
                if( stateTime <= rechargeSpeed && tmp >= rechargeSpeed ) {
                    currStatus = TrapStatus.WAIT;
                    stateTime = 0.0f;
                    return ;
                }

                stateTime = tmp;
                break;
            case WAIT:
                stateTime = tmp;
                collision();
                break;
            case HEATUP:
                if( stateTime <= heatUpSpeed && tmp >= heatUpSpeed ) {
                    currStatus = TrapStatus.ATTACK;
                    stateTime = 0.0f;
                    return ;
                }

                stateTime = tmp;
                break;
        }
    }

    /**
     * Function which analyzes the collisions between the trap and the enemies
     */
    public void collision( ) {
        LinkedList<Enemy> enemies = game.getEnemies();
        for( Enemy e : enemies ) {
            if( currStatus == TrapStatus.ATTACK && rect.overlaps(e.getRect()) )
                e.hit(stats);
            else if( currStatus == TrapStatus.WAIT && e.getRect().getX() >= rect.getX() &&
                    e.getRect().getX() + e.getRect().getWidth() <= rect.getX() + rect.getWidth() ) {
                //In heatUpMode the trap doesn't attack, it just gets ready to attack
                currStatus = TrapStatus.HEATUP;
                stateTime = 0;
                return;
            }
        }
    }
}
