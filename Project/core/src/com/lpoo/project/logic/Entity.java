package com.lpoo.project.logic;

import com.badlogic.gdx.math.Vector2;

import java.awt.Point;

/**
 * Created by Vasco on 12/05/2016.
 */
public class Entity {
    /*
    Nao sei se queremos que seja abstrata
     */
    Vector2 position;
    //String name;

    /**
     * @brief Constructor for the class Entity
     * @param x, x position of the entity
     * @param y, y position of the entity
     */
    public Entity(/*String name,*/ int x, int y) {

        position = new Vector2( x, y );
       // this.name = name;
    }

    /**
     * @brief Getter for position
     * @return the entity's current position
     */
    public Vector2 getPosition() {

        return position;
    }
}
