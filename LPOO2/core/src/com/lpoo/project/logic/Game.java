package com.lpoo.project.logic;

import java.util.LinkedList;

/**
 * Created by Vasco on 10/05/2016.
 */
public class Game implements Updatable {

    public static final int ENEMY_SPAWN_INDEX = 0;
    public static final int ENEMY_KILLED_INDEX = 1;
    public static final int PROJECTILE_FIRED_INDEX = 2;
    public static final int PROJECTILE_ERASED_INDEX = 3;

    private boolean[] frameEvents;

    public enum GameStatus { BUILDING, PLAYING, WON, LOST}
    private GameStatus state;

    private Hero hero;
    private LinkedList<Enemy> enemies;
    private Trap[] traps;
    private LinkedList<Projectile> projectiles;

    public float stateTime;

    private int diffNextEnemy = 2;
    private int enemyResist = 20, enemyHealth = 50, enemyStrength = 20;

    public Game() {
        frameEvents = new boolean[4];
        frameEvents[ENEMY_SPAWN_INDEX] = false;
        frameEvents[ENEMY_KILLED_INDEX] = false;
        frameEvents[PROJECTILE_FIRED_INDEX] = false;
        frameEvents[PROJECTILE_ERASED_INDEX] = false;

        hero = new Hero( this, 200, 144, 100, 10, 25 );
        enemies = new LinkedList<Enemy>();
        traps = new Trap[26];
        projectiles = new LinkedList<Projectile>();
        state = GameStatus.PLAYING;
        stateTime = 0;
    }

    public GameStatus getState() {
        return state;
    }

    public void updatePlaying( float delta ) {
        if( hero.getState() == Hero.HeroStatus.DEAD ) {
            state = GameStatus.LOST;
            return ;
        }

        float currTime = stateTime + delta;
        hero.update( delta );

        for( Enemy e : enemies ) {
            if(e.getState()== Enemy.EnemyStatus.DEAD)
                frameEvents[ENEMY_KILLED_INDEX] = true;
            else e.update(delta);
        }
        for( Projectile p : projectiles ) {
            p.update(delta);
            if( p.getState() == Projectile.ProjectileStatus.HIT_TARGET)
                frameEvents[PROJECTILE_ERASED_INDEX] = true;
        }
        for( Trap t : traps ) {
            if( t == null )
                continue;
            t.update(delta);
        }

        if( Math.floor( stateTime / (float)diffNextEnemy ) != Math.floor( currTime / (float)diffNextEnemy ) ) {
            Enemy e = new Enemy( this, 50, 144, enemyHealth, enemyResist, enemyStrength );
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

    public final Trap[] getTraps() {
        return traps;
    }

    public void eraseEnemy( int index ) {
        enemies.remove(index);
    }

    public void eraseProjectile( int index ) {
        projectiles.remove(index);
    }

    public void eraseTrap( int index ) {
        traps[index] = null;
    }

    public void touchDown( float screenX, float screenY ) {
        hero.touchDown( screenX);
    }

    public void touchUp( ) {
        hero.touchUp();
    }

    public void addProjectile(Projectile projectile) {
        frameEvents[PROJECTILE_FIRED_INDEX] = true;
        projectiles.add(projectile);
    }

    public void addTrap(Trap trap, int index) {
        traps[index] = trap;
    }

}
