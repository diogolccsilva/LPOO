package com.lpoo.project.processors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.lpoo.project.MyGame;
import com.lpoo.project.logic.Hero;

/**
 * Class that deals with the inputs
 * This class implements the interface InputProcessor
 */
public class Inputs implements InputProcessor {

    /**
     * Game where the inputs will be treated
     */
    MyGame game;

    /**
     * Constructor for the class Inputs
     * @param game Game where the inputs will be treated
     */
    public Inputs(MyGame game){
        this.game = game;

        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    /**
     * Called when a key was pressed
     * @param keycode Code of the key pressed
     * @return True if the input was processed, False if it wasn't
     */
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
                        //game.changeScreen(MyGame.States.PAUSE);
                        break;
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
                break;
            case PAUSE:
                switch (keycode) {
                    case Keys.BACK:
                    case Keys.ESCAPE:
                        game.changeScreen(MyGame.States.PLAY);
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
    /**
     * Called when a key was released
     * @param keycode Code of the key released
     * @return True if the input was processed, False if it wasn't
     */
    public boolean keyUp(int keycode) {
        switch (game.getState()){
            case PLAY:
                switch(keycode){
                    case Keys.BACK:
                    case Keys.ESCAPE:
                        game.changeScreen(MyGame.States.PAUSE);
                        break;
                    default:
                        game.getPlayScreen().getGame().getHero().move(Hero.HeroStatus.STILL);
                }
                return true;
            case PAUSE:
                switch(keycode){
                    case Keys.BACK:
                    case Keys.ESCAPE:
                        game.changeScreen(MyGame.States.PLAY);
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
    /**
     * Called when a key was typed
     * @param character Character typed
     * @return True if the input was processed, False if it wasn't
     */
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
    /**
     * Called when the screen was touched or a mouse button was pressed
     * @param screenX The x coordinate, origin is in the upper left corner
     * @param screenY The y coordinate, origin is in the upper left corner
     * @param pointer The pointer for the event
     * @param button The button
     * @return True if the input was processed, False if it wasn't
     */
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
            case PAUSE:
                game.getPauseMenu().touchDown(screenX, screenY, pointer, button);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    /**
     * Called when a finger was lifted or a mouse button was released
     * @param screenX The x coordinate, origin is in the upper left corner
     * @param screenY The y coordinate, origin is in the upper left corner
     * @param pointer The pointer for the event
     * @param button The button
     * @return True if the input was processed, False if it wasn't
     */
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
            case PAUSE:
                game.getPauseMenu().touchUp(screenX, screenY, pointer, button);
                break;
            case GAMEOVER:
                game.getGameOver().touchUp(screenX, screenY);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    /**
     * Called when a finger or the mouse was dragged
     * @param screenX The x coordinate, origin is in the upper left corner
     * @param screenY The y coordinate, origin is in the upper left corner
     * @param pointer The pointer for the event
     * @return True if the input was processed, False if it wasn't
     */
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
    /**
     * Called when the mouse was moved without any buttons being pressed
     * @param screenX The x coordinate, origin is in the upper left corner
     * @param screenY The y coordinate, origin is in the upper left corner
     * @return True if the input was processed, False if it wasn't
     */
    public boolean mouseMoved(int screenX, int screenY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    /**
     * Called when the mouse wheel was scrolled
     * @param amount
     * @return True if the input was processed, False if it wasn't
     */
    public boolean scrolled(int amount) {
        // TODO Auto-generated method stub
        return false;
    }
}
