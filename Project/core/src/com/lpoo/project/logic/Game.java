package com.lpoo.project.logic;

import java.util.LinkedList;

/**
 * Created by Vasco on 10/05/2016.
 */
public class Game {

    public static final int ENEMY_SPAWN_INDEX = 0;
    public static final int ENEMY_KILLED_INDEX = 1;
    public static final int PROJECTILE_FIRED_INDEX = 2;
    public static final int PROJECTILE_ERASED_INDEX = 3;

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
        frameEvents = new boolean[4];
        frameEvents[ENEMY_SPAWN_INDEX] = false;
        frameEvents[ENEMY_KILLED_INDEX] = false;
        frameEvents[PROJECTILE_FIRED_INDEX] = false;
        frameEvents[PROJECTILE_ERASED_INDEX] = false;

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

        for( Enemy e : enemies ) {
            if(e.getState()== Enemy.EnemyStatus.DEAD) {
                frameEvents[ENEMY_KILLED_INDEX] = true;
                //enemies.remove(e);
            }
            else e.update(delta, hero);
        }
        for( Projectile p : projectiles ) {
            p.update(delta, enemies);
            if( p.getState() == Projectile.ProjectileStatus.HIT_TRAGET ) {
                frameEvents[PROJECTILE_ERASED_INDEX] = true;
                //projectiles.remove(p);
            }
        }

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

    public final LinkedList<Enemy> getEnemies() {
        return enemies;
    }

    public final LinkedList<Projectile> getProjectiles() {
        return projectiles;
    }

    public void eraseEnemy( int index ) {
        enemies.remove(index);
    }

    public void eraseProjectile( int index ) {
        projectiles.remove(index);
    }

    public void touchDown( float screenX, float screenY ) {
        hero.touchDown( screenX, screenY );
    }

    public void touchUp( ) {
        hero.touchUp();
    }

    public void addProjectile(Projectile projectile) {
        frameEvents[PROJECTILE_FIRED_INDEX] = true;
        projectiles.add(projectile);
    }

}
