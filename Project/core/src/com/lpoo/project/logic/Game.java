package com.lpoo.project.logic;

import com.badlogic.gdx.Gdx;

import java.util.LinkedList;

/**
 * Created by Vasco on 10/05/2016.
 */
public class Game {

    public enum GameStatus { BUILDING, PLAYING, WON, LOST}
    private GameStatus state;

    private Hero hero;
    private LinkedList<Enemy> enemies;
    private LinkedList<Trap> traps;

    public float stateTime;

    private int diffNextEnemy = 10;
    private int enemyResist = 10, enemyHealth = 10, enemyStrength = 10;

    public Game() {
        hero = new Hero( 600, 144, 100, 10, 25 );
        enemies = new LinkedList<Enemy>();
        traps = new LinkedList<Trap>();
        state = GameStatus.PLAYING;
        stateTime = 0;
    }

    public void update( float delta ) {
        float currTime = stateTime + delta;

        for( int i = 0; i < enemies.size(); i++ ) {
            enemies.get(i).update( delta );
        }

        if( Math.floor( stateTime / diffNextEnemy ) != Math.floor( currTime / diffNextEnemy ) ) {
            Enemy e = new Enemy( 50, 144, enemyHealth, enemyResist, enemyStrength );
            enemies.add(e);
        }
        stateTime += delta;
    }

    public final Hero getHero() {
        return hero;
    }

    public final LinkedList<Enemy> getEnemies () {
        return enemies;
    }

    public void touchDown( int screenX, int screenY ) {

    }

    public void touchUp( ) {

    }

    public void touchDragged(int screenX, int screenY) {

    }
}
