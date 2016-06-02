package com.lpoo.project.logic;

/**
 * Created by Vasco on 12/05/2016.
 */
public class Stats {

    /**
     * @brief Character's health
     */
    private int health;

    private int maxHealth;

    /**
     * @brief Character's resistance
     */
    private int resistance;

    /**
     * @brief Character's strength
     */
    private int strength;

    /**
     * @brief Constructor for the class Stats
     * @param health
     * @param resistance
     * @param strength
     */
    public Stats ( int health, int resistance, int strength ) {
        maxHealth = health;
        this.health = health;
        this.resistance = resistance;
        this.strength = strength;
    }

    /**
     * @brief Getter for Character's current health
     * @return character's current health
     */
    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * @brief Getter for strength
     * @return character's current strength
     */
    public int getStrength() {
        return strength;
    }

    /**
     * @brief Getter for resistance
     * @return character's current resistance
     */
    public int getResistance() {
        return resistance;
    }

    /**
     * @brief Sets the health's variable
     * @param health New health to be setted
     */
    public void setHealth(int health) {
        this.health -= health;
    }
}
