package com.lpoo.project.storage;

import com.lpoo.project.MyGame;

import java.io.*;


/**
 * Created by Diogo on 5/20/2016.
 */
public class GameFiles {

    MyGame game;

    GameFiles(MyGame game){
        this.game = game;
    }

    public void loadEnemies() throws IOException{

    }

    public void loadHeroes() throws IOException{
        /*try(Reader reader = new InputStreamReader(GameFiles.class.getResourceAsStream("/heroes.json"), "UTF-8")){
            Gson gson = new GsonBuilder().create();
            Hero h = gson.fromJson(reader, Hero.class);
            game.addHero(h);
        }*/
    }
}
