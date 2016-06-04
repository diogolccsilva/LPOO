package com.lpoo.project.animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.lpoo.project.logic.Game;
import com.lpoo.project.logic.Trap.TrapStatus;
import com.lpoo.project.screens.PlayScreen;

/**
 * Class that creates the trap's animation
 * This class implements the interface Disposable
 */
public class TrapAnimation extends Animator {

    private static final int ATTACK_INDEX = 0;
    private static final int HEAT_UP_INDEX = 1;
    private static final int ANIMATIONS_SIZE = 2;
    private static final int TEXTURES_SIZE = 1;
    /**
     * Trap's status
     */
    //private TrapStatus state;
    /**
     * Current animation of the trap
     */
    private Animation currAnimation;

   //private Animation heatUpAnimation;
    /**
     * Trap's animation when it is attacking
     */
    //private Animation attack;
    /**
     *  Loads images from texture atlases that represents the trap recharging
     */
    private TextureRegion waitTexture;
    /**
     * Trap's "time of life"
     */
    //private float stateTime;

    /**
     * Constructor for the TrapAnimator class
     * @param path Path where is saved the TextureAtlas of the trap
     * @param attackSpeed Trap's velocity of the attack
     * @param rechargeSpeed Trap's velocity of moving
     */
    public TrapAnimation(Game game, String path, float attackSpeed, float rechargeSpeed, int index ) {
        super(game, ANIMATIONS_SIZE, TEXTURES_SIZE, index );
        //state = TrapStatus.WAIT;

        textures[TEXTURES_SIZE - 1] = new TextureAtlas( Gdx.files.internal( path ) );

        TextureRegion[] attackTexts = new TextureRegion[2]; //textures.getRegions()
        attackTexts[0] = textures[TEXTURES_SIZE - 1].getRegions().get(2);
        attackTexts[1] = textures[TEXTURES_SIZE - 1].getRegions().get(3);

        TextureRegion[] heatUpTexts = new TextureRegion[2];
        heatUpTexts[0] = textures[TEXTURES_SIZE - 1].getRegions().get(0);
        heatUpTexts[1] = textures[TEXTURES_SIZE - 1].getRegions().get(1);

        waitTexture = textures[TEXTURES_SIZE - 1].getRegions().get(4);

        animations[ATTACK_INDEX] = new Animation( attackSpeed, attackTexts );
        animations[HEAT_UP_INDEX] = new Animation( 1/20f, heatUpTexts);

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
                animations[ATTACK_INDEX].setFrameDuration( speed );
                break;
            case HEATUP:
                animations[HEAT_UP_INDEX].setFrameDuration( speed );
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
        TrapStatus stat = game.getTraps()[index].getState();

        stateTime += delta;

        switch (stat) {
            case ATTACK:
                nextAnimation = animations[ATTACK_INDEX];
                break;
            case HEATUP:
                nextAnimation = animations[HEAT_UP_INDEX];
                break;
        }

        if ((currAnimation != null && currAnimation.isAnimationFinished(stateTime)) ||
                currAnimation == null) {
            if (nextAnimation == null) {
                //state = stat;
                stateTime = 0;
                currAnimation = null;
                return waitTexture;
            } else {
                //state = stat;
                stateTime = 0;
                currAnimation = nextAnimation;
            }
        }

        return currAnimation.getKeyFrame(stateTime, true);
    }
}
