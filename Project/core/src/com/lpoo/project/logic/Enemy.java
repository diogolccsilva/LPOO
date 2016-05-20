package com.lpoo.project.logic;

/**
 * Created by Vasco on 12/05/2016.
 */
public class Enemy extends Character {

    /**
     * @brief Constructor for the class Enemy
     * @param name
     * @param x
     * @param y
     * @param health
     * @param resistance
     * @param strength
     */
    public Enemy(String name, int x, int y, int health, int resistance, int strength )  {

        super(name, x, y, health, resistance, strength );
    }
}
