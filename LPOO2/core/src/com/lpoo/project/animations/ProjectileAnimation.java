package com.lpoo.project.animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.lpoo.project.logic.Projectile.ProjectileStatus;

/**
 * Class that creates the projectile's animation
 * This class implements the interface Disposable
 */
public class ProjectileAnimation implements Disposable {

    /**
     * Velocity of the projectile's explosion
     */
    private final float explode_speed = 1/10f;

    /**
     * Projectile's "time of life"
     */
    private float stateTime;

    /**
     * Animation which represents the explosion of the projectile
     */
    private Animation explode;
    /**
     *  Loads images from texture atlases that represents the movement of the bullet / projectile
     */
    private TextureAtlas bullet;

    /**
     * Constructor for the ProjectileAnimator class
     * @param path Path where is saved the TextureAtlas of the projectile's movement
     */
    public ProjectileAnimation( String path ) {
        stateTime = 0;
        bullet = new TextureAtlas( Gdx.files.internal( path ) );
        explode = new Animation( explode_speed, bullet.getRegions() );
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
     * @param stat Projectile's status
     * @param delta Increasing time
     * @return TextureRegion to be drawn on the screen
     */
    public TextureRegion getTexture ( ProjectileStatus stat, float delta ) {
        if( stat == ProjectileStatus.HIT_TARGET) {
            stateTime += delta;
            return explode.getKeyFrame(stateTime, false);
        }
        return explode.getKeyFrames()[0];
    }


    @Override
    /**
     * Releases all textures of the projectile
     */
    public void dispose() {
        bullet.dispose();
    }
}
