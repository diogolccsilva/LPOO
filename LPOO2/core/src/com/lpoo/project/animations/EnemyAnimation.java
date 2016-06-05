package com.lpoo.project.animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.lpoo.project.logic.Enemy;
import com.lpoo.project.logic.Enemy.EnemyStatus;
import com.lpoo.project.logic.Game;

/**
 * Class that creates the enemies' animation
 * This class implements the interface Disposable
 */
public class EnemyAnimation extends Animator implements Disposable {

    private static final int ATTACK_INDEX = 0;
    private static final int MOVE_INDEX = 1;
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
     * Animation which represents the attack of the enemy
     */
    //private Animation attack;
    /**
     * Animation which represents the enemy moving to the right
     */
    //private Animation move_right;
    /**
     * Animation which represents the enemy's death
     */
    //private Animation dead;
    /**
     *  Loads images from texture atlases that represents the attack of the enemies
     */
    //private TextureAtlas attackTextures;
    /**
     *  Loads images from texture atlases that represents the enemy moving to the right
     */
    //private TextureAtlas move_rightTextures;
    /**
     *  Loads images from texture atlases that represents the enemy's death
     */
    //private TextureAtlas deadTextures;
    /**
     * Time given to the life's status of the enemy
     */
    //private float stateTime;

    /**
     * Constructor for the EnemyAnimator class
     * @param attackPath Path where is saved the TextureAtlas of the enemy's attack
     * @param movePath Path where is saved the TextureAtlas of the enemy moving to the right
     * @param attackSpeed Enemy's velocity's attack
     * @param moveSpeed Velocity of the enemy when he is moving
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

    public void reset() {
        stateTime = 0;
        state = EnemyStatus.MOVE_RIGHT;
        currAnimation = animations[MOVE_INDEX];
    }

    @Override
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

