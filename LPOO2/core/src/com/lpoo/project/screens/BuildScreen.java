package com.lpoo.project.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.lpoo.project.MyGame;
import com.lpoo.project.animations.Animator;
import com.lpoo.project.animations.Map;
import com.lpoo.project.animations.TrapAnimation;
import com.lpoo.project.logic.Game;
import com.lpoo.project.logic.Trap;

/**
 * Created by Vasco on 03/06/2016.
 */
public class BuildScreen implements Screen {

    private MyGame myGame;
    private Game game;
    private Map map;

    private OrthographicCamera camera;
    private OrthographicCamera hudCamera;

    private Music music;

    private Rectangle[] rectangles;
    private Rectangle advance;
    private Rectangle back;

    private Texture grid;
    private Texture play;
    private Texture exit;
    private Animator trapDraw;

    private int xPos = 450;
    private static final int yPos = 250;
    private static final int h = 500, w = 890;
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

        camera = new OrthographicCamera(w, h);
        hudCamera = new OrthographicCamera(w, h);

        music = Gdx.audio.newMusic(Gdx.files.internal("Come and Find Me.mp3"));
        music.setLooping(true);
        music.setVolume(myGame.getVolume()/100f);
        music.play();

        grid = new Texture("Grid.png");
        play = new Texture("PlayButton.png");
        exit = new Texture("ExitButton.png");

        trapDraw = new TrapAnimation( game, "Trap\\trap1.atlas", 0, 0, 0 );

        advance = new Rectangle( screenWidth - 100, screenHeight - 50, 50, 50);
        back = new Rectangle( screenWidth - 50, screenHeight - 50, 50, 50);
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
        if( tmp >= 450f && tmp <= 3650f )
            xPos = tmp;

        xTouch = (int)pos.x;
        yTouch = (int)pos.y;
    }

    public void touchUp( int screenX, int screenY ) {
        Vector2 pos = getRelativePosition( screenX, screenY );
        xTouch = (int)pos.x;
        yTouch = (int)pos.y;

        Rectangle hitbox = new Rectangle(screenX,screenHeight - screenY, 5, 5);
        if( advance.overlaps(hitbox))
            myGame.changeScreen(MyGame.States.PLAY);
        else if( back.overlaps(hitbox))
            myGame.changeScreen(MyGame.States.MENU);

        if( select && pos.y >= 144 && pos.y <= 272 && pos.x > 250 && pos.x < 3578 ) {
            int tmp = (int)pos.x - 250;
            tmp /= 128;
            game.addTrap( (int) rectangles[tmp].getX(), (int) rectangles[tmp].getY(), 128, 128, tmp );
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
        myGame.batch.setProjectionMatrix( camera.combined );
        //myGame.batch.setProjectionMatrix( hudCamera.combined );
        myGame.batch.begin();

        camera.position.set( xPos , yPos, 0 );
        camera.update();

        myGame.batch.draw( map.getSky(), 0, 0 );
        myGame.batch.draw( map.getTerrain(), 0, 0);

        Trap[] traps = game.getTraps();

        for( int i = 0; i < rectangles.length; i++ ) {
            if( traps[i] == null )
                myGame.batch.draw(grid, rectangles[i].getX(), rectangles[i].getY());
            else {
                trapDraw.setIndex(i);
                System.out.println("" + i);
                myGame.batch.draw(trapDraw.getTexture(delta), traps[i].getPosition().x, traps[i].getPosition().y);
            }
        }

        myGame.batch.setProjectionMatrix( hudCamera.combined );
        hudCamera.position.set( w / 2, h / 2, 0 );
        hudCamera.update();

        myGame.batch.draw(play, w - 100, h - 50);
        myGame.batch.draw(exit, w - 50, h - 50);

        myGame.batch.end();
    }

    public Vector2 getRelativePosition( int x, int y ) {
        return new Vector2( xPos + (w * x / screenWidth) - w / 2,
                            yPos - (h * y / screenHeight) + h / 2 );
    }

    @Override
    public void resize(int width, int height) {
        screenHeight = height;
        screenWidth = width;
        advance.setPosition(width - 100, height - 50);
        back.setPosition(width - 50, height - 50);

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
        music.dispose();
        map.dispose();
    }

    public void setVolume(float v) {
        music.setVolume(v);
    }
}
