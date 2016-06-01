package com.lpoo.project.animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.lpoo.project.logic.Projectile.ProjectileStatus;
import com.lpoo.project.screens.PlayScreen;

/**
 * Created by Vasco on 01/06/2016.
 */
public class ProjectileAnimation implements Disposable {

    private final float explode_speed = 1/10f;

    private float stateTime;

    private Animation explode;
    private TextureAtlas bullet;

    public ProjectileAnimation( String path ) {
        stateTime = 0;
        bullet = new TextureAtlas( Gdx.files.internal( path ) );
        explode = new Animation( explode_speed, bullet.getRegions() );
    }

    public boolean isFinished() {

        return explode_speed * 4 <= stateTime;
    }

    public TextureRegion getTexture ( ProjectileStatus stat, float delta ) {
        if( stat == ProjectileStatus.HIT_TRAGET ) {
            stateTime += delta;
            return explode.getKeyFrame(stateTime, false);
        }
        return explode.getKeyFrames()[0];
    }


    @Override
    public void dispose() {
        bullet.dispose();
    }
}
