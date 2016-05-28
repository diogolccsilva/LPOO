package com.lpoo.project.logic;

import com.badlogic.gdx.math.Vector2;

import java.awt.Point;
import java.awt.Rectangle;

/**
 * Created by Vasco on 12/05/2016.
 */
public class Entity {
    /*
    Nao sei se queremos que seja abstrata
     */
    protected Vector2 position;
    protected int width, height;

    /**
     * @brief Constructor for the class Entity
     * @param x, x position of the entity
     * @param y, y position of the entity
     */
    public Entity( int x, int y, int width, int height ) {

        position = new Vector2( x, y );
        this.width = width;
        this.height = height;
    }

    /**
     * @brief Getter for position
     * @return the entity's current position
     */
    public Vector2 getPosition() {
        return position;
    }

    public boolean collision(Rectangle rect){
        return position.x < rect.x + rect.width && position.x + width > rect.x && position.y < rect.y + rect.height && position.y + height > rect.y;
    }
}
