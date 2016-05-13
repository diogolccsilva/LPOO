package com.lpoo.project.animations;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

/**
 * Created by Vasco on 13/05/2016.
 */
public class Animator {

    private Animation animation;
    private int frameSpeed;
    private int currTime;

    public Animator( Texture text, int texWidth, int texHeight, int nImg ) {

        int x = 0, y = 0;
        int nCol = text.getWidth() / (texWidth + 2);
        int nLine = text.getHeight() / (texHeight + 2);
        TextureRegion[][] tmp = TextureRegion.split( text, texWidth, texHeight );
        TextureRegion[] sTmp = new TextureRegion[ nImg ];
    }

}
