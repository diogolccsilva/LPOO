package com.lpoo.project.processors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.lpoo.project.MyGame;
import com.lpoo.project.logic.Hero;
import com.lpoo.project.screens.PlayScreen;

public class Inputs implements InputProcessor {

    MyGame game;

    public Inputs(MyGame game){
        this.game = game;

        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public boolean keyDown(int keycode) {
        switch(keycode){
            case Keys.PLUS:
                game.volumeUp();
                break;
            case Keys.MINUS:
                game.volumeDown();
                break;
        }

        switch (game.getState()){
            case MENU:
                switch(keycode){
                    case Keys.BACK:
                        game.changeScreen(MyGame.States.EXIT);
                        return true;
                    case Keys.ESCAPE:
                        game.changeScreen(MyGame.States.EXIT);
                        return true;
                    default:
                        break;
                }
                break;
            case PLAY:
                switch(keycode){
                    case Keys.BACK:
                    case Keys.ESCAPE:
                        game.changeScreen(MyGame.States.MENU);
                        return true;
                    case Keys.LEFT:
                        game.getPlayScreen().getGame().getHero().move(Hero.HeroStatus.MOVE_LEFT);
                        break;
                    case Keys.RIGHT:
                        game.getPlayScreen().getGame().getHero().move(Hero.HeroStatus.MOVE_RIGHT);
                        break;
                    case Keys.SPACE:
                        game.getPlayScreen().getGame().getHero().attack();
                        break;
                    case Keys.S:
                        game.saveGame();
                        break;
                    case Keys.N:
                        game.newHero();
                        break;
                    default:
                        break;
                }
                break;
            case BUILD:
                switch (keycode) {
                    case Keys.BACK:
                    case Keys.ESCAPE:
                        game.changeScreen(MyGame.States.MENU);
                        return true;
                }
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
                        game.getPlayScreen().getGame().getHero().move(Hero.HeroStatus.STILL);
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
            case BUILD:
                game.getBuildScreen().touchDown(screenX,screenY);
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
            case BUILD:
                game.getBuildScreen().touchUp(screenX,screenY);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        switch (game.getState()){
            case MENU:
                //game.getMenu().touchUp(screenX,screenY,pointer, button);
                break;
            case PLAY:
                //game.getPlayScreen().touchUp(screenX,screenY,pointer, button);
                break;
            case BUILD:
                game.getBuildScreen().touchDragged(screenX, screenY);
                break;
            default:
                break;
        }
        return true;
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
