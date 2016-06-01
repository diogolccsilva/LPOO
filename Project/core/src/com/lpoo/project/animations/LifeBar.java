package com.lpoo.project.animations;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Class which creates the life's bar
 */
public class LifeBar {

    /**
     * Rectangular area of the life's bar's texture
     */
    private TextureRegion life_bar;
    /**
     * Rectangular area of the death's bar's texture
     */
    private TextureRegion death_bar;

    /**
     * Constructor for the LifeBar class
     */
    public LifeBar( ) {
        TextureAtlas atlas = new TextureAtlas( "Life-bar\\life_bar.atlas" );
        life_bar = atlas.getRegions().get(0);
        death_bar = atlas.getRegions().get(1);
    }
}
