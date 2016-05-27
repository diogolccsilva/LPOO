package com.lpoo.project.animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lpoo.project.logic.Hero;
import com.lpoo.project.logic.Hero.HeroStatus;
import com.lpoo.project.screens.PlayScreen;

/**
 * Created by Vasco on 27/05/2016.
 */
public class HeroAnimation {

    private PlayScreen game;

    private HeroStatus status;
    private Animation currAnimation;
    private Animation attack, still, move_left, move_right;
    private TextureAtlas attackTextures, stillTextures, move_leftTextures, move_rigtTextures;
    private float stateTime;

    /**
     * @brief Constructor for the Animator class
     * @param attackPath
     * @param movePath
     * @param attackSpeed
     * @param moveSpeed
     */
    public HeroAnimation( PlayScreen game, String attackPath, String movePath, float attackSpeed, float moveSpeed ) {
        this.game = game;
        stateTime = 0;
        status = HeroStatus.STILL;

        attackTextures = new TextureAtlas( Gdx.files.internal( attackPath ) );
        attack = new Animation( attackSpeed, attackTextures.getRegions() );

        stillTextures = new TextureAtlas( Gdx.files.internal( movePath ) );
        still = new Animation( moveSpeed, stillTextures.getRegions() );

        move_leftTextures = new TextureAtlas( Gdx.files.internal( movePath ) );
        move_left = new Animation( moveSpeed, stillTextures.getRegions() );

        move_rigtTextures = new TextureAtlas( Gdx.files.internal( movePath ) );
        move_right = new Animation( moveSpeed, stillTextures.getRegions() );

        currAnimation = still;
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
}
