package com.lpoo.project.animations;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Vasco on 01/06/2016.
 */
public class LifeBar {

    private TextureRegion life_bar, death_bar;

    public LifeBar( ) {
        TextureAtlas atlas = new TextureAtlas( "Life-bar\\life_bar.atlas" );
        life_bar = atlas.getRegions().get(0);
        death_bar = atlas.getRegions().get(1);
    }
}
