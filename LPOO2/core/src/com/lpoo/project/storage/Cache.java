package com.lpoo.project.storage;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Vasco on 05/06/2016.
 */
public class Cache implements Disposable {
    /**
     * Custom font used throughout all the project
     */
    private BitmapFont font;
    /**
     * Music used in the menu screen
     */
    private Music menuAudio;
    /**
     * Music used in the build screen
     */
    private Music buildAudio;
    /**
     * Music used in the play screen
     */
    private Music playAudio;

    private static Cache ourInstance = new Cache();

    public static Cache getInstance() {
        return ourInstance;
    }

    private Cache() { }

    public void setFont(BitmapFont font) {
        this.font = font;
    }

    public BitmapFont getFont() {
        return font;
    }

    public Music getBuildAudio() {
        return buildAudio;
    }

    public void setBuildAudio(Music buildAudio) {
        this.buildAudio = buildAudio;
    }

    public Music getMenuAudio() {
        return menuAudio;
    }

    public void setMenuAudio(Music menuAudio) {
        this.menuAudio = menuAudio;
    }

    public Music getPlayAudio() {
        return playAudio;
    }

    public void setPlayAudio(Music playAudio) {
        this.playAudio = playAudio;
    }

    @Override
    public void dispose() {
        font.dispose();
        menuAudio.dispose();
        playAudio.dispose();
        buildAudio.dispose();
    }
}
