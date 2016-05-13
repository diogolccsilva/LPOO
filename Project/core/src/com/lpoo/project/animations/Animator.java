package com.lpoo.project.animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Vasco on 13/05/2016.
 */
public class Animator {

   // private Animation animation;
    private Sprite[] sprites;
    private int animationSpeed;
    private float stateTime;

    public Animator( Texture text, int texWidth, int texHeight, int nImg, int speed ) {

        animationSpeed = speed;
        stateTime = 0f;

        sprites = new Sprite[nImg];

        TextureRegion[][] tmp = TextureRegion.split( text, texWidth, texHeight );

        int nLine = text.getHeight() / ( texHeight + 2 );
        int nCol = text.getWidth() / ( texWidth + 2 );

        for ( int l = 0; l < nLine; l++ ) {

            for ( int c = 0; c < nCol; c++ ) {

                if ( l + c >= nImg )
                //No need to break in the other for () because if there are no more images then there are no more lines
                    break;

                sprites[ l + c ] = new Sprite( tmp[l][c] );
            }
        }
    }

    public Sprite getImg ( ) {

        stateTime += Gdx.graphics.getDeltaTime();

        int index = (int) (animationSpeed / stateTime);

        return sprites[index];
    }

}
