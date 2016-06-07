package com.lpoo.project.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.GL20;
import com.lpoo.project.MyGame;

/**
 * Class that creates the initial menu's screen
 * This class implements the interface Screen
 */
public class Menu implements Screen{

    /**
     * Principal game where the menu will be placed
     */
    private MyGame myGame;
    /**
     * Play's button's rectangle
     */
    private Rectangle play;
    /**
     * High Score's button's rectangle
     */
    private Rectangle highScore;
    /**
     * Exit's button's rectangle
     */
    private Rectangle exit;
    /**
     * A camera with orthographic projection
     */
    private OrthographicCamera menuCamera;
    /**
     * Background's texture
     */
    private Texture background;
    /**
     * Music used in this menu
     */
    private Music music;

    /**
     * Menu's height
     */
    private static final int menuH = 256;
    /**
     * Menu's weight
     */
    private static final int menuW = 453;

    /**
     * Constructor for the class Menu
     * @param myGame Principal game where the menu will be placed
     */
    public Menu(MyGame myGame) {
        this.myGame = myGame;

        play = new Rectangle( 235, 116, 130, 35 );
        highScore = new Rectangle( 235, 163, 130, 35 );
        exit = new Rectangle( 235, 210, 130, 35 );


        OrthographicCamera c = myGame.getCache().getMenuCamera();
        if( c == null ) {
            menuCamera = new OrthographicCamera(menuW, menuH);
            myGame.getCache().setMenuCamera(menuCamera);
        } else menuCamera = c;

        menuCamera.position.set( menuW / 2, menuH / 2, 0 );
        menuCamera.update();


        Texture b = myGame.getCache().getMenuBackground();
        if( b == null ) {
            background = new Texture("Back.jpg");
            myGame.getCache().setMenuBackground(background);
        } else background = b;


        Music m = myGame.getCache().getMenuAudio();
        if( m == null ) {
            music = Gdx.audio.newMusic(Gdx.files.internal("A Night Of Dizzy Spells.mp3"));
            myGame.getCache().setMenuAudio(music);
        } else music = m;

        music.setLooping(true);
        music.setVolume(myGame.getVolume()/100f);
        music.play();
    }

    /**
     * Called when the screen was touched or a mouse button was released
     * @param screenX The x coordinate, origin is in the upper left corner
     * @param screenY The y coordinate, origin is in the upper left corner
     * @return True if the input was processed, False if it wasn't
     */
    public boolean touchUp(int screenX, int screenY) {
        Rectangle rect = new Rectangle( getRelativeX(screenX), getRelativeY(screenY), 5, 5 );
        if( rect.overlaps(play))
            myGame.changeScreen(MyGame.States.HERO);
        else if (rect.overlaps(highScore))
            myGame.changeScreen(MyGame.States.HIGHSCORE);
        else if ( rect.overlaps(exit))
            myGame.changeScreen(MyGame.States.EXIT);
        return true;
    }

    /**
     * Getter for the y's relative position
     * @param y Y coordinate
     * @return The y's relative position
     */
    public float getRelativeY( int y ) {
        return menuH * y / Gdx.graphics.getHeight();
    }

    /**
     * Getter for the x's relative position
     * @param x x coordinate
     * @return The x's relative position
     */
    public float getRelativeX( int x ) {
        return menuW * x / Gdx.graphics.getWidth();
    }

    @Override
    /**
     * Called when this screen becomes the current screen for a Game
     */
    public void show() {
    }

    @Override
    /**
     * Called when the screen should render itself
     * @param delta Difference between the last time of call and the current time
     */
    public void render(float delta) {
        //Clear screen with certain color
        Gdx.gl.glClearColor((float)0.5, (float)0.5, (float)0.5, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Set batch to only draw what the camera sees
        myGame.batch.setProjectionMatrix( menuCamera.combined );

        myGame.batch.begin();
        myGame.batch.draw(background, 0, 0);
        myGame.batch.end();
    }

    @Override
    /**
     * Called when the screen is resized
     * @param width Screen's width
     * @param height Screen's height
     */
    public void resize(int width, int height) {

    }

    @Override
    /**
     * Called when the screen is paused
     */
    public void pause() { }

    @Override
    /**
     * Called when the screen is resumed from a paused state
     */
    public void resume() {
        music.play();
    }

    @Override
    /**
     * Hide's the screen
     */
    public void hide() { }

    @Override
    /**
     * Called when the screen is destroyed
     */
    public void dispose() {
        music.stop();
    }

    /**
     * Setter for the music's volume
     * @param v New volume that will replace the old one
     */
    public void setVolume(float v) {
        music.setVolume(v);
    }
}
