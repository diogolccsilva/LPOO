package com.lpoo.project.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.TimeUtils;
import com.lpoo.project.animations.Animator;

import java.util.LinkedList;

/**
 * Created by Vasco on 12/05/2016.
 */
public class Hero extends Character {

    /**
     * @brief Constructor for the class Hero
     * @param x
     * @param y
     * @param health
     * @param resistance
     * @param strength
     */
    public Hero( int x, int y, int health, int resistance, int strength )  {

        super( x, y, health, resistance, strength );
    }

    public void setDir( int dir ) {

        //dir = -1 while moving left, dir = 0 while not moving, dir = 1 while moving
        if( dir < -1 || dir > 1)
            return ;

        //Character's can only move left or right
        velocity = max_velocity * dir;

    }

    public void update(float delta) {

        position.x += velocity * delta;
    }

}
