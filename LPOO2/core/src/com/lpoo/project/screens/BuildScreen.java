package com.lpoo.project.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
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
     *
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
     * A camera with orthographic projection
     */
    //private OrthographicCamera camera;
    /**
     *
     */
    //private OrthographicCamera hudCamera;

    /**
     * Music of the build screen
     */
    private Music music;

    private BitmapFont font;

    private Rectangle[] rectangles;
    private Rectangle advance;
    private Rectangle back;

    private Texture grid;
    private Texture gold;
    private Texture robotIcon;
    private Texture play;
    private Texture exit;
    private Animator trapDraw;

    private int xPos = 700;
    private static final int yPos = 400;
    private int screenWidth, screenHeight;

    private int xTouch = 0, yTouch = 0;
    private boolean select = false;

    public BuildScreen( MyGame myGame, Game game ) {
        this.myGame = myGame;
        this.game = game;
        this.game.changeState(Game.GameStatus.BUILDING);

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        map = new Map();

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

        grid = new Texture("Grid.png");

        Texture g = myGame.getCache().getGoldIcon();
        if( g == null ) {
            gold = new Texture("Gold.png");
            myGame.getCache().setGoldIcon(gold);
        } else gold = g;
        g = myGame.getCache().getRobotIcon();
        if( g == null ) {
            robotIcon = new Texture("Robot_Icon.png");
            myGame.getCache().setRobotIcon(robotIcon);
        } else robotIcon = g;

        play = new Texture("PlayButton.png");
        exit = new Texture("ExitButton.png");

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

    public void touchDown( int screenX, int screenY ) {
        Vector2 pos = getRelativePosition( screenX, screenY );
        xTouch = (int) pos.x;
        yTouch = (int) pos.y;
        select = true;
    }

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

    @Override
    public void show() {

    }

    @Override
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
        font.draw(myGame.batch, "" + game.getnEnemiesWon(), 250, drawY);
        myGame.batch.draw(play, myGame.w - 100, drawY);
        myGame.batch.draw(exit, myGame.w - 50, drawY);

        myGame.batch.end();
    }

    public Vector2 getRelativePosition( int x, int y ) {
        return new Vector2( xPos + (myGame.w * x / screenWidth) - myGame.w / 2,
                            yPos - (myGame.h * y / screenHeight) + myGame.h / 2 );
    }

    public Vector2 getRelativePositionScreen( int x, int y ) {
        return new Vector2( myGame.w * x / screenWidth, myGame.h  - myGame.h * y / screenHeight );
    }


    @Override
    public void resize(int width, int height) {
        screenHeight = height;
        screenWidth = width;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        play.dispose();
        exit.dispose();
        grid.dispose();
        music.stop();
    }

    public void setVolume(float v) {
        music.setVolume(v);
    }
}
