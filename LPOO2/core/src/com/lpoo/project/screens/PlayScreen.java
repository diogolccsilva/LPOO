package com.lpoo.project.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
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
 * Class that creates the play screen
 * This class implements the interface Screen
 */
public class PlayScreen implements Screen {

    /**
     * Screen's height
     */
    private static final int h = 765;
    /**
     * Screen's wight
     */
    private static final int w = 1360;
    /**
     * Game where the play screen will be placed
     */
    public Game game;
    /**
     * Principal game where the play screen will be placed
     */
    private MyGame myGame;
    /**
     * Renders bitmap fonts
     */
    private BitmapFont font;
    /**
     * Play screen's music
     */
    private Music music;
    /**
     * Hero's animations
     */
    private HeroAnimation hero_animations;
    /**
     * LinkedList with the enemies
     */
    private LinkedList<EnemyAnimation> enemies;
    /**
     * Array with the trap's animations
     */
    private Animator[] trapAnimations;
    /**
     * LinkedList with the projectile's animations
     */
    private LinkedList<Animator> projectiles;
    /**
     * Screen's map
     */
    private Map map;
    /**
     * Gold's texture
     */
    private Texture gold;
    /**
     * Texture of the robot's icon
     */
    private Texture robotIcon;
    /**
     * Boolean that represents if the game is paused
     */
    private boolean isPaused = false;
    /**
     * Screen's rectangle
     */
    private Rectangle screenRectangle;

    /**
     * Constructor for the class PlayScreen
     * @param myGame Principal game where the play screen's will be placed
     * @param game Game where the play screen's will be placed
     */
    public PlayScreen(MyGame myGame, Game game) {
        this.myGame = myGame;
        this.game = game;
        this.game.changeState(Game.GameStatus.PLAYING);

        BitmapFont f = myGame.getCache().getFont();
        if (f == null) {
            //Initialize font and store it in cache
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Font\\slkscr.ttf"));
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.size = 30;
            font = generator.generateFont(parameter);
            generator.dispose();
            myGame.getCache().setFont(font);
        } else font = f;

        Texture g = myGame.getCache().getGoldIcon();
        if (g == null) {
            gold = new Texture("Gold.png");
            myGame.getCache().setGoldIcon(gold);
        } else gold = g;
        g = myGame.getCache().getRobotIcon();
        if (g == null) {
            robotIcon = new Texture("Robot_Icon.png");
            myGame.getCache().setRobotIcon(robotIcon);
        } else robotIcon = g;

        myGame.hudCamera.position.set(myGame.w / 2, myGame.h / 2, 0);
        myGame.hudCamera.update();

        Music m = myGame.getCache().getPlayAudio();
        if (m == null) {
            music = Gdx.audio.newMusic(Gdx.files.internal("We're the Resistors.mp3"));
            myGame.getCache().setPlayAudio(music);
        } else music = m;

        music.setLooping(true);
        music.setVolume(myGame.getVolume() / 100f);
        music.play();

        HeroAnimation hA = myGame.getCache().getHeroAnimation();
        if (hA == null) {
            hero_animations = new HeroAnimation(game, "Hero\\hero1_fire.atlas", "Hero\\hero1_still.atlas",
                    "Hero\\hero1_move_left.atlas", "Hero\\hero1_move_right.atlas", game.getHero().getStats().getAttSpeed() / 7, 1 / 10f);
            myGame.getCache().setHeroAnimation(hero_animations);
        } else {
            hero_animations = hA;
            hero_animations.reset(game, game.getHero().getStats().getAttSpeed() / 7);
        }

        enemies = new LinkedList<>();
        trapAnimations = new TrapAnimation[26];
        projectiles = new LinkedList<>();

        Map mp = myGame.getCache().getMap();
        if (mp == null) {
            map = new Map();
            myGame.getCache().setMap(map);
        } else map = mp;

        screenRectangle = new Rectangle(0, 0, w, h);
    }

    /**
     * Getter for the y's relative position
     * @param y Y coordinate
     * @return The y's relative position
     */
    public float getRelativeY(int y) {
        return h * y / Gdx.graphics.getHeight();
    }

    /**
     * Getter for the x's relative position
     * @param x x coordinate
     * @return The x's relative position
     */
    public float getRelativeX(int x) {
        return w * x / Gdx.graphics.getWidth();
    }

