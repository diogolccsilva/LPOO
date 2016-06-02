package com.lpoo.project.logic;

/**
 * Created by Vasco on 27/05/2016.
 */
public class Trap extends Entity {

    public enum TrapStatus { WAIT, ATTACK, RECHARGE };
    private TrapStatus currStatus;

    public Trap ( Game game, int x, int y, int width, int height ) {
        super( game, x, y, width, height );
        currStatus = TrapStatus.WAIT;
    }
}
