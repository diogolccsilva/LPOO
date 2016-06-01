package com.lpoo.project.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.lpoo.project.MyGame;
import com.lpoo.project.animations.EnemyAnimation;
import com.lpoo.project.animations.HeroAnimation;
import com.lpoo.project.animations.Map;
import com.lpoo.project.animations.ProjectileAnimation;
import com.lpoo.project.logic.Enemy;
import com.lpoo.project.logic.Game;
import com.lpoo.project.logic.Projectile;

import java.util.LinkedList;

/**
 * Created by Vasco on 13/05/2016.
 */
public class PlayScreen implements Screen {

    private OrthographicCamera camera;
    private MyGame game;
    public Game play;

    private HeroAnimation hero_animations;
    private LinkedList<EnemyAnimation> enemies;
    private LinkedList<ProjectileAnimation> projectiles;
    private EnemyAnimation enemy_animations;
    private Map map;


    private final int h = 500, w = 890;

    public PlayScreen(MyGame game) {

        this.game = game;
        play = new Game();

        camera = new OrthographicCamera( w, h );

        hero_animations = new HeroAnimation( this, "Hero\\hero1_fire.atlas", "Hero\\hero1_still.atlas",
                                                    "Hero\\hero1_still.atlas", "Hero\\hero1_still.atlas", 1/10f, 1/3f );
        enemy_animations = new EnemyAnimation( this, "Robot\\robot1_attack.atlas", "Robot\\robot1_walk.atlas", 1/3f, 1/3f );
        enemies = new LinkedList<EnemyAnimation>();
        projectiles = new LinkedList<ProjectileAnimation>();
        map = new Map();
    }


    public float getRelativeY( int y ) {
        return h * y / Gdx.graphics.getHeight();
    }

    public float getRelativeX( int x ) {
        return w * x / Gdx.graphics.getWidth();
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
        play.update( delta );

        /* UPDATE ALL ANIMATIONS */
        /* In development */

        //Hero's animation
        TextureRegion hero_text = hero_animations.getTexture( play.getHero().getNextState(), delta );
        play.getHero().AnimationStatus( hero_animations.getState() );

        boolean[] frameEvents = play.getFrameEvents();
        if( frameEvents[Game.ENEMY_SPAWN_INDEX] )
            enemies.add( new EnemyAnimation( this, "Robot\\robot1_attack.atlas", "Robot\\robot1_walk.atlas", 1/5f, 1/3f ));
        if( frameEvents[Game.PROJECTILE_FIRED_INDEX] )
            projectiles.add( new ProjectileAnimation( this, "Projectile\\projectile1.atlas", 1/10f));
        play.setFrameEvents();

        //Traps' animations

        //Enemies' animations
       // TextureRegion robot_text = enemy_animations.getTexture( play., delta );

        /* DRAW TEXTURES ON THE SCREEN */

        //Clear screen with certain color
        Gdx.gl.glClearColor((float)0.5, (float)0.5, (float)0.5, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Calculate middle of the screen according to the hero's position
        Vector2 midScreen = calMidScreen( hPos, hero_text.getRegionWidth() );

        //Set batch to only draw what the camera sees
        game.batch.setProjectionMatrix( camera.combined );

        game.batch.begin();

        //Set camera position to match hero's center position
        camera.position.set( midScreen.x, midScreen.y, 0 );
        camera.update();

        //Draw hero's texture
        game.batch.draw( map.getMap(), 0, 0 );
        game.batch.draw( hero_text, hPos.x, hPos.y );

        LinkedList<Enemy> enemies = play.getEnemies();
        for( int i = 0; i < enemies.size(); i++ ) {
            TextureRegion robot_text = this.enemies.get(i).getTexture( enemies.get(i).getNextState(), delta );
            game.batch.draw(robot_text, enemies.get(i).getPosition().x, enemies.get(i).getPosition().y);
            play.getEnemies().get(i).AnimationStatus( this.enemies.get(i).getStatus() );
            if( enemies.get(i).getState() == Enemy.EnemyStatus.DEAD && this.enemies.get(i).isFinished( enemies.get(i).getState() ))
                play.eraseEnemy( enemies.get(i) );
        }
        LinkedList<Projectile> projectiles = play.getProjectiles();
        for( int i = 0; i < projectiles.size(); i++ ) {
            TextureRegion project_text = this.projectiles.get(i).getTexture( projectiles.get(i).getState(), delta );
            game.batch.draw(project_text, projectiles.get(i).getPosition().x, projectiles.get(i).getPosition().y);
            if( this.projectiles.get(i).isFinished() )
                play.eraseProjectile( projectiles.get(i) );
        }
        game.batch.draw( map.getSpawnWall(), 0, 142);

        game.batch.end();
    }

    /**
     * @brief Calculates the center of the screen according to the hero's position
     * @param hPos
     * @param spriteWidth
     * @return
     */
    private Vector2 calMidScreen ( Vector2 hPos, float spriteWidth ) {
        return new Vector2( (hPos.x < 450 ) ? 450 : (hPos.x > 4000 ) ? 4000 : hPos.x + spriteWidth / 2, 250);
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
        hero_animations.dispose();
        enemy_animations.dispose();
        map.dispose();
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        play.touchDown( getRelativeX(screenX), getRelativeY(screenY) );
        return true;
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        play.touchUp( );
        return true;
    }
}
