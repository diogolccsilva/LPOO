package com.lpoo.project.logic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;

/**
 * Class that creates the game's entities
 */
public class Entity implements Cloneable {

    /**
     * Game where will be placed the entity
     */
    protected Game game;
    /**
     * Bounds of the entity
     */
    protected Rectangle rect;

    /**
     * Constructor for the class Entity
     * @param game Game where will be placed the entity
     * @param x x position of the entity
     * @param y y position of the entity
     * @param width Entity's width
     * @param height Entity's height
     */
    public Entity( Game game, float x, float y, int width, int height ) {
        this.game = game;
        rect = new Rectangle( x, y, width, height );
    }

    /**
     * Getter for the rectangle that reprsents the entity's bounds
     * @return the entity's bounds
     */
    public Rectangle getRect() {
        return rect;
    }

    /**
     * Getter for position
     * @return the entity's current position
     */
    public Vector2 getPosition() {
        return new Vector2( rect.x, rect.y );
    }

    /**
     * Getter for the rectangle's size
     * @return the rectangle's size
     */
    public Vector2 getSize() {
        return new Vector2( rect.width, rect.height );
    }

    public void reset( int x, int y, int width, int height ) {
        rect.x = x;
        rect.y = y;
        rect.width = width;
        rect.height = height;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
