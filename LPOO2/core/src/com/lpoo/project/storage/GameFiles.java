package com.lpoo.project.storage;

import java.io.*;

import java.util.Arrays;
import java.util.Vector;

import com.badlogic.gdx.assets.AssetManager;
import com.google.gson.*;
import com.lpoo.project.logic.CharacterStats;
import com.lpoo.project.logic.TrapStats;

public class GameFiles {

    static String heroesPath = "heroes.json";
    static String enemiesPath = "enemies.json";
    static String trapsPath = "traps.json";

    public static void saveHeroes(Vector<CharacterStats> stats) {
        File file = new File(heroesPath);
        try {
            file.delete();
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(heroesPath), "UTF-8")) {
            Gson gson = new GsonBuilder().create();
            CharacterStats[] vs = new CharacterStats[stats.size()];
            stats.toArray(vs);
            gson.toJson(vs,writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Vector<CharacterStats> loadHeroes() {
        Vector<CharacterStats> stats = new Vector<>();
        try (Reader reader = new InputStreamReader(new FileInputStream(heroesPath), "UTF-8")) {
            Gson gson = new GsonBuilder().create();
            CharacterStats[] vs = gson.fromJson(reader, CharacterStats[].class);
            stats.addAll(Arrays.asList(vs));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stats;
    }

    public static void saveEnemies(Vector<CharacterStats> stats) {
        File file = new File(enemiesPath);
        try {
            file.delete();
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (Writer writer = new OutputStreamWriter(new FileOutputStream(enemiesPath), "UTF-8")) {
            Gson gson = new GsonBuilder().create();
            CharacterStats[] vs = new CharacterStats[stats.size()];
            stats.toArray(vs);
            gson.toJson(vs,writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Vector<CharacterStats> loadEnemies() {
        Vector<CharacterStats> stats = new Vector<>();
        try (Reader reader = new InputStreamReader(new FileInputStream(enemiesPath), "UTF-8")) {
            Gson gson = new GsonBuilder().create();
            CharacterStats[] vs = gson.fromJson(reader, CharacterStats[].class);
            stats.addAll(Arrays.asList(vs));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stats;
    }

    public static void saveTraps(Vector<TrapStats> stats) {
        File file = new File(trapsPath);
        try {
            file.delete();
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(trapsPath), "UTF-8")) {
            Gson gson = new GsonBuilder().create();
            TrapStats[] vs = new TrapStats[stats.size()];
            stats.toArray(vs);
            gson.toJson(vs,writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Vector<TrapStats> loadTraps() {
        Vector<TrapStats> stats = new Vector<>();
        try (Reader reader = new InputStreamReader(new FileInputStream(trapsPath), "UTF-8")) {
            Gson gson = new GsonBuilder().create();
            TrapStats[] vs = gson.fromJson(reader, TrapStats[].class);
            stats.addAll(Arrays.asList(vs));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stats;
    }
}
