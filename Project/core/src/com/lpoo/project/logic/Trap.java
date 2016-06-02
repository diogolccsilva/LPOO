package com.lpoo.project.logic;

import java.util.LinkedList;

/**
 * Created by Vasco on 27/05/2016.
 */
public class Trap extends Entity implements Updatable {

    public enum TrapStatus { WAIT, ATTACK, RECHARGE };
    private TrapStatus currStatus;
    private int damage;

    public Trap ( Game game, int x, int y, int width, int height, int damage ) {
        super( game, x, y, width, height );
        currStatus = TrapStatus.WAIT;
        this.damage = damage;
    }

    public int getDamage() { return damage; }

    public TrapStatus getState() {
        return currStatus;
    }

    @Override
    public void update(float delta) {
        if( currStatus != TrapStatus.ATTACK)
            collision();
    }

    public void collision( ) {
        LinkedList<Enemy> enemies = game.getEnemies();
        for( Enemy e : enemies ) {
            if(rect.overlaps(e.getRect())) {
                currStatus = TrapStatus.ATTACK;
                e.hit(damage);
                return ;
            }
        }
    }
}
