package com.lpoo.project.logic;

/**
 * Created by Diogo on 6/5/2016.
 */
public class ProjectileStats extends Stats {

    public int attDamage;

    public float movSpeed;

    public ProjectileStats(int attDamage, float movSpeed) {
        super();
        this.attDamage = attDamage;
        this.movSpeed = movSpeed;
    }

    @Override
    public int getAttDamage() {
        return attDamage;
    }

    @Override
    public float getMovSpeed() {
        return movSpeed;
    }
}
