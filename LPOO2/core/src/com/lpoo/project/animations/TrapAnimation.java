package com.lpoo.project.animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lpoo.project.logic.Game;
import com.lpoo.project.logic.Trap.TrapStatus;

/**
 * Class that creates the trap's animation
 * This class extends the class Animator
 */
public class TrapAnimation extends Animator {

    /**
     * Trap's attack's index
     */
    private static final int ATTACK_INDEX = 0;
    /**
     * Trap's heat up's index
     */
    private static final int HEAT_UP_INDEX = 1;
    /**
     * Size of the trap's animations' array
     */
    private static final int ANIMATIONS_SIZE = 2;
    /**
     * Size of the trap's textures' array
     */
    private static final int TEXTURES_SIZE = 1;

    /**
     * Current animation of the trap
     */
    private Animation currAnimation;

    //private Animation attack;
    /**
     *  Loads images from texture atlases that represents the trap recharging
     */
    private TextureRegion waitTexture;

    /**
     * Constructor for the TrapAnimator class
     * @param game Game where will be placed the animation
     * @param path Path where is saved the TextureAtlas of the trap
     * @param attackSpeed Trap's velocity of the attack
     * @param index Trap's index
     */
    public TrapAnimation(Game game, String path, float attackSpeed, int index ) {
        super(game, ANIMATIONS_SIZE, TEXTURES_SIZE, index );

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
     * @param delta Increasing value
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
