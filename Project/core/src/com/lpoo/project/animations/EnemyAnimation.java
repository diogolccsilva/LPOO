package com.lpoo.project.animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.lpoo.project.logic.Enemy.EnemyStatus;

/**
 * Class that creates the enemies' animation
 * This class implements the interface Disposable
 */
public class EnemyAnimation implements Disposable {

    /**
     * Enemy's status
     */
    private EnemyStatus status;
    /**
     * Current animation of the enemy
     */
    private Animation currAnimation;
    /**
     * Animation which represents the attack of the enemy
     */
    private Animation attack;
    /**
     * Animation which represents the enemy moving to the right
     */
    private Animation move_right;
    /**
     * Animation which represents the enemy's death
     */
    private Animation dead;
    /**
     *  Loads images from texture atlases that represents the attack of the enemies
     */
    private TextureAtlas attackTextures;
    /**
     *  Loads images from texture atlases that represents the enemy moving to the right
     */
    private TextureAtlas move_rightTextures;
    /**
     *  Loads images from texture atlases that represents the enemy's death
     */
    private TextureAtlas deadTextures;
    /**
     * Time given to the life's status of the enemy
     */
    private float stateTime;

    /**
     * Constructor for the EnemyAnimator class
     * @param attackPath Path where is saved the TextureAtlas of the enemy's attack
     * @param movePath Path where is saved the TextureAtlas of the enemy moving to the right
     * @param attackSpeed Enemy's velocity's attack
     * @param moveSpeed Velocity of the enemy when he is moving
     */
    public EnemyAnimation( String attackPath, String movePath, float attackSpeed, float moveSpeed ) {
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

    /**
     * Gets the current enemy's status
     * @return the enemy's status
     */
    public EnemyStatus getStatus() {
        return status;
    }

    /**
     * Sets the speed of an animation
     * @param stat Enemy's status
     * @param speed New speed to be changed
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
     * Gets the current texture of the animation
     * @param stat Enemy's status
     * @param delta Increasing time
     * @return TextureRegion to be drawn on the screen
     */
    public TextureRegion getTexture (EnemyStatus stat, float delta ) {

        Animation nextAnimation = null;

        switch ( stat ) {
            case ATTACK:
                nextAnimation = attack;
                break;
            case MOVE_RIGHT:
                nextAnimation = move_right;
                break;
            case DEAD:
                nextAnimation = attack;
                break;
        }

        stateTime += delta;

        if ( stat == EnemyStatus.DEAD || currAnimation.isAnimationFinished(stateTime) ) {
            stateTime = 0;
            status = stat;
            currAnimation = nextAnimation;
        }

        return currAnimation.getKeyFrame( stateTime, true );
    }

    /**
     * Function that verifies if the enemy's animation is finished
     * @param state Enemy's status
     * @return True if the enemy's animation is finished, False if it isn't
     */
    public boolean isFinished( EnemyStatus state ) {
        return state == status && currAnimation.isAnimationFinished(stateTime);
    }

    @Override
    /**
     * Releases all textures of the enemy
     */
    public void dispose() {
        attackTextures.dispose();
        move_rightTextures.dispose();
    }
}

