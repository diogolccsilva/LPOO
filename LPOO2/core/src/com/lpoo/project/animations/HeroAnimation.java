package com.lpoo.project.animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.lpoo.project.logic.Game;
import com.lpoo.project.logic.Hero;
import com.lpoo.project.logic.Hero.HeroStatus;
import com.lpoo.project.screens.PlayScreen;

/**
 * Class that creates the hero's animation
 * This class implements the interface Disposable
 */
public class HeroAnimation extends Animator {

    private static final int ATTACK_INDEX = 0;
    private static final int MOVE_LEFT_INDEX = 1;
    private static final int MOVE_RIGHT_INDEX = 2;
    private static final int STILL_INDEX = 3;
    private static final int ARRAY_SIZE = 4;

    /**
     * Hero status
     */
    private HeroStatus state;
    /**
     * Current animation of the hero
     */
    private Animation currAnimation;
    /**
     * Hero's attack's animation
     */
    //private Animation attack;
    /**
     * Hero's normal's animation
     */
    //private Animation still;
    /**
     * Animation of the hero's moving to the left
     */
    //private Animation move_left;
    /**
     * Animation of the hero's moving to the right
     */
    //private Animation move_right;
    /**
     *  Loads images from texture atlases that represents the attack of the hero
     */
    //private TextureAtlas attackTextures;
    /**
     *  Loads images from texture atlases that represents the "normal" position of the hero
     */
    //private TextureAtlas stillTextures;
    /**
     *  Loads images from texture atlases that represents the hero moving to the left
     */
    //private TextureAtlas move_leftTextures;
    /**
     *  Loads images from texture atlases that represents the hero moving to the right
     */
    //private TextureAtlas move_rightTextures;
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
     * @param delta Increasing time
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
                currAnimation = animations[STILL_INDEX];;
                stateTime = 0;
                state = stat;
                return currAnimation.getKeyFrame( stateTime, true );
        }

        stateTime += delta;

        if ( currAnimation.isAnimationFinished(stateTime) ||
                ( currAnimation == animations[STILL_INDEX] && nextAnimation != animations[STILL_INDEX] )) {
            stateTime = 0;
            state = stat;
            currAnimation = nextAnimation;
        }

        return currAnimation.getKeyFrame( stateTime, true );
    }

}