    /**
     * Getter for the game
     * @return The game where the play screen is placed
     */
    public Game getGame() {
        return game;
    }

    /**
     * Function that draws the life's bar
     * @param x X position
     * @param y Y position
     * @param textures Life's bar texture
     */
    public void drawLifeBard(float x, float y, TextureRegion[] textures) {
        float width = 0;
        for (TextureRegion t : textures) {
            myGame.batch.draw(t, x + width, y);
            width += t.getRegionWidth();
        }
    }

    /**
     * Calculates the center of the screen according to the hero's position
     * @param hPos hERO'S POSITION
     * @return The center of the screen
     */
    private Vector2 calMidScreen(Vector2 hPos) {
        float tmp = hPos.x - w / 2 + 200;
        return new Vector2((tmp < 700) ? 700 : (tmp > 3400) ? 3400 : tmp, 400);
    }

    /**
     * Called when the screen was touched or a mouse button was pressed
     * @param screenX The x coordinate, origin is in the upper left corner
     * @param screenY The y coordinate, origin is in the upper left corner
     */
    public boolean touchDown(int screenX, int screenY) {
        game.touchDown(getRelativeX(screenX), getRelativeY(screenY));
        return true;
    }

    /**
     * Called when the screen was touched or a mouse button was released
     * @return True if the input was processed, False if it wasn't
     */
    public boolean touchUp() {
        game.touchUp();
        return true;
    }

    @Override
    /**
     * Called when this screen becomes the current screen for a Game
     */
    public void show() {

    }

    @Override
    /**
     * Called when the screen should render itself
     * @param delta Difference between the last time of call and the current time
     */
    public void render(float delta) {
        if (isPaused) {
            resume();
            return;
        }

        /* UPDATE GAME'S LOGIC */
        /* To Do */
        //Hero's position
        Vector2 hPos = game.getHero().getPosition();
        game.update(delta);

        if (game.getState() == Game.GameStatus.LOST)
            myGame.changeScreen(MyGame.States.GAMEOVER);
        else if (game.getState() == Game.GameStatus.BUILDING)
            myGame.changeScreen(MyGame.States.BUILD);

        /* UPDATE ALL ANIMATIONS */
        /* In development */

        //Hero's animation
        TextureRegion hero_text = hero_animations.getTexture(delta);
        game.getHero().animationStatus(hero_animations.getState());

        boolean[] frameEvents = game.getFrameEvents();
        if (frameEvents[Game.ENEMY_MELEE_SPAWN_INDEX])
            enemies.add(new EnemyAnimation(game, "Robot\\robot1_attack.atlas", "Robot\\robot1_walk.atlas", 1 / 5f, 1 / 2f, enemies.size()));
        else if (frameEvents[Game.ENEMY_RANGED_SPAWN_INDEX])
            enemies.add(new EnemyAnimation(game, "Robot\\robot2_attack.atlas", "Robot\\robot2_walk.atlas", 1 / 10f, 1 / 5f, enemies.size()));

        if (frameEvents[Game.HERO_PROJECTILE_FIRED_INDEX])
            projectiles.add(new ProjectileAnimation(game, "Projectile\\projectile1.atlas", projectiles.size() - 1));
        if (frameEvents[Game.ENEMY_PROJECTILE_FIRED_INDEX])
            for (int i = 0; i < game.getnNewProjectiles(); i++)
                projectiles.add(new ProjectileAnimation(game, "Projectile\\projectile2.atlas", projectiles.size() - 1));
        game.setFrameEvents();

        /* DRAW TEXTURES ON THE SCREEN */

        //Clear screen with certain color
        Gdx.gl.glClearColor((float) 0.5, (float) 0.5, (float) 0.5, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Calculate middle of the screen according to the hero's position
        Vector2 midScreen = calMidScreen(hPos);
        screenRectangle.x = midScreen.x - w / 2;
        screenRectangle.y = midScreen.y - h / 2;

        //Set batch to only draw what the camera sees
        myGame.batch.setProjectionMatrix(myGame.camera.combined);
        myGame.batch.begin();

        //Set camera position to match hero's center position
        myGame.camera.position.set(midScreen.x, midScreen.y, 0);
        myGame.camera.update();

        //Draw hero's texture
        myGame.batch.draw(map.getSky(), 0, 0);

        //Iterate throw the traps' animations
        Trap[] traps = game.getTraps();
        for (int i = 0; i < traps.length; i++) {
            if (traps[i] == null)
                continue;
            Trap t = traps[i];
            if (trapAnimations[i] == null)
                trapAnimations[i] = new TrapAnimation(game, "Trap\\trap1.atlas", 1 / 10f, i);

            //Only draw what is on the screen
            if (t.getRect().overlaps(screenRectangle))
                myGame.batch.draw(trapAnimations[i].getTexture(delta), t.getPosition().x, t.getPosition().y);
        }

        //Iterate throw the enemies' animations
        LinkedList<Enemy> en = game.getEnemies();
        if (enemies.size() != en.size()) {
            enemies.clear();
            en.clear();
            System.out.println("Enemies not synced");
        }
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = en.get(i);
            EnemyAnimation e_ani = enemies.get(i);
            enemies.get(i).setIndex(i);

            if (e.getState() == Enemy.EnemyStatus.DEAD) {
                e_ani.dispose();
                enemies.remove(i);
                game.eraseEnemy(i);
                i--;
                continue;
            }

            //Update texture and enemy
            TextureRegion robot_text = e_ani.getTexture(delta);
            myGame.batch.draw(robot_text, e.getPosition().x, e.getPosition().y);
            e.animationStatus(enemies.get(i).getState());

            if (!e.getRect().overlaps(screenRectangle))
                continue;

            //Only draw what is on the screen
            drawLifeBard(e.getPosition().x + 10, e.getPosition().y + robot_text.getRegionHeight(),
                    LifeBar.getTexture(e.getStats().getHealth(), e.getStats().getMaxHealth()));
        }

        //Iterate throw the projectiles' animations
        LinkedList<Projectile> proj = game.getProjectiles();
        if (proj.size() != projectiles.size()) {
            proj.clear();
            projectiles.clear();
            System.out.println("Projectiles not synced");
        }
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile p = proj.get(i);
            Animator p_ani = projectiles.get(i);
            p_ani.setIndex(i);

            //If the projectile's animation has ended or if the bullet is already out of the map
            if (p_ani.isFinished() || p.getPosition().x <= 0) {
                p_ani.dispose();
                projectiles.remove(i);
                game.eraseProjectile(i);
                i--;
                continue;
            }

            //Update texture and projectile
            TextureRegion project_text = p_ani.getTexture(delta);

            if (!p.getRect().overlaps(screenRectangle))
                continue;

            //Only draw what is on the screen
            myGame.batch.draw(project_text, p.getPosition().x, p.getPosition().y);
        }

