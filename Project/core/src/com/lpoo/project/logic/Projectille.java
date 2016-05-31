package com.lpoo.project.logic;

import java.util.LinkedList;

/**
 * Created by asus1 on 31/05/2016.
 */
public class Projectille extends Entity {

    private int damage, velocity;

    public Projectille( float x, float y, int width, int height, int damage, int velocity ) {
        super(x, y, width, height);
        this.damage = damage;
        this.velocity = velocity;
    }

    public int getDamage() {
        return damage;
    }

    public boolean collision(LinkedList<Enemy> enemies) {
        for(int i=0; i<enemies.size(); i++) {
            if(rect.overlaps(enemies.get(i).getRect())) {
                int health = enemies.get(i).getStats().getHealth() - damage;
                enemies.get(i).getStats().setHealth(health);
                return true;
            }
        }

        return false;
    }

}
