package com.lpoo.project.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.lpoo.project.MyGame;

/**
 * Class that creates the screen where the user can choose the hero
 * This class implements the interface Screen
 */
public class HeroMenu implements Screen {

    /**
     * Principal game where the screen will be placed
     */
    private MyGame myGame;

    /**
     * Music used in this menu
     */
    private Music music;

    /**
     * Title's renders bitmap fonts
     */
    private BitmapFont title;
    /**
     * Text's renders bitmap fonts
     */
    private BitmapFont text;

    /**
     * Texture of the selection button
     */
    private Texture selected;
    /**
     * Texture of the "not selected" button
     */
    private Texture notSelected;

    /**
     * First choice's rectangle
     */
    private Rectangle opt1;
    /**
     * Second choice's rectangle
     */
    private Rectangle opt2;
    /**
     * Third choice's rectangle
     */
    private Rectangle opt3;

    /**
     * Hero's option
     */
    private int opt;

    /**
     * Menu's height
     */
    private static final int h = 765;
    /**
     * Menu's width
     */
    private static final int w = 1360;

    /**
     * Constructor for the class HeroMenu
     * @param myGame Principal game where the menu will be placed
     */
    public HeroMenu ( MyGame myGame ) {
        this.myGame = myGame;

        myGame.camera.position.set( myGame.w / 2, myGame.h / 2, 0 );
        myGame.camera.update();

        //Initialize font and store it in cache
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Font\\slkscr.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 100;
        title = generator.generateFont(parameter);
        title.setColor(Color.BLACK);

        parameter.size = 30;
        text = generator.generateFont(parameter);
        generator.dispose();

        selected = new Texture("Selected.png");
        notSelected = new Texture("NotSelected.png");
        opt = -1;

        opt1 = new Rectangle(619, 523, 700, 200);
        opt2 = new Rectangle(619, 282, 700, 200);
        opt3 = new Rectangle(619, 41, 700, 200);
    }

    /**
     * Getter for the y's relative position
     * @param y Y coordinate
     * @return The y's relative position
     */
    public float getRelativeY(int y) {
        return h * y / Gdx.graphics.getHeight();
    }

    /**
     * Getter for the x's relative position
     * @param x x coordinate
     * @return The x's relative position
     */
    public float getRelativeX(int x) {
        return w * x / Gdx.graphics.getWidth();
    }

    /**
     * Called when the screen was touched or a mouse button was released
     * @param screenX The x coordinate, origin is in the upper left corner
     * @param screenY The y coordinate, origin is in the upper left corner
     */
    public void touchUp( int screenX, int screenY ) {
        Rectangle rect = new Rectangle( getRelativeX(screenX), h - getRelativeY(screenY), 20, 20 );
        if( rect.overlaps(opt1))
            opt = 0;
        else if ( rect.overlaps(opt2))
            opt = 1;
        else if ( rect.overlaps(opt3))
            opt = 2;
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
        Gdx.gl.glClearColor(0.516f, 0.516f, 0.516f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Set batch to only draw what the camera sees
        myGame.batch.setProjectionMatrix( myGame.camera.combined );
        myGame.batch.begin();

        title.draw(myGame.batch, "CHOOSE", 41, 550);
        title.draw(myGame.batch, "YOUR", 41, 425);
        title.draw(myGame.batch, "HERO", 41, 300);

        text.draw(myGame.batch, "HEALTH: " + 300, 670, 700);
        text.draw(myGame.batch, "RESISTANCE: " + 10, 670, 665);
        text.draw(myGame.batch, "MOVEMENT SPEED: " + 80, 670, 630);
        text.draw(myGame.batch, "ATTACK SPEED: " + 1, 670, 595);
        text.draw(myGame.batch, "ATTACK DAMAGE: " + 10, 670, 560);

        text.draw(myGame.batch, "HEALTH: " + 150, 670, 459);
        text.draw(myGame.batch, "RESISTANCE: " + 5, 670, 424);
        text.draw(myGame.batch, "MOVEMENT SPEED: " + 100, 670, 389);
        text.draw(myGame.batch, "ATTACK SPEED: " + 0.7, 670, 354);
        text.draw(myGame.batch, "ATTACK DAMAGE: " + 20, 670, 319);

        text.draw(myGame.batch, "HEALTH: " + 100, 670, 218);
        text.draw(myGame.batch, "RESISTANCE: " + 3, 670, 183);
        text.draw(myGame.batch, "MOVEMENT SPEED: " + 200, 670, 148);
        text.draw(myGame.batch, "ATTACK SPEED: " + 0.4, 670, 113);
        text.draw(myGame.batch, "ATTACK DAMAGE: " + 12, 670, 78);

        myGame.batch.draw( opt == 2 ? selected: notSelected, 619, 41, 700, 200 );
        myGame.batch.draw( opt == 1 ? selected: notSelected, 619, 282, 700, 200 );
        myGame.batch.draw( opt == 0 ? selected: notSelected, 619, 523, 700, 200 );

        myGame.batch.end();

        if( opt != -1 ) {
            myGame.setSelectedHeroIndex(opt);
            myGame.changeScreen(MyGame.States.BUILD);
        }
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
        music.pause();
    }

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
    public void hide() {

    }

    @Override
    /**
     * Called when the screen is destroyed
     */
    public void dispose() {

    }
}
