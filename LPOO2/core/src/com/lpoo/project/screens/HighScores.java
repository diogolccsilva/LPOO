package com.lpoo.project.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Circle;
import com.lpoo.project.MyGame;
import com.lpoo.project.storage.GameFiles;

import java.util.Vector;

/**
 * Class that creates the screen where the user can see the high scores
 * This class implements the interface Screen
 */
public class HighScores implements Screen {

    /**
     * Principal game where the screen will be placed
     */
    private MyGame myGame;

    /**
     * Music used in this menu
     */
    private Music music;

    /**
     * Text's renders bitmap fonts
     */
    private BitmapFont text;

    /**
     * Vector with the top 5 scores of the game
     */
    private Vector<Integer> topScores;

    /**
     * Background's texture
     */
    private Texture background;

    /**
     * Circle that represents the back button
     */
    private Circle back;

    /**
     * A camera with orthographic projection
     */
    private OrthographicCamera highScoresCamera;

    /**
     * Menu's height
     */
    private static final int highScoresH = 256;

    /**
     * Menu's weight
     */
    private static final int highScoresW = 453;

    /**
     * Constructor for the class HighScores
     * @param myGame Main game where the HighScores will be placed
     */
    public HighScores(MyGame myGame){
        this.myGame = myGame;

        back = new Circle(107, 127, 32);

        highScoresCamera = new OrthographicCamera(highScoresW, highScoresH);
        highScoresCamera.position.set( highScoresW / 2, highScoresH / 2, 0 );
        highScoresCamera.update();


        //Initialize font and store it in cache
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Font\\slkscr.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        text = generator.generateFont(parameter);
        generator.dispose();


        background = new Texture("HighScores.jpg");
        topScores = GameFiles.loadScore(5);
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
        Gdx.gl.glClearColor((float) 0.5, (float) 0.5, (float) 0.5, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Set batch to only draw what the camera sees
        myGame.batch.setProjectionMatrix(highScoresCamera.combined);
        myGame.batch.begin();

        myGame.batch.draw(background,0,0);

        /*for (int i = 0;i<5;i++){
            int n = i+1;
            int k = i*30;
            if( topScores.size() <= i )
                break;

            text.draw(myGame.batch, "#" + n + "    " + topScores.elementAt(i), 220, 150-k);
        }*/

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
    public void pause() {
        //music.pause();
    }

    @Override
    /**
     * Called when the screen is resumed from a paused state
     */
    public void resume() {
        //music.play();
    }

    @Override
    /**
     * Hide's the screen
     */
    public void hide() {

    }

    @Override
    /**
     * Called when the screen is destroyed
     */
    public void dispose() {

    }

    /**
     * Getter for the y's relative position
     * @param y Y coordinate
     * @return The y's relative position
     */
    public float getRelativeY(int y ) {
        return highScoresH * y / Gdx.graphics.getHeight();
    }

    /**
     * Getter for the x's relative position
     * @param x x coordinate
     * @return The x's relative position
     */
    public float getRelativeX( int x ) {
        return highScoresW * x / Gdx.graphics.getWidth();
    }

    /**
     * Called when the screen was touched or a mouse button was released
     * @param screenX The x coordinate, origin is in the upper left corner
     * @param screenY The y coordinate, origin is in the upper left corner
     * @return True if the input was processed, False if it wasn't
     */
    public boolean touchUp(int screenX, int screenY) {
        Circle circ = new Circle( getRelativeX(screenX), getRelativeY(screenY), 32);
        if( circ.overlaps(back))
            myGame.changeScreen(MyGame.States.MENU);
        return true;
    }
}
