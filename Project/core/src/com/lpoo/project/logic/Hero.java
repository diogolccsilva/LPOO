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

    public enum HeroStatus { STILL, ATTACK, MOVE_LEFT, MOVE_RIGHT, DEAD }
    private HeroStatus state;
    private HeroStatus nextState;
    private int stateTime;

    /**
     * @brief Constructor for the class Hero
     * @param name
     * @param x
     * @param y
     * @param health
     * @param resistance
     * @param strength
     */
    public Hero(String name, int x, int y, int health, int resistance, int strength )  {

        super(name, x, y, health, resistance, strength );
    }

}
