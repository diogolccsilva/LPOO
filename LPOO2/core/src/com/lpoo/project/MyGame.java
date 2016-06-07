package com.lpoo.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lpoo.project.logic.CharacterStats;
import com.lpoo.project.logic.Game;
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

/**
 * Class that creates the main game that represents the main function of the program
 * This class extends the function that exists in com.badlogic.gdx.Game
 */
public class MyGame extends com.badlogic.gdx.Game {

    /**
     * Draws batched quads using indices
     */
    public SpriteBatch batch;

    /**
     * A camera with orthographic projection
     */
    public OrthographicCamera camera;
    /**
     * A camera with orthographic projection
     */
    public OrthographicCamera hudCamera;

    /**
     * Game's cache
     */
    private Cache cache;

    /**
     * Game's height
     */
    public static final int h = 765;
    /**
     * Game's wight
     */
    public static final int w = 1360;

    /**
     * Game's inputs
     */
    private Inputs inputs;
    /**
     * Game's play screen
     */
    private PlayScreen play;
    /**
     * Game's build screen
     */
    private BuildScreen build;
    /**
     * Game's main menu
     */
    private Menu menu;
    /**
     * Game's pause menu
     */
    private PauseMenu pauseMenu;
    /**
     * Game over option
     */
    private GameOver gameOver;
    /**
     * Game's hero menu
     */
    private HeroMenu heroMenu;

    /**
     * Sounds' volume
     */
    private int volume;
    /**
     * Hero index selected
     */
    private int selectedHeroIndex;

    /**
     * Game created
     */
    private Game game;

    /**
     * Vector with all heroes' properties
     */
    private Vector<CharacterStats> heroes;
    /**
     * Vector with all traps' properties
     */
    private Vector<TrapStats> traps;
    /**
     * Vector with all enemies' properties
     */
    private Vector<CharacterStats> enemies;

    /**
     * Enumeration for the main game's status
     */
    public enum States {MENU, HERO, BUILD, PLAY, EXIT, PAUSE, GAMEOVER}

    /**
     * Main game's status
     */
    private States state;

    /**
     * Instantiates the main game
     */
    private static MyGame ourInstance = new MyGame();

    /**
     * Getter for the instantiation of the main game
     * @return The main game's instantiation
     */
    public static MyGame getInstance() {
        return ourInstance;
    }

    /**
     * Constructor for the class MyGame
     */
    private MyGame() {
    }

    @Override
    /**
     * Creates the main game
     */
    public void create() {
        batch = new SpriteBatch();
        cache = Cache.getInstance();

        camera = new OrthographicCamera(w, h);
        hudCamera = new OrthographicCamera(w, h);

        volume = 50;

        inputs = new Inputs(this);
        menu = new Menu(this);
        setScreen(menu);

        heroes = new Vector<>();
        traps = new Vector<>();
        enemies = new Vector<>();
        selectedHeroIndex = 0;

        loadGame();

        state = States.MENU;
    }

    /**
     * Disposes all the main game's status
     */
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

    /**
     * Changes the current screen
     * @param stat Status that will allowed to change the screen
     */
    public void changeScreen(States stat) {
        switch (stat) {
            case MENU:
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

            case HERO:
                disposeState();
                state = stat;
                heroMenu = new HeroMenu(this);
                setScreen(heroMenu);
                break;

            case BUILD:
                if (state == States.HERO)
                    game = new Game(heroes.elementAt(selectedHeroIndex));
                disposeState();
                state = stat;
                build = new BuildScreen(this, game);
                setScreen(build);
                break;

            case PLAY:
                if (state != States.PAUSE)
                    play = new PlayScreen(this, game);
                disposeState();
                state = stat;
                setScreen(play);
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

            case EXIT:
                Gdx.app.exit();
        }
    }

    /**
     * Getter for the main game's status
     * @return The main game's status
     */
    public final States getState() {
        return state;
    }

    /**
     * Getter for the main menu
     * @return The main menu
     */
    public Menu getMenu() {
        return menu;
    }

    /**
     * Getter for the pause menu
     * @return The pause menu
     */
    public PauseMenu getPauseMenu() {
        return pauseMenu;
    }

    /**
     * Getter for the play screen
     * @return The play screen
     */
    public PlayScreen getPlayScreen() {
        return play;
    }

    /**
     * Getter for the build screen
     * @return The build screen
     */
    public BuildScreen getBuildScreen() {
        return build;
    }

    /**
     * Getter for the game over screen
     * @return The game over screen
     */
    public GameOver getGameOver() {
        return gameOver;
    }

    /**
     * Getter for the hero's menu
     * @return Th hero's menu
     */
    public HeroMenu getHeroMenu() {
        return heroMenu;
    }

    /**
     * Getter for the game's cache
     * @return The game's cache
     */
    public Cache getCache() {
        return cache;
    }

    /**
     * Setter for the selected hero's index
     * @param selectedHeroIndex New hero's index to be selected
     */
    public void setSelectedHeroIndex(int selectedHeroIndex) {
        this.selectedHeroIndex = selectedHeroIndex;
    }

    /**
     * Volumes up the music
     */
    public void volumeUp() {
        if (volume < 100) {
            volume++;
        }
        updateVolume();
    }

    /**
     * Volumes down the music
     */
    public void volumeDown() {
        if (volume > 0) {
            volume--;
        }
        updateVolume();
    }

    /**
     * Mutes the music
     */
    public void volumeMute() {
        volume = 0;
        updateVolume();
    }

    /**
     * Updates the music
     */
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

    /**
     * Getter for the music's volume
     * @return The music's volume
     */
    public int getVolume() {
        return volume;
    }

    /**
     * Getter for the game's heroes
     * @return The game's heroes
     */
    public Vector<CharacterStats> getHeroes() {
        return heroes;
    }

    /**
     * Adds a hero to the vector of game's heroes
     * @param stats New hero's properties
     */
    public void addHero(CharacterStats stats) {
        heroes.add(stats);
    }

    /**
     * Adds a enemy to the vector of game's enemies
     * @param stats New enemy's properties
     */
    public void addEnemy(CharacterStats stats) {
        enemies.add(stats);
    }

    /**
     * Adds a new trap to the game's traps
     * @param stats New trap's properties
     */
    public void addTrap(TrapStats stats) {
        traps.add(stats);
    }

    /**
     * Creates a new Hero
     */
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

    /**
     * Function that saves the game into files
     */
    public void saveGame() {
        GameFiles.saveHeroes(heroes);
        GameFiles.saveEnemies(enemies);
        GameFiles.saveTraps(traps);
    }

    /**
     * Function that loads a game from files
     */
    public void loadGame() {
        heroes = GameFiles.loadHeroes();
        enemies = GameFiles.loadEnemies();
        traps = GameFiles.loadTraps();
    }

    /**
     * Setter for the main game's status
     * @param state Main game's status to replace the old one
     */
    public void setStatus (States state){
        this.state = state;
    }

    @Override
    /**
     * Releases all textures of the game
     */
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
