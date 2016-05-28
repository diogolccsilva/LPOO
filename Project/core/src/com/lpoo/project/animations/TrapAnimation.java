package com.lpoo.project.animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.lpoo.project.logic.Trap;
import com.lpoo.project.screens.PlayScreen;

/**
 * Created by Vasco on 27/05/2016.
 */
public class TrapAnimation implements Disposable {

    private PlayScreen game;

    private Trap.TrapStatus status;
    private Animation currAnimation;
    private Animation attack, wait, recharge;
    private TextureAtlas attackTextures, waitTextures,rechargeTextures;
    private float stateTime;

    /**
     * @brief Constructor for the Animator class
     * @param attackPath
     * @param movePath
     * @param attackSpeed
     * @param moveSpeed
     */
    public TrapAnimation( PlayScreen game, String attackPath, String movePath, float attackSpeed, float moveSpeed ) {
        this.game = game;
        stateTime = 0;
        status = Trap.TrapStatus.WAIT;

        attackTextures = new TextureAtlas( Gdx.files.internal( attackPath ) );
        attack = new Animation( attackSpeed, attackTextures.getRegions() );

        currAnimation = wait;
    }

    /**
     * @biref Sets the speed of an animation
     * @param stat
     * @param speed
     */
    public void setAttackSpeed ( Trap.TrapStatus stat, float speed ) {

        switch ( stat ) {
            case ATTACK:
                attack.setFrameDuration( speed );
                break;
            case WAIT:
                wait.setFrameDuration( speed );
                break;
            case RECHARGE:
                recharge.setFrameDuration( speed );
                break;
        }
    }

    /**
     * @brief Getter for the current texture of the animation
     * @param stat
     * @param delta
     * @return TextureRegion to be drawn on the screen
     */
    public TextureRegion getTexture (Trap.TrapStatus stat, float delta ) {
        /* Mundo complitado */

        Animation nextAnimation = null;

        switch ( stat ) {
            case ATTACK:
                nextAnimation = attack;
                break;
            case WAIT:
                nextAnimation = wait;
                break;
            case RECHARGE:
                nextAnimation = recharge;
                break;
        }

        stateTime += delta;

        if (currAnimation.isAnimationFinished(stateTime) || ( currAnimation == wait && nextAnimation != wait )) {
            stateTime = 0;
            status = stat;
            currAnimation = nextAnimation;
        }

        return currAnimation.getKeyFrame( stateTime, true );
    }

    @Override
    public void dispose() {
        attackTextures.dispose();
        waitTextures.dispose();
        rechargeTextures.dispose();
    }
}
