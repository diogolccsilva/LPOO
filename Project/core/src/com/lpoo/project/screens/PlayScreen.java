package com.lpoo.project.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.lpoo.project.MyGame;
import com.lpoo.project.logic.Game;

/**
 * Created by Vasco on 13/05/2016.
 */
public class PlayScreen implements Screen, InputProcessor {

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

        Gdx.input.setInputProcessor(this); //Indicate that this class handles the inputs
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor((float)0.5, (float)0.5, (float)0.5, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        font.draw( game.batch, str, game.screenWidth / 2 , game.screenHeight / 2 );
        play.render( game.batch );

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
