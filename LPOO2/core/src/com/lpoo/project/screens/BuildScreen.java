package com.lpoo.project.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.StringBuilder;
import com.lpoo.project.MyGame;
import com.lpoo.project.animations.Animator;
import com.lpoo.project.animations.Map;
import com.lpoo.project.animations.TrapAnimation;
import com.lpoo.project.logic.Game;
import com.lpoo.project.logic.Trap;

/**
 * Class that creates the screen where the player can build the traps
 * This class implements the interface Screen
 */
public class BuildScreen implements Screen {

    /**
     * Principal game where the screen will be created
     */
    private MyGame myGame;
    /**
     * Game where the build screen will be placed
     */
    private Game game;
    /**
     * Build screen's map
     */
    private Map map;
    /**
     * Music of the build screen
     */
    private Music music;

    /**
     * Renders bitmap fonts
     */
    private BitmapFont font;

    /**
     * Array with the button's rectangles
     */
    private Rectangle[] rectangles;
    /**
     * Rectangle of the advance's button
     */
    private Rectangle advance;
    /**
     * Rectangle of the back's button
     */
    private Rectangle back;

    /**
     * Grid's texture
     */
    private Texture grid;
    /**
     * Gold's texture
     */
    private Texture gold;
    /**
     * Texture of the robot's icon
     */
    private Texture robotIcon;
    /**
     * Play's button's texture
     */
    private Texture play;
    /**
     * Exit's button's texture
     */
    private Texture exit;
    /**
     * Trap's animation
     */
    private Animator trapDraw;

    /**
     * Screen's x position
     */
    private int xPos = 700;
    /**
     * Screen's y position
     */
    private static final int yPos = 400;
    /**
     * Screen's width
     */
    private int screenWidth;
    /**
     * Screen's height
     */
    private int screenHeight;

    /**
     * X touch's coordinate
     */
    private int xTouch = 0;
    /**
     * Y touch's coordinate
     */
    private int yTouch = 0;
    /**
     * Boolean that represents if the traps were selected
     */
    private boolean select = false;

    /**
     * Constructor for the class BuildScreen
     * @param myGame Principal game where the screen will be placed
     * @param game Game where the screen will be placed
     */
    public BuildScreen( MyGame myGame, Game game ) {
        this.myGame = myGame;
        this.game = game;
        this.game.changeState(Game.GameStatus.BUILDING);

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        Map mp = myGame.getCache().getMap();
        if( mp == null ) {
            map = new Map();
            myGame.getCache().setMap(map);
        } else map = mp;

        myGame.hudCamera.position.set( myGame.w / 2, myGame.h / 2, 0 );
        myGame.hudCamera.update();

        //Initialize custom font
        BitmapFont f = myGame.getCache().getFont();
        if( f == null ) {
            //Initialize font and store it in cache
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Font\\slkscr.ttf"));
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.size = 30;
            font = generator.generateFont(parameter);
            generator.dispose();
            myGame.getCache().setFont(font);
        } else
            font = f;

        Music m = myGame.getCache().getBuildAudio();
        if( m == null ) {
            music = Gdx.audio.newMusic(Gdx.files.internal("Come and Find Me.mp3"));
            myGame.getCache().setBuildAudio(music);
        }
        else music = m;

        music.setLooping(true);
        music.setVolume(myGame.getVolume()/100f);
        music.play();

        grid = new Texture(Gdx.files.internal("Grid.png"));

        Texture g = myGame.getCache().getGoldIcon();
        if( g == null ) {
            gold = new Texture(Gdx.files.internal("Gold.png"));
            myGame.getCache().setGoldIcon(gold);
        } else gold = g;
        g = myGame.getCache().getRobotIcon();
        if( g == null ) {
            FileHandle file = Gdx.files.internal("Robot_Icon.png");
            robotIcon = new Texture(Gdx.files.internal("Robot_Icon.png"));
            myGame.getCache().setRobotIcon(robotIcon);
        } else robotIcon = g;

        play = new Texture(Gdx.files.internal("PlayButton.png"));
        exit = new Texture(Gdx.files.internal("ExitButton.png"));

        trapDraw = new TrapAnimation( game, "Trap\\trap1.atlas", 0, 0 );

        advance = new Rectangle( myGame.w - 105, myGame.h - 55, 35, 35);
        back = new Rectangle( myGame.w - 55, myGame.h - 55, 35, 35);
        rectangles = new Rectangle[26];

        int x = 250;
        for( int i = 0; i < rectangles.length; i++ ) {
            rectangles[i] = new Rectangle( x, 144, 128, 128 );
            x += 128;
        }
    }

