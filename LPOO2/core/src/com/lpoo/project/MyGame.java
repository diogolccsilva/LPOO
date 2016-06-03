package com.lpoo.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lpoo.project.logic.Game;
import com.lpoo.project.logic.Hero;
import com.lpoo.project.processors.Inputs;
import com.lpoo.project.screens.BuildScreen;
import com.lpoo.project.screens.Menu;
import com.lpoo.project.screens.PlayScreen;

import java.lang.*;
import java.util.Vector;

public class MyGame extends com.badlogic.gdx.Game {

    public SpriteBatch batch;
    public int screenWidth, screenHeight;

    private Inputs inputs;
    private PlayScreen play;
    private BuildScreen build;
    private Menu menu;

    private Game game;

    private Vector<Hero> heroes;

    public enum States { MENU, PLAY, BUILD, EXIT }
    private States state;

    @Override
	public void create () {
        batch = new SpriteBatch();

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        inputs = new Inputs(this);
        //menu = new Menu(this);
        build = new BuildScreen( this, new Game() );
        setScreen( build );

        heroes = new Vector<Hero>();

        state = States.BUILD;
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
                disposeState();
                state = stat;
                menu = new Menu(this);
                setScreen( new Menu(this) );
                break;
            case PLAY:
                if( state == States.MENU )
                    game = new Game();
                disposeState();
                state = stat;
                play = new PlayScreen(this, game);
                setScreen( play );
                break;
            case BUILD:
                disposeState();
                state = States.BUILD;
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

    public void addHero(Hero h){
        heroes.add(h);
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
    }

    @Override
	public void render () {
        super.render();
	}
}
