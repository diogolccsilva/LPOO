package com.lpoo.project.logic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Vasco on 10/05/2016.
 */
public class Game {

    public enum GameStatus { BUILDING, PLAYING, WON, LOST}

    private Hero hero;
    private boolean fire;

    public Game() {

        hero = new Hero( 2, 1, 100, 10, 25 );

        fire = false;
    }

    public void render(SpriteBatch batch) {

        Vector2 hPos = hero.getPosition();
        batch.draw( hero.SelectImg( fire ), hPos.x, hPos.y );
    }

    public void touchDown(int screenX, int screenY) {

        fire = true;
    }

    public void touchUp(int screenX, int screenY) {

        fire = false;
    }

    public void touchDragged(int screenX, int screenY) {

    }
}
