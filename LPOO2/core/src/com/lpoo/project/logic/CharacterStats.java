package com.lpoo.project.logic;

/**
 * Created by Diogo on 6/5/2016.
 */
public class CharacterStats extends Stats {

    /**
     * Character's current health
     */
    private int health;

    /**
     * Character's max health
     */
    private int maxHealth;

    /**
     * Character's resistance
     */
    private int resistance;

    /**
     * Character's movement's velocity
     */
    private float movSpeed;

    /**
     * Character's attack's speed
     */
    private float attSpeed;

    /**
     * Character's attack's damage
     */
    private int attDamage;

    public CharacterStats(int health, int resistance, float movSpeed, float attSpeed, int attDamage) {
        super();
        this.health = health;
        this.maxHealth = health;
        this.resistance = resistance;
        this.movSpeed = movSpeed;
        this.attSpeed = attSpeed;
        this.attDamage = attDamage;
    }

    /**
     * Getter for the character's current health
     *
     * @return Character's current health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Getter for the character's max health
     *
     * @return Character's max health
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Getter for the character's attack's speed
     *
     * @return Character's attack's speed
     */
    public float getAttSpeed() {
        return attSpeed;
    }

    /**
     * Getter for the character's movement's speed
     *
     * @return Character's movement's speed
     */
    public float getMovSpeed() {
        return movSpeed;
    }

    /**
     * Getter for the character's attack's damage
     *
     * @return Character's attack's damage
     */
    public int getAttDamage() {
        return attDamage;
    }

    /**
     * Getter for the character's resistance
     *
     * @return Character's resistance
     */
    public int getResistance() {
        return resistance;
    }

    /**
     * Setter for the character's health
     *
     * @param health Character's new health
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Setter for the character's attack speed
     *
     * @param attSpeed
     */
    public void setAttSpeed(float attSpeed) {
        this.attSpeed = attSpeed;
    }

    /**
     * Setter for the character's movement speed
     *
     * @param movSpeed
     */
    public void setMovSpeed(float movSpeed) {

        this.movSpeed = movSpeed;
    }
    /**
     * Setter for the character's resistance
     *
     * @param resistance
     */
    public void setResistance(int resistance) {
        this.resistance = resistance;
    }

    /**
     * Setter for the character's maximum health
     *
     * @param maxHealth
     */
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    /**
     * Setter for the character's attack damage
     *
     * @param attDamage
     */
    public void setAttDamage(int attDamage) {
        this.attDamage = attDamage;
    }

    /**
     * Function that applies the damage to the character
     *
     * @param stats Character's properties
     */
    public void applyDamage(Stats stats) {
        this.health -= stats.getAttDamage();
    }

}
