package com.lpoo.project.logic;

/**
 * Created by Vasco on 27/05/2016.
 */
public class Trap extends Entity {

    public enum TrapStatus { WAIT, ATTACK, RECHARGE };
    private TrapStatus currStatus;

    public Trap ( int x, int y ) {
        super( x, y );
    }
}
