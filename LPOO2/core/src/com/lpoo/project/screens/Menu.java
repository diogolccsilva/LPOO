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

    private MyGame myGame;
    private Rectangle play, highScore, exit;
    private OrthographicCamera menuCamera;
    private Texture background;
    private Music music;

    private static final int menuH = 256, menuW = 453;

    public Menu(MyGame myGame) {
        this.myGame = myGame;

        play = new Rectangle( 235, 116, 130, 35 );
        highScore = new Rectangle( 235, 163, 130, 35 );
        exit = new Rectangle( 235, 210, 130, 35 );


        OrthographicCamera c = myGame.getCache().getMenuCamera();
        if( c == null ) {
            menuCamera = new OrthographicCamera(menuW, menuH);
            myGame.getCache().setMenuCamera(menuCamera);
        } else menuCamera = c;

        menuCamera.position.set( menuW / 2, menuH / 2, 0 );
        menuCamera.update();


        Texture b = myGame.getCache().getMenuBackground();
        if( b == null ) {
            background = new Texture("Back.jpg");
            myGame.getCache().setMenuBackground(background);
        } else background = b;


        Music m = myGame.getCache().getMenuAudio();
        if( m == null ) {
            music = Gdx.audio.newMusic(Gdx.files.internal("A Night Of Dizzy Spells.mp3"));
            myGame.getCache().setMenuAudio(music);
        } else music = m;

        music.setLooping(true);
        music.setVolume(myGame.getVolume()/100f);
        music.play();
    }

    public boolean touchUp(int screenX, int screenY) {
        Rectangle rect = new Rectangle( getRelativeX(screenX), getRelativeY(screenY), 5, 5 );
        if( rect.overlaps(play))
            myGame.changeScreen(MyGame.States.HERO);
        else if ( rect.overlaps(exit))
            myGame.changeScreen(MyGame.States.EXIT);
        return true;
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
        myGame.batch.setProjectionMatrix( menuCamera.combined );

        myGame.batch.begin();
        myGame.batch.draw(background, 0, 0);
        myGame.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        music.pause();
    }

    @Override
    public void resume() {
        music.play();
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        music.stop();
    }

    public void setVolume(float v) {
        music.setVolume(v);
    }
}
