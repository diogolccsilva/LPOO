package com.lpoo.project.animations;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.lpoo.project.logic.Game;

/**
 * Created by Vasco on 04/06/2016.
 */
public abstract class Animator implements Disposable{

    protected float stateTime;

    protected Animation[] animations;

    protected TextureAtlas[] textures;

    protected Game game;

    protected int index;

    public Animator( Game game, int nAnimations, int nTextures, int index ) {
        this.game = game;
        animations = new Animation[nAnimations];
        textures = new TextureAtlas[nTextures];
        stateTime = 0;
        this.index = index;
    }

    public TextureRegion getTexture( float delta ) {
        stateTime += delta;
        return animations[0].getKeyFrame( delta, true );
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isFinished() {
        return false;
    }

    @Override
    public void dispose() {
        for( int i = 0; i < textures.length; i++ )
            textures[i].dispose();
    }
}
