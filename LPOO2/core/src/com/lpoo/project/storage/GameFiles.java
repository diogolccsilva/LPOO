package com.lpoo.project.storage;

import java.io.*;

import java.util.Arrays;
import java.util.Vector;
import com.google.gson.*;
import com.lpoo.project.logic.Enemy;
import com.lpoo.project.logic.Hero;

/**
 * Created by Diogo on 5/20/2016.
 */
public class GameFiles {

    static String heroesPath = "heroes.json";
    static String enemiesPath = "enemies.json";

    public static void saveHeroes(Vector<Hero> heroes) {
        File file = new File(heroesPath);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(heroesPath), "UTF-8")) {
            Gson gson = new GsonBuilder().create();
            for (int i = 0; i < heroes.size(); i++) {
                gson.toJson(heroes.elementAt(i), writer);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static Vector<Hero> loadHeroes() {
        Vector<Hero> heroes = new Vector<Hero>();
        try (Reader reader = new InputStreamReader(GameFiles.class.getResourceAsStream(heroesPath), "UTF-8")){
            Gson gson = new GsonBuilder().create();
            Hero[] h = gson.fromJson(reader, Hero[].class);
            heroes.addAll(Arrays.asList(h));
        } catch (IOException e){
            e.printStackTrace();
        }
        return heroes;
    }

    public static void saveEnemies(Vector<Enemy> enemies) {
        File file = new File(enemiesPath);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(enemiesPath), "UTF-8")) {
            Gson gson = new GsonBuilder().create();
            for (int i = 0; i < enemies.size(); i++) {
                gson.toJson(enemies.elementAt(i), writer);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static Vector<Enemy> loadEnemies() {
        Vector<Enemy> enemies = new Vector<Enemy>();
        try (Reader reader = new InputStreamReader(GameFiles.class.getResourceAsStream(enemiesPath), "UTF-8")){
            Gson gson = new GsonBuilder().create();
            Enemy[] ve = gson.fromJson(reader, Enemy[].class);
            enemies.addAll(Arrays.asList(ve));
        } catch (IOException e){
            e.printStackTrace();
        }
        return enemies;
    }
}
