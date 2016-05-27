package com.lpoo.project.animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lpoo.project.logic.Enemy;
import com.lpoo.project.screens.PlayScreen;

/**
 * Created by Vasco on 27/05/2016.
 */
public class EnemyAnimation {
    private PlayScreen game;

    private Enemy.EnemyStatus status;
    private Animation currAnimation;
    private Animation attack, move_right;
    private TextureAtlas attackTextures, move_rigtTextures;
    private float stateTime;

    /**
     * @brief Constructor for the Animator class
     * @param attackPath
     * @param movePath
     * @param attackSpeed
     * @param moveSpeed
     */
    public EnemyAnimation( PlayScreen game, String attackPath, String movePath, float attackSpeed, float moveSpeed ) {
        this.game = game;
        stateTime = 0;
        status = Enemy.EnemyStatus.MOVE_RIGHT;

        attackTextures = new TextureAtlas( Gdx.files.internal( attackPath ) );
        attack = new Animation( attackSpeed, attackTextures.getRegions() );

        move_rigtTextures = new TextureAtlas( Gdx.files.internal( movePath ) );
        move_right = new Animation( moveSpeed, move_rigtTextures.getRegions() );

        currAnimation = move_right;
    }

    /**
     * @biref Sets the speed of an animation
     * @param stat
     * @param speed
     */
    public void setAttackSpeed (Enemy.EnemyStatus stat, float speed ) {

        switch ( stat ) {
            case ATTACK:
                attack.setFrameDuration( speed );
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
    public TextureRegion getTexture (Enemy.EnemyStatus stat, float delta ) {
    /* Mundo complitado */

        stateTime += delta;
        return currAnimation.getKeyFrame( stateTime, true );
    /*
        Animation nextAnimation = null;

        switch ( stat ) {
            case ATTACK:
                nextAnimation = attack;
                break;
            case MOVE_RIGHT:
                nextAnimation = move_right;
                break;
        }

        stateTime += delta;

        if ( currAnimation.isAnimationFinished(stateTime )) {
            stateTime = 0;
            status = stat;
            currAnimation = nextAnimation;
        }

        return currAnimation.getKeyFrame( stateTime, true );
    */
    }
}

