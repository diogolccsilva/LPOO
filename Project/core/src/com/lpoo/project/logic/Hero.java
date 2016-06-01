package com.lpoo.project.logic;

/**
 * Created by Vasco on 12/05/2016.
 */
public class Hero extends Character {

    public enum HeroStatus { STILL, ATTACK, MOVE_LEFT, MOVE_RIGHT, DEAD }
    private float move_speed, attack_speed;
    private HeroStatus state, nextState;
    private float stateTime;
    private Game game;

    /**
     * @brief Constructor for the class Hero
     * @param x
     * @param y
     * @param health
     * @param resistance
     * @param strength
     */
    public Hero( int x, int y, int health, int resistance, int strength, Game game )  {
        super( x, y, 45, 88, health, resistance, strength );
        state = HeroStatus.STILL;
        nextState = state;
        move_speed = 1/3f;
        attack_speed = 1/10f * 7;
        this.game = game;
    }

    public HeroStatus getState () {
        return state;
    }

    public HeroStatus getNextState () {
        return nextState;
    }

    public void move( int dir, float delta ) {
        rect.x += 40 * delta * dir;
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

    public void update( float delta ) {
        stateTime += delta;

        switch( state ) {
            case ATTACK:
                if( stateTime >= attack_speed ) {
                    Projectile projectile = new Projectile(rect.x, rect.y + 46, 10, 3, 5, 80);
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
}
