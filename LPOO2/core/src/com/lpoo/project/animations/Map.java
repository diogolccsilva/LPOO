package com.lpoo.project.animations;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

/**
 * Class that creates the map
 * This class implements the interface Disposable
 */
public class Map implements Disposable {

    /**
     * Texture which represents the map
     */
    private final Texture sky = new Texture("Map\\Sky.png");

    /**
     * Texture that represents the spawn wall
     */
    private final Texture terrain = new Texture("Map\\Terrain.png");

    /**
     * Getter for the map's texture
     * @return The map's texture
     */
    public final Texture getSky() {
        return sky;
    }

    /**
     * Getter for the spawn wall's texture
     * @return The spawn wall's texture
     */
    public final Texture getTerrain() {
        return terrain;
    }

    @Override
    /**
     * Releases all textures of the map
     */
    public void dispose() {
        sky.dispose();
        terrain.dispose();
    }
}
