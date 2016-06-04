package com.lpoo.project.logic;

/**
 * Class that creates the accessories to be used
 */
public class Accessory {

    /**
     * Accessory's damage
     */
    private int damage;

    /**
     * Accessory's defense
     */
    private int defense;

    /**
     * Accessory's durability
     */
    private int durability;

    /**
     * Constructor for the class Accessory
     * @param damage Acessory's damage
     * @param defense Acessory's defense
     * @param durability Acessory's durability
     */
    public Accessory( int damage, int defense, int durability ) {

        this.damage = damage;
        this.defense = defense;
        this.durability = durability;
    }

    /**
     * Getter for damage
     * @return Accessory's damage
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Getter for defense
     * @return Accessory's defense
     */
    public int getDefense() {
        return defense;
    }

    /**
     * Getter for durability
     * @return Accessory's durability
     */
    public int getDurability() {
        return durability;
    }
}
