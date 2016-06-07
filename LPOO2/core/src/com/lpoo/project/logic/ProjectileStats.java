package com.lpoo.project.logic;

/**
 * Class that creates the Projectile's properties
 * This class extends the class Stats
 */
public class ProjectileStats extends Stats {

    /**
     * Projectile's damage
     */
    public int attDamage;

    /**
     * Projectile's movement speed
     */
    public float movSpeed;

    /**
     * Constructor for the class ProjectileStats
     * @param attDamage Projectile's damage
     * @param movSpeed Projectile's movement speed
     */
    public ProjectileStats(int attDamage, float movSpeed) {
        super();
        this.attDamage = attDamage;
        this.movSpeed = movSpeed;
    }

    @Override
    /**
     * Getter for the projectile's damage
     * @return The projectile's damage
     */
    public int getAttDamage() {
        return attDamage;
    }

    @Override
    /**
     * Getter for the projectile's movement speed
     * @return The projectile's movement speed
     */
    public float getMovSpeed() {
        return movSpeed;
    }

    /**
     * Setter for the projectile's damage
     * @param attDamage Value of attack damage to be replaced
     */
    public void setAttDamage(int attDamage) {
        this.attDamage = attDamage;
    }
}
