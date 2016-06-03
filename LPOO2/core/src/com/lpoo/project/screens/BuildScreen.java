package com.lpoo.project.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.lpoo.project.MyGame;
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
    private BitmapFont font;

    private OrthographicCamera camera;
    private Texture grid;
    private Texture play;
    private Rectangle[] rectangles;
    private Rectangle advance;
    private TrapAnimation trapDraw;

    private int xPos = 450;
    private final int yPos = 250;
    private final int h = 500, w = 890;

    private int xTouch = 0, yTouch = 0;
    private boolean select = false;

    public BuildScreen( MyGame myGame, Game game ) {
        this.myGame = myGame;
        this.game = game;
        map = new Map();
        font = new BitmapFont();

        camera = new OrthographicCamera(w, h);
        trapDraw = new TrapAnimation( "Trap\\trap1.atlas", 0, 0 );
        grid = new Texture("Grid.png");
        rectangles = new Rectangle[26];
        advance = new Rectangle( xPos + 200, yPos + 100, 18, 25);
        play = new Texture("PlayButton.png");

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

        if( advance.overlaps(new Rectangle(pos.x, pos.y, 20, 20)))
            myGame.changeScreen(MyGame.States.PLAY);

        if( select && pos.y >= 144 && pos.y <= 272 && pos.x > 250 && pos.x < 3578 ) {
            int tmp = (int)pos.x - 250;
            tmp /= 128;
            System.out.println("Tmp:" + tmp);
            game.addTrap( new Trap( game, (int) rectangles[tmp].getX(), (int) rectangles[tmp].getY(), 128, 128, 20 ), tmp );
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

        myGame.batch.begin();

        camera.position.set( xPos , yPos, 0 );
        camera.update();

        myGame.batch.draw(map.getSky(), 0,0);
        myGame.batch.draw( map.getTerrain(), 0, 0);

        Trap[] traps = game.getTraps();

        for( int i = 0; i < rectangles.length; i++ ) {
            if( traps[i] == null )
                myGame.batch.draw(grid, rectangles[i].getX(), rectangles[i].getY());
            else
                myGame.batch.draw(trapDraw.getTexture(Trap.TrapStatus.WAIT, 0), traps[i].getPosition().x, traps[i].getPosition().y);
        }

        advance.setX( xPos + 200 );
        advance.setY( yPos + 100 );
        myGame.batch.draw(play, advance.getX(), advance.getY());

        font.draw(myGame.batch, "" + xTouch + "-" + yTouch, xPos, yPos + 100);

        myGame.batch.end();
    }

    public Vector2 getRelativePosition( int x, int y ) {
        return new Vector2( xPos + (w * x / Gdx.graphics.getWidth()) - w / 2,
                            yPos - (h * y / Gdx.graphics.getHeight()) + h / 2 );
    }

    @Override
    public void resize(int width, int height) {

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

    }
}
