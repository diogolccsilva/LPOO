package com.lpoo.project.logic;

public class ProjectileStats extends Stats {

    /**
     * Projectile's damage
     */
    public int attDamage;

    /**
     * Projectile's movement speed
     */
    public float movSpeed;

    public int penetration; //TODO: add this

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
