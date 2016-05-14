package com.lpoo.project.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Vasco on 10/05/2016.
 */
public class Game {

    public enum GameStatus { BUILDING, PLAYING, WON, LOST}

    private Hero hero;
    private boolean fire;
    private GameStatus state;

    public Game() {

        hero = new Hero( 0, 0, 100, 10, 25 );
        state = GameStatus.PLAYING;
        fire = false;
    }

    public Hero getHero() {

        return hero;
    }

    public boolean getFire() {

        return fire;
    }

    public void touchDown( int screenX, int screenY ) {

        int width = Gdx.graphics.getWidth();
        if( screenX < width / 8 )
            hero.setDir( -1 );
        else if ( screenX > width - width / 8 )
            hero.setDir( 1 );
        else
            fire = true;
    }

    public void touchUp( ) {

        fire = false;
        hero.setDir( 0 );
    }

    public void touchDragged(int screenX, int screenY) {

    }
}