    /**
     * Called when the screen was touched or a mouse button was pressed
     * @param screenX The x coordinate, origin is in the upper left corner
     * @param screenY The y coordinate, origin is in the upper left corner
     */
    public void touchDown( int screenX, int screenY ) {
        Vector2 pos = getRelativePosition( screenX, screenY );
        xTouch = (int) pos.x;
        yTouch = (int) pos.y;
        select = true;
    }

    /**
     * Called when a finger or the mouse was dragged
     * @param screenX The x coordinate, origin is in the upper left corner
     * @param screenY The y coordinate, origin is in the upper left corner
    */
    public void touchDragged( int screenX, int screenY ) {
        Vector2 pos = getRelativePosition( screenX, screenY );
        int deltaX = xTouch - (int) pos.x;

        if( deltaX < 5 && deltaX > -5 )
            deltaX = 0;
        else if( deltaX > 20 )
            deltaX = 20;
        else if( deltaX < -20 )
            deltaX = -20;

        if( deltaX < 7 && deltaX > -7 )
            select = true;
        else
            select = false;

        int tmp = xPos + deltaX;
        if( tmp >= 700 && tmp <= 3400 )
            xPos = tmp;

        xTouch = (int)pos.x;
        yTouch = (int)pos.y;
    }

    /**
     * Called when a finger was lifted or a mouse button was released
     * @param screenX The x coordinate, origin is in the upper left corner
     * @param screenY The y coordinate, origin is in the upper left corner
     */
    public void touchUp( int screenX, int screenY ) {
        Vector2 pos = getRelativePosition( screenX, screenY );
        xTouch = (int)pos.x;
        yTouch = (int)pos.y;

        pos = getRelativePositionScreen( screenX, screenY );
        Rectangle hitbox = new Rectangle(pos.x,pos.y, 5, 5);
        if( advance.overlaps(hitbox))
            myGame.changeScreen(MyGame.States.PLAY);
        else if( back.overlaps(hitbox))
            myGame.changeScreen(MyGame.States.MENU);

        if( select && yTouch >= 144 && yTouch <= 272 && xTouch > 250 && xTouch < 3578 ) {
            int tmp = xTouch - 250;
            tmp /= 128;
            game.setTrap( (int) rectangles[tmp].getX(), (int) rectangles[tmp].getY(), 128, 128, tmp );
        }
        select = false;
    }

    /**
     * Getter for the relative position
     * @param x X coordinate
     * @param y Y coordinate
     * @return The relative position
     */
    public Vector2 getRelativePosition( int x, int y ) {
        return new Vector2( xPos + (myGame.w * x / screenWidth) - myGame.w / 2,
                            yPos - (myGame.h * y / screenHeight) + myGame.h / 2 );
    }

    /**
     * Getter for the relative position of the screen
     * @param x X coordinate
     * @param y Y coordinate
     * @return The relative position of the screen
     */
    public Vector2 getRelativePositionScreen( int x, int y ) {
        return new Vector2( myGame.w * x / screenWidth, myGame.h  - myGame.h * y / screenHeight );
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
        myGame.batch.setProjectionMatrix( myGame.camera.combined );
        myGame.batch.begin();

        myGame.camera.position.set( xPos , yPos, 0 );
        myGame.camera.update();

        myGame.batch.draw( map.getSky(), 0, 0 );
        myGame.batch.draw( map.getTerrain(), 0, 0);

        Trap[] traps = game.getTraps();

        for( int i = 0; i < rectangles.length; i++ ) {
            if( traps[i] == null )
                myGame.batch.draw(grid, rectangles[i].getX(), rectangles[i].getY());
            else {
                trapDraw.setIndex(i);
                myGame.batch.draw(trapDraw.getTexture(delta), traps[i].getPosition().x, traps[i].getPosition().y);
            }
        }

        myGame.batch.setProjectionMatrix( myGame.hudCamera.combined );

        int drawY = myGame.h - 50;
        int hudY = myGame.h - 70;
        myGame.batch.draw(gold, 50, hudY);
        font.draw(myGame.batch, "" + game.getMoney(), 100, drawY);
        myGame.batch.draw(robotIcon, 200, hudY);

        StringBuilder str = new StringBuilder();
        str.append(game.getnEnemiesWon());
        str.append("/");
        str.append(game.getMaxEnemiesWon());

        font.draw(myGame.batch, str, 250, drawY);
        myGame.batch.draw(play, myGame.w - 100, drawY);
        myGame.batch.draw(exit, myGame.w - 50, drawY);

        myGame.batch.end();
    }

    @Override
    /**
     * Called when the screen is resized
     * @param width Screen's width
     * @param height Screen's height
     */
    public void resize(int width, int height) {
        screenHeight = height;
        screenWidth = width;
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
        play.dispose();
        exit.dispose();
        grid.dispose();
        trapDraw.dispose();
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
