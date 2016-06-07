package com.lpoo.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lpoo.project.logic.CharacterStats;
import com.lpoo.project.logic.Game;
import com.lpoo.project.logic.Hero;
import com.lpoo.project.logic.TrapStats;
import com.lpoo.project.processors.Inputs;
import com.lpoo.project.screens.BuildScreen;
import com.lpoo.project.screens.GameOver;
import com.lpoo.project.screens.HeroMenu;
import com.lpoo.project.screens.Menu;
import com.lpoo.project.screens.PauseMenu;
import com.lpoo.project.screens.PlayScreen;
import com.lpoo.project.storage.Cache;
import com.lpoo.project.storage.GameFiles;

import java.lang.*;
import java.util.Vector;

public class MyGame extends com.badlogic.gdx.Game {

    public SpriteBatch batch;

    public OrthographicCamera camera;
    public OrthographicCamera hudCamera;

    private Cache cache;

    public static final int h = 765, w = 1360;

    private Inputs inputs;
    private PlayScreen play;
    private BuildScreen build;
    private Menu menu;
    private PauseMenu pauseMenu;
    private GameOver gameOver;
    private HeroMenu heroMenu;

    private int volume;
    private int selectedHeroIndex;

    private Game game;

    private Vector<CharacterStats> heroes;
    private Vector<TrapStats> traps;
    private Vector<CharacterStats> enemies;

    public enum States {MENU, PLAY, BUILD, EXIT, PAUSE, GAMEOVER, HERO}

    private States state;

    private static MyGame ourInstance = new MyGame();

    public static MyGame getInstance() {
        return ourInstance;
    }

    private MyGame() {
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        cache = Cache.getInstance();

        camera = new OrthographicCamera(w, h);
        hudCamera = new OrthographicCamera(w, h);

        volume = 50;

        inputs = new Inputs(this);
        menu = new Menu(this);
        //heroMenu = new HeroMenu(this);
        setScreen(menu);

        heroes = new Vector<>();
        traps = new Vector<>();
        enemies = new Vector<>();
        selectedHeroIndex = 0;
        loadGame();

        state = States.MENU;
        GameFiles.loadScore(10);
    }

    public void disposeState() {
        switch (state) {
            case MENU:
                menu.dispose();
                break;
            case PLAY:
                play.dispose();
                break;
            case BUILD:
                build.dispose();
                break;
            case PAUSE:
                pauseMenu.dispose();
                break;
            case GAMEOVER:
                gameOver.dispose();
                break;

        }
    }

    public void changeScreen(States stat) {
        switch (stat) {
            case MENU:
                if (state == States.PLAY)
                    game = null;
                if (state == States.PAUSE) {
                    if (play != null)
                        play.dispose();
                    game = null;
                }

                disposeState();
                state = stat;
                menu = new Menu(this);
                setScreen(menu);
                break;

            case PLAY:
                if (state != States.PAUSE) {
                    if (game == null)
                        game = new Game(heroes.elementAt(selectedHeroIndex));
                    play = new PlayScreen(this, game);
                }
                disposeState();
                state = stat;
                setScreen(play);
                break;

            case BUILD:
                if (game == null)
                    game = new Game(heroes.elementAt(selectedHeroIndex));
                disposeState();
                state = stat;
                build = new BuildScreen(this, game);
                setScreen(build);
                break;

            case PAUSE:
                if (state == States.PLAY) {
                    play.pause();
                    state = stat;
                    pauseMenu = new PauseMenu(this);
                    setScreen(pauseMenu);
                }
                break;

            case GAMEOVER:
                GameFiles.saveScore(game.getScore());
                disposeState();
                state = stat;
                gameOver = new GameOver(this);
                setScreen(gameOver);
                break;

            case HERO:

                break;

            case EXIT:
                Gdx.app.exit();
        }
    }

    public final States getState() {
        return state;
    }

    public Menu getMenu() {
        return menu;
    }

    public PauseMenu getPauseMenu() {
        return pauseMenu;
    }

    public PlayScreen getPlayScreen() {
        return play;
    }

    public BuildScreen getBuildScreen() {
        return build;
    }

    public GameOver getGameOver() {
        return gameOver;
    }

    public HeroMenu getHeroMenu() {
        return heroMenu;
    }

    public Cache getCache() {
        return cache;
    }

    public void volumeUp() {
        if (volume < 100) {
            volume++;
        }
        updateVolume();
    }

    public void volumeDown() {
        if (volume > 0) {
            volume--;
        }
        updateVolume();
    }

    public void volumeMute() {
        volume = 0;
        updateVolume();
    }

    public void updateVolume() {
        System.out.println(volume);
        switch (state) {
            case MENU:
                menu.setVolume(volume / 100f);
                break;
            case PLAY:
                play.setVolume(volume / 100f);
                break;
            case BUILD:
                build.setVolume(volume / 100f);
                break;
        }
    }

    public int getVolume() {
        return volume;
    }

    public Vector<CharacterStats> getHeroes() {
        return heroes;
    }

    public void addHero(CharacterStats stats) {
        heroes.add(stats);
    }

    public void addEnemy(CharacterStats stats) {
        enemies.add(stats);
    }

    public void addTrap(TrapStats stats) {
        traps.add(stats);
    }

    public void newHero() {
        addHero(new CharacterStats(100, 1, 80f, 0.7f, 1));
        int damage = 10;
        float attackSpeed = 1f;
        float rechargeSpeed = 3f;
        float heatUpSpeed = 1 / 10f;
        float timeAttack = attackSpeed / 3f;
        int cost = 20;
        addTrap(new TrapStats(damage, attackSpeed, rechargeSpeed, heatUpSpeed, timeAttack, cost));
    }

    public void saveGame() {
        GameFiles.saveHeroes(heroes);
        GameFiles.saveEnemies(enemies);
        GameFiles.saveTraps(traps);
    }

    public void loadGame() {
        heroes = GameFiles.loadHeroes();
        enemies = GameFiles.loadEnemies();
        traps = GameFiles.loadTraps();
    }

    public void setStatus (States state){
        this.state = state;
    }

    @Override
    public void dispose () {
        if (play != null)
            play.dispose();
        else if (menu != null)
            menu.dispose();
        else if (build != null)
            build.dispose();
        else if (pauseMenu != null)
            pauseMenu.dispose();
        batch.dispose();
        cache.dispose();
    }

    @Override
    public void render () {
        super.render();
    }
}
