package com.lpoo.project.logic;

import java.util.LinkedList;

/**
 * Created by Vasco on 10/05/2016.
 */
public class Game {

    public static final int ENEMY_SPAWN_INDEX = 0;
    public static final int PROJECTILE_FIRED_INDEX = 1;

    private boolean[] frameEvents;

    public enum GameStatus { BUILDING, PLAYING, WON, LOST}
    private GameStatus state;

    private Hero hero;
    private LinkedList<Enemy> enemies;
    private LinkedList<Trap> traps;
    private LinkedList<Projectile> projectiles;

    public float stateTime;

    private int diffNextEnemy = 10;
    private int enemyResist = 10, enemyHealth = 10, enemyStrength = 10;

    public Game() {
        frameEvents = new boolean[2];
        frameEvents[ENEMY_SPAWN_INDEX] = false;
        frameEvents[PROJECTILE_FIRED_INDEX] = false;

        hero = new Hero( 200, 144, 100, 10, 25, this );
        enemies = new LinkedList<Enemy>();
        traps = new LinkedList<Trap>();
        projectiles = new LinkedList<Projectile>();
        state = GameStatus.PLAYING;
        stateTime = 0;
    }

    public void updatePlaying( float delta ) {
        float currTime = stateTime + delta;
        hero.update( delta );

        for( int i = 0; i < enemies.size(); i++ )
            enemies.get(i).update( delta, hero );

        if( Math.floor( stateTime / (float)diffNextEnemy ) != Math.floor( currTime / (float)diffNextEnemy ) ) {
            Enemy e = new Enemy( 50, 144, enemyHealth, enemyResist, enemyStrength );
            frameEvents[ENEMY_SPAWN_INDEX] = true;
            enemies.add(e);
        }

        stateTime = currTime;
    }

    public void update( float delta ) {
        switch ( state ) {
            case PLAYING:
                updatePlaying( delta );
                break;
            case BUILDING:
                break;
            case WON:
                break;
            case LOST:
                break;
        }
    }

    public boolean[] getFrameEvents() {
        return frameEvents;
    }

    public void setFrameEvents( ) {
        for( int i = 0; i < frameEvents.length; i++ )
            frameEvents[i] = false;
    }

    public final Hero getHero() {
        return hero;
    }

    public final LinkedList<Enemy> getEnemies () {
        return enemies;
    }

    public void touchDown( float screenX, float screenY ) {
        hero.touchDown( screenX, screenY );
    }

    public void touchUp( ) {
        hero.touchUp();
    }

    public void addProjectile(Projectile projectile) {
        projectiles.add(projectile);
    }

}
