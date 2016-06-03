package com.lpoo.project.animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.lpoo.project.logic.Trap.TrapStatus;
import com.lpoo.project.screens.PlayScreen;

/**
 * Class that creates the trap's animation
 * This class implements the interface Disposable
 */
public class TrapAnimation {

    /*
     * PlayScreen where the game will be played
     */
    private PlayScreen game;

    /**
     * Trap's status
     */
    private TrapStatus status;
    /**
     * Current animation of the trap
     */
    private Animation currAnimation;

    private Animation heatUpAnimation;
    /**
     * Trap's animation when it is attacking
     */
    private Animation attack;
    /**
     *  Loads images from texture atlases that represents the trap recharging
     */
    private TextureRegion waitTexture;
    /**
     * Trap's "time of life"
     */
    private float stateTime;

    /**
     * Constructor for the TrapAnimator class
     * @param path Path where is saved the TextureAtlas of the trap
     * @param attackSpeed Trap's velocity of the attack
     * @param rechargeSpeed Trap's velocity of moving
     */
    public TrapAnimation( PlayScreen game, String path, float attackSpeed, float rechargeSpeed ) {
        this.game = game;
        stateTime = 0;
        status = TrapStatus.WAIT;

        TextureAtlas textures = new TextureAtlas( Gdx.files.internal( path ) );
        TextureRegion[] attackTexts = new TextureRegion[2]; //textures.getRegions()
        attackTexts[0] = textures.getRegions().get(2);
        attackTexts[1] = textures.getRegions().get(3);
        TextureRegion[] heatUpTexts = new TextureRegion[2];
        heatUpTexts[0] = textures.getRegions().get(0);
        heatUpTexts[1] = textures.getRegions().get(1);
        waitTexture = textures.getRegions().get(4);
        attack = new Animation( attackSpeed, attackTexts );
        heatUpAnimation = new Animation( rechargeSpeed, heatUpTexts);

        currAnimation = null;
    }

    /**
     * Sets the speed of an animation
     * @param stat Trap's status
     * @param speed Trap's velocity
     */
    public void setAttackSpeed ( TrapStatus stat, float speed ) {
        switch ( stat ) {
            case ATTACK:
                attack.setFrameDuration( speed );
                break;
            case HEATUP:
                heatUpAnimation.setFrameDuration( speed );
                break;
        }
    }

    /**
     * Getter for the current texture of the animation
     * @param stat Trap's status
     * @param delta Increasing time
     * @return TextureRegion to be drawn on the screen
     */
    public TextureRegion getTexture (TrapStatus stat, float delta ) {
        Animation nextAnimation = null;
        stateTime += delta;

        switch (stat) {
            case ATTACK:
                nextAnimation = attack;
                break;
            case HEATUP:
                nextAnimation = heatUpAnimation;
                break;
        }

        if ((currAnimation != null && currAnimation.isAnimationFinished(stateTime)) ||
                currAnimation == null) {
            if (nextAnimation == null) {
                status = stat;
                stateTime = 0;
                currAnimation = null;
                return waitTexture;
            } else {
                status = stat;
                stateTime = 0;
                currAnimation = nextAnimation;
            }
        }

        return currAnimation.getKeyFrame(stateTime, true);
    }
}
