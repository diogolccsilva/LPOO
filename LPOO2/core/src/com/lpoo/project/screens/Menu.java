package com.lpoo.project.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.GL20;
import com.lpoo.project.MyGame;

/**
 * Created by Vasco on 11/05/2016.
 */
public class Menu implements Screen{

    private MyGame game;
    private Rectangle play, instructions, exit;
    private OrthographicCamera menuCamera;
    private Texture background;
    private Music music;

    private static final int menuH = 256, menuW = 453;

    public Menu(MyGame game ) {
        this.game = game;

        play = new Rectangle( 235, 116, 130, 35 );
        instructions = new Rectangle( 235, 163, 130, 35 );
        exit = new Rectangle( 235, 210, 130, 35 );

        menuCamera = new OrthographicCamera( menuW, menuH );
        menuCamera.position.set( menuW / 2, menuH / 2, 0 );
        menuCamera.update();

        background = new Texture("Back.jpg");

        music = Gdx.audio.newMusic(Gdx.files.internal("A Night Of Dizzy Spells.mp3"));
        music.setLooping(true);
        music.setVolume(game.getVolume()/100f);
        music.play();
    }

    public float getRelativeY( int y ) {
        return menuH * y / Gdx.graphics.getHeight();
    }

    public float getRelativeX( int x ) {
        return menuW * x / Gdx.graphics.getWidth();
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
        game.batch.setProjectionMatrix( menuCamera.combined );

        //menuCamera.position.set( menuW / 2, menuH / 2, 0 );
        //menuCamera.update();

        game.batch.begin();
        game.batch.draw(background, 0, 0);
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
        dispose();
    }

    @Override
    public void dispose() {
        music.stop();
        music.dispose();
        background.dispose();
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return true;
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Rectangle rect = new Rectangle( getRelativeX(screenX), getRelativeY(screenY), 20, 20 );
        if( rect.overlaps(play))
            game.changeScreen(MyGame.States.BUILD);
        else if ( rect.overlaps(exit))
            game.changeScreen(MyGame.States.EXIT);
        return true;
    }

    public void setVolume(float v) {
        music.setVolume(v);
    }
}
