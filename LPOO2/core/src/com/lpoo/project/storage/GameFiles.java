package com.lpoo.project.storage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lpoo.project.logic.CharacterStats;
import com.lpoo.project.logic.TrapStats;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

/**
 * Class that deals with the respective files created
 */
public class GameFiles {

    /**
     * Path of hero's file
     */
    private static String heroesPath = "heroes.json";
    /**
     * Path of enemies' file
     */
    private static String enemiesPath = "enemies.json";
    /**
     * Path of  traps' file
     */
    private static String trapsPath = "traps.json";
    /**
     * Path of high scores' file
     */
    private static String scoresPath = "Data/highscores.txt";

    /**
     * Function which allows the application to save the heroes into a file
     * @param stats Vector with all the heroes' properties
     */
    public static void saveHeroes(Vector<CharacterStats> stats) {
        File file = new File(heroesPath);
        try {
            file.delete();
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (Writer writer = new OutputStreamWriter(Gdx.files.internal(heroesPath).write(false), "UTF-8")) {
            Gson gson = new GsonBuilder().create();
            CharacterStats[] vs = new CharacterStats[stats.size()];
            stats.toArray(vs);
            gson.toJson(vs,writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function that loads the heroes from a file
     * @return A vector with the heroes' properties
     */
    public static Vector<CharacterStats> loadHeroes() {
        Vector<CharacterStats> stats = new Vector<>();
        try (Reader reader = new InputStreamReader(Gdx.files.internal(heroesPath).read(), "UTF-8")) {
            Gson gson = new GsonBuilder().create();
            CharacterStats[] vs = gson.fromJson(reader, CharacterStats[].class);
            stats.addAll(Arrays.asList(vs));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stats;
    }

    /**
     * Function which allows the application to save the enemies into a file
     * @param stats Vector with all the enemies' properties
     */
    public static void saveEnemies(Vector<CharacterStats> stats) {
        File file = new File(enemiesPath);
        try {
            file.delete();
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (Writer writer = new OutputStreamWriter(Gdx.files.internal(enemiesPath).write(false), "UTF-8")) {
            Gson gson = new GsonBuilder().create();
            CharacterStats[] vs = new CharacterStats[stats.size()];
            stats.toArray(vs);
            gson.toJson(vs,writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function that loads the enemies from a file
     * @return A vector with the enemies' properties
     */
    public static Vector<CharacterStats> loadEnemies() {
        Vector<CharacterStats> stats = new Vector<>();
        try (Reader reader = new InputStreamReader(Gdx.files.internal(enemiesPath).read(), "UTF-8")) {
            Gson gson = new GsonBuilder().create();
            CharacterStats[] vs = gson.fromJson(reader, CharacterStats[].class);
            stats.addAll(Arrays.asList(vs));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stats;
    }

    /**
     * Function which allows the application to save the traps into a file
     * @param stats Vector with all the traps' properties
     */
    public static void saveTraps(Vector<TrapStats> stats) {
        File file = new File(trapsPath);
        try {
            file.delete();
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (Writer writer = new OutputStreamWriter(Gdx.files.internal(trapsPath).write(false), "UTF-8")) {
            Gson gson = new GsonBuilder().create();
            TrapStats[] vs = new TrapStats[stats.size()];
            stats.toArray(vs);
            gson.toJson(vs,writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function that loads the traps from a file
     * @return A vector with the traps' properties
     */
    public static Vector<TrapStats> loadTraps() {
        Vector<TrapStats> stats = new Vector<>();
        try (Reader reader = new InputStreamReader(Gdx.files.internal(trapsPath).read(), "UTF-8")) {
            Gson gson = new GsonBuilder().create();
            TrapStats[] vs = gson.fromJson(reader, TrapStats[].class);
            stats.addAll(Arrays.asList(vs));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stats;
    }

    /**
     * Function which allows the application to save the tscores into a file
     * @param score Score to be saved
     */
    public static void saveScore(int score){
        FileHandle file = Gdx.files.local(scoresPath);
        Vector<Integer> v = new Vector<>();
        try {
            BufferedReader reader = file.reader(Integer.SIZE);
            String line;
            while ((line = reader.readLine()) != null) {
                v.add(Integer.parseInt(line));
            }
            v.add(score);
            Collections.sort(v);
            Collections.reverse(v);
            file.writeString("",false);
            for (int i = 0;i<v.size();i++){
                file.writeString(v.elementAt(i).toString()+'\n',true);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Function that loads the scores from a file
     * @param top Number of scores wanted
     * @return A vector with the scores
     */
    public static Vector<Integer> loadScore(int top){
        FileHandle file = Gdx.files.internal(scoresPath);
        if (!file.exists()){
            file.write(false);
        }
        Vector<Integer> v = new Vector<>();
        try {
            BufferedReader reader = file.reader(Integer.SIZE);
            String line;
            while ((line = reader.readLine()) != null) {
                v.add(Integer.parseInt(line));
                top--;
                if (top<1)
                    break;
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return v;
    }
}
