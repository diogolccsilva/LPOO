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

    public Game() {

        hero = new Hero( 0, 0, 100, 10, 25 );/**/

        fire = false;
    }

    public Hero getHero() {

        return hero;
    }

    public boolean getFire() {

        return fire;
    }

    public void render(SpriteBatch batch, OrthographicCamera camera) {

        Vector2 hPos = hero.getPosition();
        camera.position.set( hPos.x, hPos.y, 0 );
        camera.update();
        hero.SelectImg( fire ).draw( batch );
    }

    public void touchDown(int screenX, int screenY) {

        int width = Gdx.graphics.getWidth();
        if( screenX < width / 8 )
            hero.walk( -1 );
        else if ( screenX > width - width / 8 )
            hero.walk( 1 );
        else
            fire = true;
    }

    public void touchUp(int screenX, int screenY) {

        fire = false;
    }

    public void touchDragged(int screenX, int screenY) {

    }
}
