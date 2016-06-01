package com.lpoo.project.animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.lpoo.project.logic.Hero;
import com.lpoo.project.logic.Hero.HeroStatus;
import com.lpoo.project.screens.PlayScreen;

/**
 * Class that creates the hero's animation
 * This class implements the interface Disposable
 */
public class HeroAnimation implements Disposable {

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
    private Animation attack;
    /**
     * Hero's normal's animation
     */
    private Animation still;
    /**
     * Animation of the hero's moving to the left
     */
    private Animation move_left;
    /**
     * Animation of the hero's moving to the right
     */
    private Animation move_right;
    /**
     *  Loads images from texture atlases that represents the attack of the hero
     */
    private TextureAtlas attackTextures;
    /**
     *  Loads images from texture atlases that represents the "normal" position of the hero
     */
    private TextureAtlas stillTextures;
    /**
     *  Loads images from texture atlases that represents the hero moving to the left
     */
    private TextureAtlas move_leftTextures;
    /**
     *  Loads images from texture atlases that represents the hero moving to the right
     */
    private TextureAtlas move_rightTextures;
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


    public HeroAnimation( String attackPath,
                          String stillPath, String leftPath,
                          String rightPath, float attackSpeed, float moveSpeed ) {
        stateTime = 0;
        state = HeroStatus.STILL;

        attackTextures = new TextureAtlas( Gdx.files.internal( attackPath ) );
        attack = new Animation( attackSpeed, attackTextures.getRegions() );

        stillTextures = new TextureAtlas( Gdx.files.internal( stillPath ) );
        still = new Animation( moveSpeed, stillTextures.getRegions() );

        move_leftTextures = new TextureAtlas( Gdx.files.internal( leftPath ) );
        move_left = new Animation( moveSpeed, move_leftTextures.getRegions() );

        move_rightTextures = new TextureAtlas( Gdx.files.internal( rightPath ) );
        move_right = new Animation( moveSpeed, move_rightTextures.getRegions() );

        currAnimation = still;

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
                attack.setFrameDuration( speed );
                break;
            case STILL:
                still.setFrameDuration( speed );
                break;
            case MOVE_LEFT:
                move_left.setFrameDuration( speed );
                break;
            case MOVE_RIGHT:
                move_right.setFrameDuration( speed );
                break;
        }
    }

    /**
     * Getter for the current texture of the animation
     * @param delta Increasing time
     * @return TextureRegion to be drawn on the screen
     */
    public TextureRegion getTexture ( HeroStatus stat, float delta ) {
        Animation nextAnimation = null;

        switch ( stat ) {
            case ATTACK:
                nextAnimation = attack;
                break;
            case STILL:
                nextAnimation = still;
                break;
            case MOVE_LEFT:
                nextAnimation = move_left;
                break;
            case MOVE_RIGHT:
                nextAnimation = move_right;
                break;
        }

        stateTime += delta;

        if (currAnimation.isAnimationFinished(stateTime) || ( currAnimation == still && nextAnimation != still )) {
            stateTime = 0;
            state = stat;
            currAnimation = nextAnimation;
        }

        return currAnimation.getKeyFrame( stateTime, true );
    }

    @Override
    /**
     * Releases all textures of the hero
     */
    public void dispose() {
        attackTextures.dispose();
        stillTextures.dispose();
        move_leftTextures.dispose();
        move_rightTextures.dispose();
    }
}
