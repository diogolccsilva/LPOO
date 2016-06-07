package com.lpoo.project.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.lpoo.project.MyGame;
import com.lpoo.project.animations.Map;


/**
 * Class that creates the pause's menu
 * This class implements the interface Screen
 */
public class PauseMenu implements Screen{

    /**
     * Principal game where the menu will be placed
     */
    private MyGame myGame;
    /**
     * Main menu's button's rectangle
     */
    private Rectangle mainMenu;
    /**
     * Back's button's rectangle
     */
    private Rectangle back;
    /**
     * A camera with orthographic projection
     */
    private OrthographicCamera menuCamera;
    /**
     * Background's texture
     */
    private Texture background;
    /**
     * Map that represents the pause's menu
     */
    private Map map;

    /**
     * Menu's height
     */
    private static final int menuH = 256;
    /**
     * Menu's wight
     */
    private static final int menuW = 453;

    /**
     * Constructor for the class PauseMenu
     * @param myGame Principal game where the menu will placed
     */
    public PauseMenu(MyGame myGame) {
        this.myGame = myGame;

        mainMenu = new Rectangle( 169, 109, 116, 36 );
        back = new Rectangle( 169, 165, 116, 36 );

        OrthographicCamera c = myGame.getCache().getMenuCamera();
        if( c == null ) {
            menuCamera = new OrthographicCamera(menuW, menuH);
            myGame.getCache().setMenuCamera(menuCamera);
        } else menuCamera = c;

        menuCamera.position.set( menuW / 2, menuH / 2, 0 );
        menuCamera.update();

        background = new Texture("PauseMenu.png");

        Map mp = myGame.getCache().getMap();
        if (mp == null) {
            map = new Map();
            myGame.getCache().setMap(map);
        } else map = mp;

    }

    /**
     * Called when the screen was touched or a mouse button was released
     * @param screenX The x coordinate, origin is in the upper left corner
     * @param screenY The y coordinate, origin is in the upper left corner
     * @return True if the input was processed, False if it wasn't
     */
    public boolean touchUp(int screenX, int screenY) {
        Rectangle rect = new Rectangle( getRelativeX(screenX), getRelativeY(screenY), 5, 5 );
        if( rect.overlaps(mainMenu))
            myGame.changeScreen(MyGame.States.MENU);
        else if ( rect.overlaps(back))
            myGame.changeScreen(MyGame.States.PLAY);
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
        Gdx.gl.glClearColor((float) 0.5, (float) 0.5, (float) 0.5, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Set batch to only draw what the camera sees
        myGame.batch.setProjectionMatrix(myGame.camera.combined);
        myGame.batch.begin();

        //Draw hero's texture
        myGame.batch.draw(map.getSky(), 0, 0);
        myGame.batch.draw(map.getTerrain(), 0, 0);

        //Set batch to only draw what the camera sees
        myGame.batch.setProjectionMatrix( menuCamera.combined );

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
    public void resume() { }

    @Override
    /**
     * Hide's the screen
     */
    public void hide() {
        dispose();
    }

    @Override
    /**
     * Called when the screen is destroyed
     */
    public void dispose() {
    }
}

