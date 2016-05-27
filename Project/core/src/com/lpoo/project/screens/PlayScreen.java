package com.lpoo.project.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.lpoo.project.MyGame;
import com.lpoo.project.animations.Animator;
import com.lpoo.project.animations.EnemyAnimation;
import com.lpoo.project.animations.HeroAnimation;
import com.lpoo.project.logic.Enemy;
import com.lpoo.project.logic.Game;
import com.lpoo.project.logic.Hero;

/**
 * Created by Vasco on 13/05/2016.
 */
public class PlayScreen implements Screen, InputProcessor {

    private OrthographicCamera camera;
    private MyGame game;
    public Game play;

    private boolean pressed;

    private HeroAnimation hero_animations;
    private EnemyAnimation enemy_animations;
    private Texture map;

    public PlayScreen(MyGame game) {

        this.game = game;
        pressed = false;
        play = new Game();

        int h = 500, w = h * 16 / 9;
        camera = new OrthographicCamera( w, h );

        hero_animations = new HeroAnimation( this, "Hero\\hero1_fire.atlas", "Hero\\hero1_still.atlas", 1/10f, 1/3f );
        enemy_animations = new EnemyAnimation( this, "Hero\\hero1_fire.atlas", "Robot\\robot1_walk.atlas", 1/10f, 1/3f );
        map = new Texture( "Map.png");

        Gdx.input.setInputProcessor(this); //Indicate that this class handles the inputs
    }

    public Game getGame() {
        return play;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        /* UPDATE GAME'S LOGIC */
        /* To Do */
        //Hero's position
        Vector2 hPos = play.getHero().getPosition();


        /* UPDATE ALL ANIMATIONS */
        /* In development */

        //Hero's animation
        TextureRegion text = hero_animations.getTexture( pressed ?
                                                        Hero.HeroStatus.ATTACK :
                                                        Hero.HeroStatus.STILL,
                                                        delta );

        TextureRegion robot_text = enemy_animations.getTexture( Enemy.EnemyStatus.MOVE_RIGHT,
                                                                delta );

        //Traps' animations

        //Enemies' animations

        /* DRAW TEXTURES ON THE SCREEN */

        //Clear screen with certain color
        Gdx.gl.glClearColor((float)0.5, (float)0.5, (float)0.5, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Calculate middle of the screen according to the hero's position
        Vector2 midScreen = calMidScreen( hPos, text.getRegionWidth() );

        //Set batch to only draw what the camera sees
        game.batch.setProjectionMatrix( camera.combined );

        game.batch.begin();

        //Set camera position to match hero's center position
        camera.position.set( midScreen.x, midScreen.y, 0 );
        camera.update();

        //Draw hero's texture
        game.batch.draw( map, 0, 0 );
        game.batch.draw( text, hPos.x, hPos.y );
        game.batch.draw( robot_text, hPos.x - 200, hPos.y);

        game.batch.end();
    }

    /**
     * @brief Calculates the center of the screen according to the hero's position
     * @param hPos
     * @param spriteWidth
     * @return
     */
    private Vector2 calMidScreen ( Vector2 hPos, float spriteWidth ) {

        return new Vector2( hPos.x + spriteWidth / 2, 250);
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

        play.touchDown( screenX, screenY );

        pressed = true;

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        play.touchUp( );

        pressed = false;

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
