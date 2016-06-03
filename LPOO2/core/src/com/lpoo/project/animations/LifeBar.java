package com.lpoo.project.animations;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Class which creates the life's bar
 */
public class LifeBar {

    private static final TextureAtlas atlas = new TextureAtlas( "Life-bar\\life_bar.atlas" );
    /**
     * Rectangular area of the life's bar's texture
     */
    private static final TextureRegion life_bar = atlas.getRegions().get(1);
    /**
     * Rectangular area of the death's bar's texture
     */
    private static final TextureRegion death_bar = atlas.getRegions().get(0);

    /**
     * Constructor for the LifeBar class
     *//*
    public LifeBar( ) {
        TextureAtlas atlas = new TextureAtlas( "Life-bar\\life_bar.atlas" );
        life_bar = atlas.getRegions().get(0);
        death_bar = atlas.getRegions().get(1);
    }*/

    public static TextureRegion[] getTexture( int health, int max_health ) {
        int life_pixel = health * 38 / max_health;

        if( life_pixel == life_bar.getRegionWidth() - 2 ) {
            TextureRegion[] textureRegions = new TextureRegion[1];
            textureRegions[0] = life_bar;
            return textureRegions;
        }
        else if( life_pixel == 0 ) {
            TextureRegion[] textureRegions = new TextureRegion[1];
            textureRegions[0] = death_bar;
            return textureRegions;
        }

        TextureRegion[] textureRegions = new TextureRegion[2];
        textureRegions[0] = new TextureRegion(life_bar, 0, 0, life_pixel + 1, life_bar.getRegionHeight());
        textureRegions[1] = new TextureRegion(death_bar, life_pixel + 1, 0, life_bar.getRegionWidth(),  life_bar.getRegionHeight());
        return  textureRegions;
    }
}
