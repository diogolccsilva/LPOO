package com.lpoo.project;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lpoo.project.screens.PlayScreen;

import java.lang.*;

public class MyGame extends com.badlogic.gdx.Game {

    public SpriteBatch batch;
    public int screenWidth, screenHeight;

    private enum States { MENU, PLAY }
    private States state;

    @Override
	public void create () {

        batch = new SpriteBatch();

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        setScreen( new PlayScreen(this) );

        state = States.PLAY;
	}

    @Override
    public void dispose() {

    }

    @Override
	public void render () {
        super.render();
	}
}
