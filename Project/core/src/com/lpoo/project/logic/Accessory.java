package com.lpoo.project.logic;

/**
 * Created by Vasco on 12/05/2016.
 */
public class Accessory {

    /**
     * @brief Accessory's damage
     */
    private int damage;

    /**
     * @brief Accessory's defense
     */
    private int defense;

    /**
     * @brief Accessory's durability
     */
    private int durability;

    /**
     * @brief Constructor for the class Accessory
     * @param damage
     * @param defense
     * @param durability
     */
    public Accessory( int damage, int defense, int durability ) {

        this.damage = damage;
        this.defense = defense;
        this.durability = durability;
    }

    /**
     * @brief Getter for damage
     * @return accessory's damge
     */
    public int getDamage() {
        return damage;
    }

    /**
     * @brief Getter for defense
     * @return accessory's defense
     */
    public int getDefense() {
        return defense;
    }

    /**
     * @brief Getter for durability
     * @return accessory's durability
     */
    public int getDurability() {
        return durability;
    }
}
