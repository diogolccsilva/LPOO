package com.lpoo.project.logic;

/**
 * Created by Vasco on 12/05/2016.
 */
public class Stats {

    private int health;

    private int maxHealth;

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

    @Override
    public String toString() {
        return super.toString();
    }
}
