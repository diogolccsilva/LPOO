package com.lpoo.project.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.lpoo.project.MyGame;
import com.lpoo.project.animations.Map;

public class GameOver implements Screen{

    private MyGame myGame;
    private Circle back;
    private OrthographicCamera menuCamera;
    private Texture background;
    private Music music;
    private Map map;

    private static final int menuH = 256, menuW = 453;

    public GameOver(MyGame myGame) {
        this.myGame = myGame;

        back = new Circle(107, 127, 32);

        OrthographicCamera c = myGame.getCache().getMenuCamera();
        if( c == null ) {
            menuCamera = new OrthographicCamera(menuW, menuH);
            myGame.getCache().setMenuCamera(menuCamera);
        } else menuCamera = c;

        menuCamera.position.set( menuW / 2, menuH / 2, 0 );
        menuCamera.update();

        background = new Texture("gameOver.jpg");

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
        Gdx.gl.glClearColor((float) 0.5, (float) 0.5, (float) 0.5, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Set batch to only draw what the camera sees
        myGame.batch.setProjectionMatrix(myGame.camera.combined);
        myGame.batch.begin();

        //Set batch to only draw what the camera sees
        myGame.batch.setProjectionMatrix( menuCamera.combined );

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
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        music.stop();
    }

    public boolean touchUp(int screenX, int screenY) {
        Circle circ = new Circle( getRelativeX(screenX), getRelativeY(screenY), 32);
        if( circ.overlaps(back))
            myGame.changeScreen(MyGame.States.MENU);
        return true;
    }

    public void setVolume(float v) {
        music.setVolume(v);
    }
}

