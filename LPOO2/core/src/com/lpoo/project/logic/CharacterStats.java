package com.lpoo.project.logic;

/**
 * Class that creates the character's properties
 * This class extends the class Stats
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

    /**
     * Constructor for the CharacterStats class
     * @param health Character's health
     * @param resistance Character's resistance
     * @param movSpeed Character's movement's velocity
     * @param attSpeed Character's attack's speed
     * @param attDamage Character's attack's damage
     */
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

    /**
     * Setter for the character's attack speed
     * @param attSpeed New Character's attack's speed to change the old one
     */
    public void setAttSpeed(float attSpeed) {
        this.attSpeed = attSpeed;
    }

    /**
     * Setter for the character's movement speed
     * @param movSpeed New Character's movement speed to change the old one
     */
    public void setMovSpeed(float movSpeed) {

        this.movSpeed = movSpeed;
    }
    /**
     * Setter for the character's resistance
     * @param resistance New Character's resistance to change the old one
     */
    public void setResistance(int resistance) {
        this.resistance = resistance;
    }

    /**
     * Setter for the character's maximum health
     * @param maxHealth New Character's maximum health to change the old one
     */
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    /**
     * Setter for the character's attack damage
     * @param attDamage New Character's attack's damage to change the old one
     */
    public void setAttDamage(int attDamage) {
        this.attDamage = attDamage;
    }

    /**
     * Function that applies the damage to the character
     * @param stats Character's properties
     */
    public void applyDamage(Stats stats) {
        this.health -= stats.getAttDamage() * (1-this.resistance/20);
    }

}
