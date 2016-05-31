package com.lpoo.project.animations;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Vasco on 31/05/2016.
 */
public class Map implements Disposable {

    private Texture map;
    private Texture spawnWall;

    public Map() {
        map = new Texture("Map\\Map.png");
        spawnWall = new Texture("Map\\SpawnWall.png");
    }

    public final Texture getMap() {
        return map;
    }

    public final Texture getSpawnWall() {
        return spawnWall;
    }

    @Override
    public void dispose() {
        map.dispose();
        spawnWall.dispose();
    }
}
