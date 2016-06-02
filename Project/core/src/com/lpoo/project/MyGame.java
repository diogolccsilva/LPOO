package com.lpoo.project;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lpoo.project.processors.Inputs;
import com.lpoo.project.screens.Menu;
import com.lpoo.project.screens.PlayScreen;

import java.lang.*;

public class MyGame extends com.badlogic.gdx.Game {

    public SpriteBatch batch;
    public int screenWidth, screenHeight;

    private Inputs inputs;
    private PlayScreen game;
    private Menu menu;

    public enum States { MENU, PLAY, EXIT }
    private States state;

    @Override
	public void create () {
        batch = new SpriteBatch();

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        inputs = new Inputs(this);
        menu = new Menu(this);
        setScreen( menu );

        state = States.MENU;
	}

    public void disposeState( ) {
        switch (state) {
            case MENU:
                menu.dispose();
                break;
            case PLAY:
                game.dispose();
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
                disposeState();
                state = stat;
                game = new PlayScreen(this);
                setScreen( game );
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
        return game;
    }

    @Override
    public void dispose() {
        if( game != null )
            game.dispose();
        else if( menu != null )
            menu.dispose();
        batch.dispose();
    }

    @Override
	public void render () {
        super.render();
	}
}
