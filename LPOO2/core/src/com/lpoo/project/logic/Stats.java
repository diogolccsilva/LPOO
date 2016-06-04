package com.lpoo.project.logic;

/**
 * Class that creates objects of type Stats (properties of a character)
 * */
public class Stats {

    /**
     * Character's health
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

    private float movSpeed;

    private float attSpeed;

    private int attDamage;

    public Stats(int health, int resistance, float movSpeed, float attSpeed, int attDamage){
        this.health = health;
        this.maxHealth = health;
        this.resistance = resistance;
        this.movSpeed = movSpeed;
        this.attSpeed = attSpeed;
        this.attDamage = attDamage;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public float getAttSpeed() {
        return attSpeed;
    }

    public float getMovSpeed() {
        return movSpeed;
    }

    public int getAttDamage() {
        return attDamage;
    }

    public int getResistance() {
        return resistance;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public void applyDamage(Stats stats) {
        this.health -= stats.getAttDamage();
    }
}
