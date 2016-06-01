package com.lpoo.project.processors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;

import com.lpoo.project.MyGame;

public class Inputs implements InputProcessor {

    MyGame game;

    public Inputs(MyGame game){
        this.game = game;

        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (game.getState()){
            case MENU:
                switch(keycode){
                    case Keys.BACK:
                        game.changeScreen(MyGame.States.EXIT);
                        return true;
                    default:
                        break;
                }
                break;
            case PLAY:
                switch(keycode){
                    case Keys.BACK:
                        game.changeScreen(MyGame.States.MENU);
                        return true;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (game.getState()){
            case MENU:
                switch(keycode){
                    case Keys.BACK:
                        return true;
                    default:
                        break;
                }
                break;
            case PLAY:
                switch(keycode){
                    case Keys.BACK:
                        return true;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        switch (game.getState()){
            case MENU:
                break;
            case PLAY:
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        switch (game.getState()){
            case MENU:
                game.getMenu().touchDown(screenX, screenY, pointer, button);
                break;
            case PLAY:
                game.getPlayScreen().touchDown(screenX, screenY, pointer, button);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        switch (game.getState()){
            case MENU:
                game.getMenu().touchUp(screenX,screenY,pointer, button);
                break;
            case PLAY:
                game.getPlayScreen().touchUp(screenX,screenY,pointer, button);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        // TODO Auto-generated method stub
        return false;
    }
}
