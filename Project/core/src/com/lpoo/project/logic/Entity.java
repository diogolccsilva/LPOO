package com.lpoo.project.logic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Vasco on 12/05/2016.
 */
public class Entity {
    /*
    Nao sei se queremos que seja abstrata
     */
    protected Game game;
    protected Rectangle rect;

    /**
     * @brief Constructor for the class Entity
     * @param x, x position of the entity
     * @param y, y position of the entity
     */
    public Entity( Game game, float x, float y, int width, int height ) {
        this.game = game;
        rect = new Rectangle( x, y, width, height );
    }

    public Rectangle getRect() {
        return rect;
    }

    /**
     * @brief Getter for position
     * @return the entity's current position
     */
    public Vector2 getPosition() {
        return new Vector2( rect.x, rect.y );
    }

    public Vector2 getSize() {
        return new Vector2( rect.width, rect.height );
    }
/*
    public boolean collision(Rectangle rect){
        return position.x < rect.x + rect.width && position.x + width > rect.x && position.y < rect.y + rect.height && position.y + height > rect.y;
    }
*/
}
