package com.lpoo.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lpoo.project.logic.Game;
import com.lpoo.project.logic.Hero;
import com.lpoo.project.processors.Inputs;
import com.lpoo.project.screens.BuildScreen;
import com.lpoo.project.screens.Menu;
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

    private int volume;

    private Game game;

    private Vector<Hero> heroes;

    public enum States { MENU, PLAY, BUILD, EXIT }
    private States state;

    private static MyGame ourInstance = new MyGame();

    public static MyGame getInstance() {
        return ourInstance;
    }

    private MyGame() {}

    @Override
	public void create () {
        batch = new SpriteBatch();
        cache = Cache.getInstance();

        camera = new OrthographicCamera(w, h);
        hudCamera = new OrthographicCamera(w, h);

        volume = 50;

        inputs = new Inputs(this);
        menu = new Menu(this);
        setScreen( menu );

        heroes = new Vector<>();

        state = States.MENU;
	}

    public void disposeState( ) {
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
        }
    }

    public void changeScreen( States stat ) {
        switch ( stat ) {
            case MENU:
                if( state == States.PLAY )
                    game = null;
                disposeState();
                state = stat;
                menu = new Menu(this);
                setScreen( menu );
                break;
            case PLAY:
                if( game == null )
                    game = new Game();
                disposeState();
                state = stat;
                play = new PlayScreen(this, game);
                setScreen( play );
                break;
            case BUILD:
                if( game == null )
                    game = new Game();
                disposeState();
                state = stat;
                build = new BuildScreen(this, game);
                setScreen(build);
                break;
            case EXIT:
                Gdx.app.exit();
        }
    }

    public final States getState() {
        return state;
    }

    public Menu getMenu(){
        return menu;
    }

    public PlayScreen getPlayScreen(){
        return play;
    }

    public BuildScreen getBuildScreen() {
        return build;
    }

    public Vector<Hero> getHeroes(){
        return heroes;
    }

    public Cache getCache() {
        return cache;
    }

    public void addHero(Hero h){
        heroes.add(h);
    }

    public void newHero() {
        Hero h = new Hero(null,0,0,100,1,1);
        addHero(h);
    }

    public void saveGame() {
        GameFiles.saveHeroes(heroes);
    }

    public void volumeUp() {
        if (volume<100){
            volume++;
        }
        System.out.println(volume);
        switch(state){
            case MENU:
                menu.setVolume(volume/100f);
                break;
            case PLAY:
                play.setVolume(volume/100f);
                break;
            case BUILD:
                build.setVolume(volume/100f);
                break;
        }
    }

    public void volumeDown() {
        if (volume>0){
            volume--;
        }
        System.out.println(volume);
        switch(state){
            case MENU:
                menu.setVolume(volume/100f);
                break;
            case PLAY:
                play.setVolume(volume/100f);
                break;
            case BUILD:
                build.setVolume(volume/100f);
                break;
        }
    }

    public int getVolume(){
        return volume;
    }

    @Override
    public void dispose() {
        if( play != null )
            play.dispose();
        else if( menu != null )
            menu.dispose();
        else if( build != null )
            build.dispose();
        batch.dispose();
        cache.dispose();
    }

    @Override
	public void render () {
        super.render();
	}
}
