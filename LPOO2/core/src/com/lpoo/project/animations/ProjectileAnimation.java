package com.lpoo.project.animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.lpoo.project.logic.Game;
import com.lpoo.project.logic.Projectile.ProjectileStatus;

/**
 * Class that creates the projectile's animation
 * This class implements the interface Disposable
 */
public class ProjectileAnimation extends Animator {

    private static final int EXPLODE_INDEX = 0;
    private static final int ARRAY_SIZE = 1;
    /**
     * Velocity of the projectile's explosion
     */
    private final float explode_speed = 1/10f;

    /**
     * Projectile's "time of life"
     */
    //private float stateTime;

    /**
     * Animation which represents the explosion of the projectile
     */
    //private Animation explode;
    /**
     *  Loads images from texture atlases that represents the movement of the bullet / projectile
     */
    //private TextureAtlas bullet;

    /**
     * Constructor for the ProjectileAnimator class
     * @param path Path where is saved the TextureAtlas of the projectile's movement
     */
    public ProjectileAnimation(Game game, String path, int index ) {
        super(game, ARRAY_SIZE, ARRAY_SIZE, index);

        textures[EXPLODE_INDEX] = new TextureAtlas( Gdx.files.internal( path ) );
        animations[EXPLODE_INDEX] = new Animation( explode_speed, textures[EXPLODE_INDEX].getRegions() );
    }

    /**
     * Function that verifies if the projectile's animation is finished
     * @return True if the projectile's animation is finished, False if it isn't
     */
    public boolean isFinished() {
        return explode_speed * 4 <= stateTime;
    }

    /**
     * Gets the current texture of the animation
     * @param delta Increasing time
     * @return TextureRegion to be drawn on the screen
     */
    public TextureRegion getTexture( float delta ) {
        ProjectileStatus stat = game.getProjectiles().get(index).getState();
        if( stat == ProjectileStatus.HIT_TARGET) {
            stateTime += delta;
            return animations[EXPLODE_INDEX].getKeyFrame(stateTime, false);
        }
        return animations[EXPLODE_INDEX].getKeyFrames()[0];
    }
}
