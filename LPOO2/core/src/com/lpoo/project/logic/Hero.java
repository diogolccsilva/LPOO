package com.lpoo.project.logic;

import com.lpoo.project.MyGame;

/**
 * Created by Vasco on 12/05/2016.
 */
public class Hero extends Character implements Updatable, Movable, Hitable {

    public enum HeroStatus { STILL, ATTACK, MOVE_LEFT, MOVE_RIGHT, DEAD }
    private HeroStatus state, nextState;
    private float stateTime;

    private float move_speed, attack_speed;
    private float velocity;

    /**
     * @brief Constructor for the class Hero
     * @param x
     * @param y
     * @param health
     * @param resistance
     * @param strength
     */
    public Hero( Game game, int x, int y, int health, int resistance, int strength )  {
        super( game, x, y, 45, 88, health, resistance, strength );
        state = HeroStatus.STILL;
        nextState = state;
        move_speed = 1/3f;
        attack_speed = 0.7f;
        velocity = 50f;
        this.game = game;
    }

    public HeroStatus getState () {
        return state;
    }

    public HeroStatus getNextState () {
        return nextState;
    }

    public void move(HeroStatus state) {
        nextState = state;
    }

    public void attack() {
        nextState = HeroStatus.ATTACK;
    }

    public void touchDown( float screenX, float screenY ) {
        if( screenX < 50 )
            nextState = HeroStatus.MOVE_LEFT;
        else if( screenX > 840 )
            nextState = HeroStatus.MOVE_RIGHT;
        else
            nextState = HeroStatus.ATTACK;
    }

    public void touchUp( ) {
        nextState = HeroStatus.STILL;
    }

    public void AnimationStatus( HeroStatus stat ) {
        if( stat != state ) {
            nextState = stat;
            state = stat;
            stateTime = 0;
        }
    }

    @Override
    public void update(float delta) {
        stateTime += delta;

        switch( state ) {
            case ATTACK:
                if( stateTime >= attack_speed ) {
                    Projectile projectile = new Projectile(game, rect.x, rect.y + 46, 10, 3, 5);
                    game.addProjectile(projectile);
                    stateTime -= attack_speed;
                }
                break;
            case MOVE_LEFT:
                move( -1, delta );
                break;
            case MOVE_RIGHT:
                move( 1, delta );
                break;
        }
    }

    public void hit(Stats stats) {
        this.stats.applyDamage(stats);
        if(this.stats.getHealth()<=0) {
            state = HeroStatus.DEAD;
            nextState = HeroStatus.DEAD;
        }
    }

    public void move( int dir, float delta ) {
        rect.x += velocity * delta * dir;
    }

    @Override
    public String toString(){
        return "Hero [stats=" + stats + "]";
    }
}
