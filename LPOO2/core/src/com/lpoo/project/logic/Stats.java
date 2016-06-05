package com.lpoo.project.logic;

/**
 * Class that creates objects of type Stats (properties of a character)
 * */
public class Stats {

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

    /**
     * Constructor for the class Stats
     * @param health Character's current health
     * @param resistance Character's resistance
     * @param movSpeed Character's movement's speed
     * @param attSpeed Character's attack's speed
     * @param attDamage Character's attack's damage
     */
    public Stats(int health, int resistance, float movSpeed, float attSpeed, int attDamage){
        this.health = health;
        this.maxHealth = health;
        this.resistance = resistance;
        this.movSpeed = movSpeed;
        this.attSpeed = attSpeed;
        this.attDamage = attDamage;
    }

    /**
     * Getter for the character's current health
     * @return Character's current health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Getter for the character's max health
     * @return Character's max health
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Getter for the character's attack's speed
     * @return Character's attack's speed
     */
    public float getAttSpeed() {
        return attSpeed;
    }

    /**
     * Getter for the character's movement's speed
     * @return Character's movement's speed
     */
    public float getMovSpeed() {
        return movSpeed;
    }

    /**
     * Getter for the character's attack's damage
     * @return Character's attack's damage
     */
    public int getAttDamage() {
        return attDamage;
    }

    /**
     * Getter for the character's resistance
     * @return Character's resistance
     */
    public int getResistance() {
        return resistance;
    }

    /**
     * Setter for the character's health
     * @param health Character's new health
     */
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    /**
     * Creates a string with the character's properties
     */
    public String toString() {
        return super.toString();
    }

    /**
     * Function that applies the damage to the character
     * @param stats Character's properties
     */
    public void applyDamage(Stats stats) {
        this.health -= stats.getAttDamage();
    }
}
