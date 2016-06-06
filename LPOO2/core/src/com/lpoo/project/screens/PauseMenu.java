package com.lpoo.project.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.lpoo.project.MyGame;


public class PauseMenu implements Screen{

    private MyGame myGame;
    private Rectangle mainMenu, back;
    private OrthographicCamera menuCamera;
    private Texture background;
    private Music music;

    private static final int menuH = 256, menuW = 453;

    public PauseMenu(MyGame myGame) {
        this.myGame = myGame;

        mainMenu = new Rectangle( 169, 109, 116, 36 );
        back = new Rectangle( 169, 165, 116, 36 );

        menuCamera = new OrthographicCamera( menuW, menuH );
        menuCamera.position.set( menuW / 2, menuH / 2, 0 );
        menuCamera.update();

        Texture b = myGame.getCache().getMenuBackground();
        if( b != null ) {
            background = new Texture("Menu2.jpg");
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

        //menuCamera.position.set( menuW / 2, menuH / 2, 0 );
        //menuCamera.update();

        myGame.batch.begin();
        myGame.batch.draw(background, 0, 0);
        myGame.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
        myGame.changeScreen(MyGame.States.PLAY);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        music.stop();
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return true;
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Rectangle rect = new Rectangle( getRelativeX(screenX), getRelativeY(screenY), 20, 20 );
        if( rect.overlaps(mainMenu))
            myGame.changeScreen(MyGame.States.EXIT);
        else if ( rect.overlaps(back))
            resume();
        return true;
    }

    public void setVolume(float v) {
        music.setVolume(v);
    }
}

