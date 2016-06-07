package com.lpoo.project.animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lpoo.project.logic.Game;
import com.lpoo.project.logic.Hero.HeroStatus;

/**
 * Class that creates the hero's animation
 * This class extends the class Animator
 */
public class HeroAnimation extends Animator {

    /**
     * Hero's attack index
     */
    private static final int ATTACK_INDEX = 0;
    /**
     * Hero's movement to the left's index
     */
    private static final int MOVE_LEFT_INDEX = 1;
    /**
     * Hero's movement to the right's index
     */
    private static final int MOVE_RIGHT_INDEX = 2;
    /**
     * Hero stopped's index
     */
    private static final int STILL_INDEX = 3;
    /**
     * Size of the hero's array
     */
    private static final int ARRAY_SIZE = 4;

    /**
     * Hero's status
     */
    private HeroStatus state;
    /**
     * Current animation of the hero
     */
    private Animation currAnimation;

    /**
     * Time given to the life's status of the hero
     */
    private float stateTime;
    /**
     * Hero's velocity's attack
     */
    private float attack_speed;
    /**
     * Velocity of hero when he is moving
     */
    private float move_speed;

    /**
     * Constructor for the class HeroAnimation
     * @param game Game where the hero's animation will be placed
     * @param attackPath Path where the TextureAtlas of the hero's attack is saved
     * @param stillPath Path where the TextureAtlas of the hero stopped is saved
     * @param leftPath Path where the TextureAtlas of the hero's movement to the left is saved
     * @param rightPath Path where the TextureAtlas of the hero's movement to the right is saved
     * @param attackSpeed Hero's attack's speed
     * @param moveSpeed Hero's movement's speed
     */
    public HeroAnimation( Game game, String attackPath,
                         String stillPath, String leftPath,
                         String rightPath, float attackSpeed, float moveSpeed ) {
        super( game, ARRAY_SIZE, ARRAY_SIZE, 0 );
        stateTime = 0;
        state = HeroStatus.STILL;

        textures[ATTACK_INDEX] = new TextureAtlas( Gdx.files.internal( attackPath ) );
        animations[ATTACK_INDEX] = new Animation( attackSpeed, textures[ATTACK_INDEX].getRegions() );

        textures[STILL_INDEX] = new TextureAtlas( Gdx.files.internal( stillPath ) );
        animations[STILL_INDEX] = new Animation( moveSpeed, textures[STILL_INDEX].getRegions() );

        textures[MOVE_LEFT_INDEX] = new TextureAtlas( Gdx.files.internal( leftPath ) );
        animations[MOVE_LEFT_INDEX] = new Animation( moveSpeed, textures[MOVE_LEFT_INDEX].getRegions() );

        textures[MOVE_RIGHT_INDEX] = new TextureAtlas( Gdx.files.internal( rightPath ) );
        animations[MOVE_RIGHT_INDEX] = new Animation( moveSpeed, textures[MOVE_RIGHT_INDEX].getRegions() );

        currAnimation = animations[STILL_INDEX];

        this.attack_speed = attackSpeed;
        this.move_speed = moveSpeed;
    }

    public HeroStatus getState() {
        return state;
    }

    /**
     * Sets the speed of an animation
     * @param stat Hero's status
     * @param speed Hero's velocity when he is moving
     */
    public void setAttackSpeed ( HeroStatus stat, float speed ) {

        switch ( stat ) {
            case ATTACK:
                animations[ATTACK_INDEX].setFrameDuration( speed );
                break;
            case STILL:
                animations[STILL_INDEX].setFrameDuration( speed );
                break;
            case MOVE_LEFT:
                animations[MOVE_LEFT_INDEX].setFrameDuration( speed );
                break;
            case MOVE_RIGHT:
                animations[MOVE_RIGHT_INDEX].setFrameDuration( speed );
                break;
        }
    }

    /**
     * Getter for the current texture of the animation
     * @param delta Difference between the last time of call and the current time
     * @return TextureRegion to be drawn on the screen
     */
    public TextureRegion getTexture( float delta ) {
        Animation nextAnimation = null;
        HeroStatus stat = game.getHero().getNextState();

        switch ( stat ) {
            case ATTACK:
                nextAnimation = animations[ATTACK_INDEX];
                break;
            case STILL:
                nextAnimation = animations[STILL_INDEX];
                break;
            case MOVE_LEFT:
                nextAnimation = animations[MOVE_LEFT_INDEX];
                break;
            case MOVE_RIGHT:
                nextAnimation = animations[MOVE_RIGHT_INDEX];
                break;
            case DEAD:
                currAnimation = animations[STILL_INDEX];
                stateTime = 0;
                state = stat;
                return currAnimation.getKeyFrame( stateTime, true );
        }

        stateTime += delta;

        if ( currAnimation.isAnimationFinished(stateTime) ||
                ( currAnimation == animations[STILL_INDEX] && nextAnimation != animations[STILL_INDEX] ||
                        state == HeroStatus.DEAD)) {
            stateTime = 0;
            if( nextAnimation != animations[STILL_INDEX] )
                state = stat;
            else state = stat;
            currAnimation = nextAnimation;
        }

        return currAnimation.getKeyFrame( stateTime, true );
    }

    /**
     * Resets the animation and changes the values of stateTime, state and currAnimation
     */
    public void reset( Game game ) {
        stateTime = 0;
        this.game = game;
        state = HeroStatus.STILL;
        currAnimation = animations[STILL_INDEX];
    }

    /**
     * Clones the hero's animation
     */
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
