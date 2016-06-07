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
import com.badlogic.gdx.math.Vector2;
import com.lpoo.project.MyGame;
import com.lpoo.project.logic.CharacterStats;

import java.util.Vector;

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
     * Array of rectangles with the hero's options
     */
    private Rectangle[] opts;

    /**
     * Number of hero options
     */
    private int nOpts;

    private int xTouch,yTouch;
    private int yPos,xPos;
    private int yi;
    private int dy;

    /**
     * Hero's current selected option
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

        myGame.camera.position.set(myGame.w / 2, myGame.h / 2, 0);
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
        nOpts = myGame.getHeroes().size();
        opts = new Rectangle[nOpts];

        xPos = 619;
        yPos = 523;
        yi = yPos;
        dy = 0;

        for (int i = 0; i < nOpts; i++) {
            opts[i] = new Rectangle(xPos,yPos-i*241,700,200);
        }
        xTouch = 0;
        yTouch = 0;
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

    public void touchUp(int screenX, int screenY) {
        Vector2 pos = getRelativePosition( screenX, screenY );
        xTouch = (int) pos.x;
        yTouch = (int) pos.y;
        Rectangle rect = new Rectangle(xTouch, h-yTouch, 20, 20);
        for (int i = 0; i< nOpts; i++){
            if (rect.overlaps(opts[i])){
                opt = i;
                break;
            }
        }
    }

    public void touchDown(int screenX,int screenY){
        Vector2 pos = getRelativePosition( screenX, screenY );
        xTouch = (int) pos.x;
        yTouch = (int) pos.y;
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
        myGame.batch.setProjectionMatrix(myGame.camera.combined);
        myGame.batch.begin();

        title.draw(myGame.batch, "CHOOSE", 41, 550);
        title.draw(myGame.batch, "YOUR", 41, 425);
        title.draw(myGame.batch, "HERO", 41, 300);

        Vector<CharacterStats> stats = myGame.getHeroes();

        for (int i = 0; i< nOpts; i++){
            int k = i*241-dy;
            text.draw(myGame.batch, "HEALTH: " + stats.elementAt(i).getHealth(), 670, 700-k);
            text.draw(myGame.batch, "RESISTANCE: " + stats.elementAt(i).getResistance(), 670, 665-k);
            text.draw(myGame.batch, "MOVEMENT SPEED: " + stats.elementAt(i).getMovSpeed(), 670, 630-k);
            text.draw(myGame.batch, "ATTACK SPEED: " + stats.elementAt(i).getAttSpeed(), 670, 595-k);
            text.draw(myGame.batch, "ATTACK DAMAGE: " + stats.elementAt(i).getAttDamage(), 670, 560-k);
            myGame.batch.draw(notSelected,opts[i].getX(),opts[i].getY(),opts[i].getWidth(),opts[i].getHeight());
        }

        myGame.batch.end();

        if (opt != -1) {
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

    public Vector2 getRelativePosition(int x, int y) {
        return new Vector2(getRelativeX(x),getRelativeY(y));
    }

    public void touchDragged(int screenX, int screenY) {
        Vector2 pos = getRelativePosition( screenX, screenY );
        int deltaY = yTouch - (int)pos.y;

        if( deltaY < 5 && deltaY > -5 )
            deltaY = 0;
        else if( deltaY > 20 )
            deltaY = 20;
        else if( deltaY < -20 )
            deltaY = -20;

        int tmp = yPos + deltaY;
        System.out.println(tmp);
        if( tmp >= 523 && (tmp - (nOpts -1)*241) <= 41 ){
            yPos += deltaY;
            dy = yPos-yi;
        }

        for (int i = 0; i< nOpts; i++){
            opts[i].setPosition(xPos,yPos-i*241);
        }
    }
}
