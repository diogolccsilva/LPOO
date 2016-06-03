package com.lpoo.project.logic;

import java.util.LinkedList;

/**
 * Created by asus1 on 31/05/2016.
 */
public class Projectile extends Entity implements Updatable, Movable {

    public enum ProjectileStatus { TRAVELLING, HIT_TARGET}
    private ProjectileStatus state;

    private Stats stats;
    private float velocity;

    public Projectile( Game game, float x, float y, int width, int height, int damage ) {
        super(game, x, y, width, height);
        state = ProjectileStatus.TRAVELLING;
        this.stats = new Stats(100,0,0,damage,0);
        velocity = 100f;
    }

    public ProjectileStatus getState() {
        return state;
    }

    public void update( float delta ) {
        if( state != ProjectileStatus.HIT_TARGET) {
            move( -1, delta );
            collision();
        }
    }

    public void collision( ) {
        LinkedList<Enemy> enemies = game.getEnemies();
        for( Enemy e : enemies ) {
            if(rect.overlaps(e.getRect())) {
                state = ProjectileStatus.HIT_TARGET;
                e.hit(stats);
                return ;
            }
        }
    }

    public void move( int dir, float delta ) {
        rect.x += dir * velocity * delta;
    }
}