        if (game.getHero().getState() != Hero.HeroStatus.DEAD)
            myGame.batch.draw(hero_text, hPos.x, hPos.y);

        drawLifeBard(hPos.x + 10, hPos.y + hero_text.getRegionHeight() + 10,
                LifeBar.getTexture(game.getHero().getStats().getHealth(), game.getHero().getStats().getMaxHealth()));

        myGame.batch.draw(map.getTerrain(), 0, 0);

        myGame.batch.setProjectionMatrix(myGame.hudCamera.combined);

        int drawY = myGame.h - 50;
        int hudY = myGame.h - 70;
        myGame.batch.draw(gold, 50, hudY);
        font.draw(myGame.batch, "" + game.getMoney(), 100, drawY);
        myGame.batch.draw(robotIcon, 200, hudY);
        font.draw(myGame.batch, "" + game.getnEnemiesWon() + "/" + game.getMaxEnemiesWon(), 250, drawY);

        myGame.batch.end();
    }

    @Override
    /**
     * Called when the screen is resized
     * @param width Screen's width
     * @param height Screen's height
     */
    public void resize(int width, int height) {

    }

    @Override
    /**
     * Called when the screen is paused
     */
    public void pause() {
        isPaused = true;
    }

    @Override
    /**
     * Called when the screen is resumed from a paused state
     */
    public void resume() {
        isPaused = false;
        music.play();
    }

    @Override
    /**
     * Hide's the screen
     */
    public void hide() {

    }

    @Override
    /**
     * Called when the screen is destroyed
     */
    public void dispose() {
        //hero_animations.dispose();
        for (Animator e : enemies)
            e.dispose();
        for (Animator p : projectiles)
            p.dispose();
        music.stop();
    }

    public void setVolume(float v) {
        music.setVolume(v);
    }
}
