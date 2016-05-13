package com.lpoo.project.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.lpoo.project.MyGame;
import com.lpoo.project.logic.Game;
import com.lpoo.project.logic.Hero;

/**
 * Created by Vasco on 13/05/2016.
 */
public class PlayScreen implements Screen, InputProcessor {

    private OrthographicCamera camera;
    private MyGame game;
    private Game play;
    private BitmapFont font;
    private String str;

    public PlayScreen(MyGame game) {

        this.game = game;

        play = new Game();

        font = new BitmapFont();
        font.setColor(Color.BLACK);

        str = "Nothing";

        int h = 500, w = h * 16 / 9;
        camera = new OrthographicCamera( w, h );

        Gdx.input.setInputProcessor(this); //Indicate that this class handles the inputs
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor((float)0.5, (float)0.5, (float)0.5, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Hero hero = play.getHero();
        Vector2 hPos = hero.getPosition();
        Sprite hSprite = hero.SelectImg( play.getFire() );

        float xPos = hPos.x + hSprite.getWidth() / 2,
                yPos = hPos.y + hSprite.getHeight() / 2;

        game.batch.setProjectionMatrix( camera.combined );
        game.batch.begin();

        camera.position.set( xPos, yPos, 0 );
        camera.update();

        hero.SelectImg( play.getFire() ).draw( game.batch );
        font.draw( game.batch, str, xPos - game.screenWidth / 2 , yPos + game.screenHeight / 2 );

        game.batch.end();
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


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        str = "Touch down at " + screenX + " - " + screenY;
        play.touchDown( screenX, screenY );

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        str = "Touch up at " + screenX + " - " + screenY;
        play.touchUp( screenX, screenY );

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        play.touchDragged( screenX, screenY );

        return true;
    }


    /*
        Functions that are not used in android
    */

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
