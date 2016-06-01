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

    private PlayScreen game;
    private float stateTime;
    private ProjectileStatus state;

    private Animation explode;
    private TextureAtlas bullet;

    public ProjectileAnimation( PlayScreen game, String path, float explode_speed ) {
        this.game = game;
        stateTime = 0;
        state = ProjectileStatus.TRAVELLING;

        bullet = new TextureAtlas( Gdx.files.internal( path ) );
        explode = new Animation( explode_speed, bullet.getRegions() );
    }

    public ProjectileStatus getState() {
        return state;
    }

    public boolean isFinished() {
        return explode.isAnimationFinished(stateTime);
    }

    public TextureRegion getTexture ( ProjectileStatus stat, float delta ) {
        if( stat == ProjectileStatus.HIT_TRAGET ) {
            stateTime += delta;
            return explode.getKeyFrame(stateTime, true);
        }
        return explode.getKeyFrames()[0];
    }


    @Override
    public void dispose() {
        bullet.dispose();
    }
}
