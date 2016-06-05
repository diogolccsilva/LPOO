package com.lpoo.project.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.lpoo.project.MyGame;
import com.lpoo.project.animations.Animator;
import com.lpoo.project.animations.EnemyAnimation;
import com.lpoo.project.animations.HeroAnimation;
import com.lpoo.project.animations.LifeBar;
import com.lpoo.project.animations.Map;
import com.lpoo.project.animations.ProjectileAnimation;
import com.lpoo.project.animations.TrapAnimation;
import com.lpoo.project.logic.Enemy;
import com.lpoo.project.logic.Game;
import com.lpoo.project.logic.Hero;
import com.lpoo.project.logic.Projectile;
import com.lpoo.project.logic.Trap;

import java.util.LinkedList;

/**
 * Created by Vasco on 13/05/2016.
 */
public class PlayScreen implements Screen {

    private OrthographicCamera camera;
    private MyGame myGame;
    public Game game;

    private Music music;

    private HeroAnimation hero_animations;
    private LinkedList<EnemyAnimation> enemies;
    private Animator[] trapAnimations;
    private LinkedList<Animator> projectiles;
    private Map map;

    private final int h = 500, w = 890;

    public PlayScreen( MyGame myGame, Game game ) {
        this.myGame = myGame;
        this.game = game;
        this.game.changeState(Game.GameStatus.PLAYING);

        camera = new OrthographicCamera( w, h );

        music = Gdx.audio.newMusic(Gdx.files.internal("We're the Resistors.mp3"));
        music.setLooping(true);
        music.play();

        hero_animations = new HeroAnimation( game, "Hero\\hero1_fire.atlas", "Hero\\hero1_still.atlas",
                                                    "Hero\\hero1_move_left.atlas", "Hero\\hero1_move_right.atlas", 1/10f, 1/10f );
        enemies = new LinkedList<>();
        trapAnimations = new TrapAnimation[26];
        projectiles = new LinkedList<>();
        map = new Map();
    }


    public float getRelativeY( int y ) {
        return h * y / Gdx.graphics.getHeight();
    }

    public float getRelativeX( int x ) {
        return w * x / Gdx.graphics.getWidth();
    }

    @Override
    public void show() {

    }

    public void drawLifeBard( float x, float y, TextureRegion[] textures  ) {
        float width = 0;
        for( TextureRegion t : textures ) {
            myGame.batch.draw(t, x + width, y);
            width += t.getRegionWidth();
        }
    }

    @Override
    public void render(float delta) {

        /* UPDATE GAME'S LOGIC */
        /* To Do */
        //Hero's position
        Vector2 hPos = game.getHero().getPosition();
        game.update( delta );

        if( game.getState() == Game.GameStatus.LOST || game.getState() == Game.GameStatus.WON )
            myGame.changeScreen(MyGame.States.MENU);
        else if( game.getState() == Game.GameStatus.BUILDING )
            myGame.changeScreen(MyGame.States.BUILD);

        /* UPDATE ALL ANIMATIONS */
        /* In development */

        //Hero's animation
        TextureRegion hero_text = hero_animations.getTexture( delta );
        game.getHero().animationStatus( hero_animations.getState() );


        boolean[] frameEvents = game.getFrameEvents();
        if( frameEvents[Game.ENEMY_SPAWN_INDEX] )
            enemies.add( new EnemyAnimation( game, "Robot\\robot1_attack.atlas", "Robot\\robot1_walk.atlas", 1/5f, 1/2f, enemies.size() - 1 ));
        if( frameEvents[Game.PROJECTILE_FIRED_INDEX] )
            projectiles.add( new ProjectileAnimation( game, "Projectile\\projectile1.atlas", projectiles.size() - 1 ));
        game.setFrameEvents();

        /* DRAW TEXTURES ON THE SCREEN */

        //Clear screen with certain color
        Gdx.gl.glClearColor((float)0.5, (float)0.5, (float)0.5, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Calculate middle of the screen according to the hero's position
        Vector2 midScreen = calMidScreen( hPos, hero_text.getRegionWidth() );


        //Set batch to only draw what the camera sees
        myGame.batch.setProjectionMatrix( camera.combined );
        myGame.batch.begin();

        //Set camera position to match hero's center position
        camera.position.set( midScreen.x, midScreen.y, 0 );
        camera.update();

        //Draw hero's texture
        myGame.batch.draw( map.getSky(), 0, 0 );

        //Iterate throw the traps' animations
        Trap[] traps = game.getTraps();
        for( int i = 0; i < traps.length; i++ ) {
            if( traps[i] == null )
                continue;
            Trap t = traps[i];
            if( trapAnimations[i] == null )
                trapAnimations[i] = new TrapAnimation( game, "Trap\\trap1.atlas", 1/10f, 3, i);

            myGame.batch.draw(trapAnimations[i].getTexture( delta ),t.getPosition().x,t.getPosition().y);
        }

        //Iterate throw the enemies' animations
        LinkedList<Enemy> en = game.getEnemies();
        for( int i = 0; i < enemies.size(); i++ ) {
            Enemy e = en.get(i);
            enemies.get(i).setIndex(i);
            TextureRegion robot_text = enemies.get(i).getTexture( delta );
            myGame.batch.draw(robot_text, e.getPosition().x,e.getPosition().y);
            drawLifeBard( e.getPosition().x + 10, e.getPosition().y + robot_text.getRegionHeight(),
                    LifeBar.getTexture( e.getStats().getHealth(), e.getStats().getMaxHealth() ));

            if( e.getState() == Enemy.EnemyStatus.DEAD ) {
                enemies.remove(i);
                game.eraseEnemy(i);
                i--;
            } else e.animationStatus( enemies.get(i).getState() );
        }

        //Iterate throw the projectiles' animations
        LinkedList<Projectile> proj = game.getProjectiles();
        for( int i = 0; i < projectiles.size(); i++ ) {
            Animator p_ani = projectiles.get(i);
            p_ani.setIndex(i);
            Projectile p = proj.get(i);
            TextureRegion project_text = p_ani.getTexture( delta );
            myGame.batch.draw(project_text, p.getPosition().x, p.getPosition().y);

            //If the projectile's animation has ended or if the bullet is already out of the map
            if( p_ani.isFinished() || p.getPosition().x <= 0 ) {
                projectiles.remove(i);
                game.eraseProjectile(i);
                i--;
            }
        }

        if( game.getHero().getState() != Hero.HeroStatus.DEAD )
            myGame.batch.draw( hero_text, hPos.x, hPos.y );

        drawLifeBard( hPos.x + 10, hPos.y + hero_text.getRegionHeight() + 10,
                LifeBar.getTexture( game.getHero().getStats().getHealth(), game.getHero().getStats().getMaxHealth() ));

        myGame.batch.draw( map.getTerrain(), 0, 0);

        myGame.batch.end();
    }

    /**
     * @brief Calculates the center of the screen according to the hero's position
     * @param hPos
     * @param spriteWidth
     * @return
     */
    private Vector2 calMidScreen ( Vector2 hPos, float spriteWidth ) {
        float tmp = hPos.x + 10;
        return new Vector2( (tmp < 450 ) ? 450 : (tmp > 3650 ) ? 3650 : tmp, 250);
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
        for( Animator e : enemies )
            e.dispose();
        for( Animator p : projectiles )
            p.dispose();
        map.dispose();
        music.stop();
        music.dispose();
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        game.touchDown( getRelativeX(screenX), getRelativeY(screenY) );
        return true;
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        game.touchUp( );
        return true;
    }

    public Game getGame(){
        return game;
    }
}
