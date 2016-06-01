package com.lpoo.project.storage;

import java.io.*;
import com.google.gson.*;
import com.lpoo.project.logic.Hero;

/**
 * Created by Diogo on 5/20/2016.
 */
public class GameFiles {

    private GsonBuilder gbuilder;
    private Hero[] heroes;

    GameFiles(){
        gbuilder = new GsonBuilder();

    }

    public void loadEnemies() throws IOException{
        /*try(Reader reader = new InputStreamReader(GameFiles.class.getResourceAsStream(""), "UTF-8")){
            Gson gson = new GsonBuilder().create();
            Person p = gson.fromJson(reader, Person.class);
            System.out.println(p);
        }*/
    }

    public void loadHeroes(){

    }
}
