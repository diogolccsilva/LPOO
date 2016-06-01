package com.lpoo.project.animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.lpoo.project.logic.Enemy.EnemyStatus;
import com.lpoo.project.screens.PlayScreen;

/**
 * Created by Vasco on 27/05/2016.
 */
public class EnemyAnimation implements Disposable {
    private PlayScreen game;

    private EnemyStatus status;
    private Animation currAnimation;
    private Animation attack, move_right, dead;
    private TextureAtlas attackTextures, move_rightTextures, deadTextures;
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
        status = EnemyStatus.MOVE_RIGHT;

        attackTextures = new TextureAtlas( Gdx.files.internal( attackPath ) );
        attack = new Animation( attackSpeed, attackTextures.getRegions() );

        move_rightTextures = new TextureAtlas( Gdx.files.internal( movePath ) );
        move_right = new Animation( moveSpeed, move_rightTextures.getRegions() );

        //deadTextures = new TextureAtlas( Gdx.files.internal( deadPath ));
        dead = new Animation( attackSpeed, move_rightTextures.getRegions() );

        currAnimation = move_right;
    }

    public EnemyStatus getStatus() {
        return status;
    }

    /**
     * @biref Sets the speed of an animation
     * @param stat
     * @param speed
     */
    public void setAttackSpeed (EnemyStatus stat, float speed ) {

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
    public TextureRegion getTexture (EnemyStatus stat, float delta ) {
    /* Mundo complitado */

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
    }

    public boolean isFinished( EnemyStatus state ) {
        return state == status && currAnimation.isAnimationFinished(stateTime);
    }

    @Override
    public void dispose() {
        attackTextures.dispose();
        move_rightTextures.dispose();
    }
}

