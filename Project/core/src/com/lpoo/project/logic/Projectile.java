package com.lpoo.project.logic;

import java.util.LinkedList;

/**
 * Created by asus1 on 31/05/2016.
 */
public class Projectile extends Entity {

    public enum ProjectileStatus { TRAVELLING, HIT_TRAGET }
    private ProjectileStatus state;
    private int damage, velocity;
    private float stateTime;

    public Projectile( float x, float y, int width, int height, int damage, int velocity ) {
        super(x, y, width, height);
        state = ProjectileStatus.TRAVELLING;
        stateTime = 0;
        this.damage = damage;
        this.velocity = velocity;
    }

    public int getDamage() {
        return damage;
    }

    public ProjectileStatus getState() {
        return state;
    }

    public void update( float delta, LinkedList<Enemy> enemies ) {
        stateTime += delta;
        if( state != ProjectileStatus.HIT_TRAGET) {
            rect.x -= velocity * delta;
            collision(enemies);
        }
    }

    public void collision(LinkedList<Enemy> enemies) {
        for( Enemy e : enemies ) {
            if(rect.overlaps(e.getRect())) {
                state = ProjectileStatus.HIT_TRAGET;
                e.bulletHit(this);
                return ;
            }
        }
    }

}
