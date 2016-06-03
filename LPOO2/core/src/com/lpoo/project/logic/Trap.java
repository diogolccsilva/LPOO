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
        if( currStatus != TrapStatus.ATTACK)
            collision();
    }

    /**
     * Function which analyzes the collisions between the trap and the enemies
     */
    public void collision( ) {
        LinkedList<Enemy> enemies = game.getEnemies();
        for( Enemy e : enemies ) {
            if(rect.overlaps(e.getRect())) {
                currStatus = TrapStatus.ATTACK;
                e.hit(stats);
                return ;
            }
        }
    }
}
