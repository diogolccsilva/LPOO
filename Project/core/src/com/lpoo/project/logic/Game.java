package com.lpoo.project.logic;

import com.badlogic.gdx.Gdx;

/**
 * Created by Vasco on 10/05/2016.
 */
public class Game {

    public enum GameStatus { BUILDING, PLAYING, WON, LOST}
    private GameStatus state;

    public Hero hero;

    public Game() {

        hero = new Hero( 0, 0, 100, 10, 25 );
        state = GameStatus.PLAYING;
    }

    public Hero getHero() {

        return hero;
    }

    public void touchDown( int screenX, int screenY ) {

        int width = Gdx.graphics.getWidth();
        if( screenX < width / 8 )
            hero.setDir( -1 );
        else if ( screenX > width - width / 8 )
            hero.setDir( 1 );
    }

    public void touchUp( ) {

        hero.setDir( 0 );
    }

    public void touchDragged(int screenX, int screenY) {

    }
}
