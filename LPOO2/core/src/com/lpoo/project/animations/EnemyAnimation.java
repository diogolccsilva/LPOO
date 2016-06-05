package com.lpoo.project.animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lpoo.project.logic.Enemy.EnemyStatus;
import com.lpoo.project.logic.Game;

/**
 * Class that creates the enemies' animation
 * This class extends the class Animator
 */
public class EnemyAnimation extends Animator {

    /**
     * Enemy's attack's index
     */
    private static final int ATTACK_INDEX = 0;
    /**
     * Enemy's movement's index
     */
    private static final int MOVE_INDEX = 1;
    /**
     * Size of the enemy's array
     */
    private static final int ARRAY_SIZE = 2;

    /**
     * Enemy's status
     */
    private EnemyStatus state;
    /**
     * Current animation of the enemy
     */
    private Animation currAnimation;

    /**
     * Constructor for the EnemyAnimator class
     * @param attackPath Path where is saved the TextureAtlas of the enemy's attack
     * @param movePath Path where is saved the TextureAtlas of the enemy moving to the right
     * @param attackSpeed Enemy's velocity's attack
     * @param moveSpeed Velocity of the enemy when he is moving
     * @param index Enemy's index
     */
    public EnemyAnimation( Game game, String attackPath, String movePath, float attackSpeed, float moveSpeed, int index ) {
        super( game, ARRAY_SIZE, ARRAY_SIZE, index );

        stateTime = 0;
        state = EnemyStatus.MOVE_RIGHT;

        textures[ATTACK_INDEX] = new TextureAtlas( Gdx.files.internal( attackPath ) );
        animations[ATTACK_INDEX] = new Animation( attackSpeed, textures[ATTACK_INDEX].getRegions() );

        textures[MOVE_INDEX] = new TextureAtlas( Gdx.files.internal( movePath ) );
        animations[MOVE_INDEX] = new Animation( moveSpeed, textures[MOVE_INDEX].getRegions() );

        this.index = index;
        //deadTextures = new TextureAtlas( Gdx.files.internal( deadPath ));
        //dead = new Animation( attackSpeed, move_rightTextures.getRegions() );

        currAnimation = animations[MOVE_INDEX];
    }

    /**
     * Gets the current enemy's status
     * @return the enemy's status
     */
    public EnemyStatus getState() {
        return state;
    }

    /**
     * Sets the speed of an animation
     * @param stat Enemy's status
     * @param speed New speed to be changed
     */
    public void setAttackSpeed (EnemyStatus stat, float speed ) {

        switch ( stat ) {
            case ATTACK:
                animations[ATTACK_INDEX].setFrameDuration( speed );
                break;
            case MOVE_RIGHT:
                animations[MOVE_INDEX].setFrameDuration( speed );
                break;
        }
    }

    @Override
    /**
     * Setter for the enemy's index
     * @param index Enemy's index
     */
    public void setIndex(int index) {
        super.setIndex(index);
    }

    /**
     * Gets the current texture of the animation
     * @param delta Increasing time
     * @return TextureRegion to be drawn on the screen
     */
    public TextureRegion getTexture( float delta ) {
        Animation nextAnimation = null;
        EnemyStatus stat = game.getEnemies().get(index).getNextState();

        switch ( stat ) {
            case ATTACK:
                nextAnimation = animations[ATTACK_INDEX];
                break;
            case MOVE_RIGHT:
                nextAnimation = animations[MOVE_INDEX];
                break;
            case DEAD:
                nextAnimation = animations[ATTACK_INDEX];
                break;
        }

        stateTime += delta;

        if ( stat == EnemyStatus.DEAD || currAnimation.isAnimationFinished(stateTime) ) {
            stateTime = 0;
            state = stat;
            currAnimation = nextAnimation;
        }

        return currAnimation.getKeyFrame( stateTime, true );
    }

    /**
     * Resets the animation and changes the values of stateTime, state and currAnimation
     */
    public void reset() {
        stateTime = 0;
        state = EnemyStatus.MOVE_RIGHT;
        currAnimation = animations[MOVE_INDEX];
    }

    @Override
    /**
     * Clones the enemy's animation
     */
    protected Object clone() {
        Object clone = null;
        try {
            clone = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }


}

