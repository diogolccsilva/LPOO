package com.lpoo.project.animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.lpoo.project.logic.Trap;
import com.lpoo.project.screens.PlayScreen;

/**
 * Class that creates the trap's animation
 * This class implements the interface Disposable
 */
public class TrapAnimation implements Disposable {

    /**
     * PlayScreen where the game will be played
     */
    private PlayScreen game;

    /**
     * Trap's status
     */
    private Trap.TrapStatus status;
    /**
     * Current animation of the trap
     */
    private Animation currAnimation;
    /**
     * Trap's animation when it is attacking
     */
    private Animation attack;
    /**
     * Trap's animation when it is waiting
     */
    private Animation wait;
    /**
     * Trap's animation when it is recharging
     */
    private Animation recharge;
    /**
     *  Loads images from texture atlases that represents the attack of the trap
     */
    private TextureAtlas attackTextures;
    /**
     *  Loads images from texture atlases that represents the trap waiting
     */
    private TextureAtlas waitTextures;
    /**
     *  Loads images from texture atlases that represents the trap recharging
     */
    private TextureAtlas rechargeTextures;
    /**
     * Trap's "time of life"
     */
    private float stateTime;

    /**
     * @brief Constructor for the TrapAnimator class
     * @param attackPath Path where is saved the TextureAtlas of the trap's attack
     * @param movePath Path where is saved the TextureAtlas of the trap's moving
     * @param attackSpeed Trap's velocity of the attack
     * @param moveSpeed Trap's velocity of moving
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
     * Sets the speed of an animation
     * @param stat Trap's status
     * @param speed Trap's velocity
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
     * Getter for the current texture of the animation
     * @param stat Trap's status
     * @param delta Increasing time
     * @return TextureRegion to be drawn on the screen
     */
    public TextureRegion getTexture (Trap.TrapStatus stat, float delta ) {
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
    /**
     * Releases all textures of the trap
     */
    public void dispose() {
        attackTextures.dispose();
        waitTextures.dispose();
        rechargeTextures.dispose();
    }
}
