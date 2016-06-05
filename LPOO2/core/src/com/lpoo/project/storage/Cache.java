package com.lpoo.project.storage;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Disposable;
import com.lpoo.project.animations.EnemyAnimation;
import com.lpoo.project.animations.HeroAnimation;
import com.lpoo.project.animations.Map;

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
    /**
     * Instance of map used in the build screen and play screen
     */
    private Map map;
    /**
     * Texture used for the background of the menu screen
     */
    private Texture menuBackground;
    /**
     * Instance of EnemyAnimation to be used during the playing stage by a melee robot
     */
    private EnemyAnimation enemyMeleeAnimation;
    /**
     * Instance of EnemyAnimation to be used during the playing stage by a ranged robot
     */
    private EnemyAnimation enemyRangedAnimation;
    /**
     * Instance of HeroAnimation to be used during the playing stage by the hero
     */
    private HeroAnimation heroAnimation;
    /**
     * Texture used in the HUD to represent gold
     */
    private Texture goldIcon;
    /**
     * Texture used int the HUD to represent the robots
     */
    private Texture robotIcon;

    private static Cache ourInstance = new Cache();

    public static Cache getInstance() {
        return ourInstance;
    }

    private Cache() { }

    /**
     * Getter for the font used throughout the game
     * @return the custom font
     */
    public BitmapFont getFont() {
        return font;
    }

    /**
     * Setter for the font used throughout the game
     * @param font the new font to be stored
     */
    public void setFont(BitmapFont font) {
        this.font = font;
    }

    /**
     * Getter for the music used during the build screen
     * @return the music to be played
     */
    public Music getBuildAudio() {
        return buildAudio;
    }

    /**
     * Setter for the music used during the build screen
     * @param buildAudio  the music to be stored
     */
    public void setBuildAudio(Music buildAudio) {
        this.buildAudio = buildAudio;
    }

    /**
     * Getter for the music used during the menu screen
     * @return the music to be played
     */
    public Music getMenuAudio() {
        return menuAudio;
    }

    /**
     * Setter for the music used during the menu screen
     * @param menuAudio  the music to be stored
     */
    public void setMenuAudio(Music menuAudio) {
        this.menuAudio = menuAudio;
    }

    /**
     * Getter for the music used during the play screen
     * @return the music to be played
     */
    public Music getPlayAudio() {
        return playAudio;
    }

    /**
     * Setter for the music used during the play screen
     * @param playAudio the music to be stored
     */
    public void setPlayAudio(Music playAudio) {
        this.playAudio = playAudio;
    }

    /**
     * Getter for the map containing the textures to draw the map
     * @return the map
     */
    public Map getMap() {
        return map;
    }

    /**
     * Setter for the map containing the textures to draw the map
     * @param map the map to be stored
     */
    public void setMap(Map map) {
        this.map = map;
    }

    public Texture getMenuBackground() {
        return menuBackground;
    }

    public void setMenuBackground(Texture menuBackground) {
        this.menuBackground = menuBackground;
    }

    public EnemyAnimation getEnemyMeleeAnimation() {
        return enemyMeleeAnimation;
    }

    public void setEnemyMeleeAnimation(EnemyAnimation enemyAnimation) {
        this.enemyMeleeAnimation = enemyAnimation;
    }

    public EnemyAnimation getEnemyRangedAnimation() {
        return enemyRangedAnimation;
    }

    public void setEnemyRangedAnimation(EnemyAnimation enemyRangedAnimation) {
        this.enemyRangedAnimation = enemyRangedAnimation;
    }

    public HeroAnimation getHeroAnimation() {
        return heroAnimation;
    }

    public void setHeroAnimation(HeroAnimation heroAnimation) {
        this.heroAnimation = heroAnimation;
    }

    /**
     * Getter for the texture used to represent gold
     * @return the texture to be used in the HUD
     */
    public Texture getGoldIcon() {
        return goldIcon;
    }

    /**
     * Setter for the texture used to represent gold
     * @param goldIcon the texture to be stored
     */
    public void setGoldIcon(Texture goldIcon) {
        this.goldIcon = goldIcon;
    }

    /**
     * Getter for the texture used to represent the robots
     * @return the texture to be used in the HUD
     */
    public Texture getRobotIcon() {
        return robotIcon;
    }

    /**
     * Setter for the texture used to represent gold
     * @param robotIcon the texture to be stored
     */
    public void setRobotIcon(Texture robotIcon) {
        this.robotIcon = robotIcon;
    }

    @Override
    public void dispose() {
        if( font != null )
            font.dispose();
        if( menuAudio != null )
            menuAudio.dispose();
        if( playAudio != null )
            playAudio.dispose();
        if( buildAudio != null )
            buildAudio.dispose();
        if( map != null )
            map.dispose();
        if( enemyMeleeAnimation != null )
            enemyMeleeAnimation.dispose();
        if( enemyRangedAnimation != null )
            enemyRangedAnimation.dispose();
        if( heroAnimation != null )
            heroAnimation.dispose();
        if( menuBackground != null )
            menuBackground.dispose();
    }
}
