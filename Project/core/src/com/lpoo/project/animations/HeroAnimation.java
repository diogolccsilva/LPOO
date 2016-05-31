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
 * Created by Vasco on 27/05/2016.
 */
public class HeroAnimation implements Disposable {

    private PlayScreen game;

    private HeroStatus status;
    private Animation currAnimation;
    private Animation attack, still, move_left, move_right;
    private TextureAtlas attackTextures, stillTextures, move_leftTextures, move_rightTextures;
    private float stateTime;

    /**
     * @brief Constructor for the Animator class
     * @param attackPath
     * @param stillPath
     * @param leftPath
     * @param rightPath
     * @param attackSpeed
     * @param moveSpeed
     */
    public HeroAnimation( PlayScreen game, String attackPath,
                          String stillPath, String leftPath,
                          String rightPath, float attackSpeed, float moveSpeed ) {
        this.game = game;
        stateTime = 0;
        status = HeroStatus.STILL;

        attackTextures = new TextureAtlas( Gdx.files.internal( attackPath ) );
        attack = new Animation( attackSpeed, attackTextures.getRegions() );

        stillTextures = new TextureAtlas( Gdx.files.internal( stillPath ) );
        still = new Animation( moveSpeed, stillTextures.getRegions() );

        move_leftTextures = new TextureAtlas( Gdx.files.internal( leftPath ) );
        move_left = new Animation( moveSpeed, move_leftTextures.getRegions() );

        move_rightTextures = new TextureAtlas( Gdx.files.internal( rightPath ) );
        move_right = new Animation( moveSpeed, move_rightTextures.getRegions() );

        currAnimation = still;
    }

    public HeroStatus getStatus() {
        return status;
    }

    /**
     * @biref Sets the speed of an animation
     * @param stat
     * @param speed
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
     * @brief Getter for the current texture of the animation
     * @param delta
     * @return TextureRegion to be drawn on the screen
     */
    public TextureRegion getTexture ( HeroStatus stat, float delta ) {
        /* Mundo complitado */

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
            status = stat;
            currAnimation = nextAnimation;
        }

        return currAnimation.getKeyFrame( stateTime, true );
    }

    @Override
    public void dispose() {
        attackTextures.dispose();
        stillTextures.dispose();
        move_leftTextures.dispose();
        move_rightTextures.dispose();
    }
}
