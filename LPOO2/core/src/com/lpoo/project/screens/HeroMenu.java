package com.lpoo.project.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.lpoo.project.MyGame;
import com.lpoo.project.logic.CharacterStats;

import java.util.Vector;

/**
 * Created by Vasco on 06/06/2016.
 */
public class HeroMenu implements Screen {

    private MyGame myGame;

    private Music music;

    private BitmapFont title;
    private BitmapFont text;

    private Texture selected;
    private Texture notSelected;

    private Rectangle[] opts;
    private int nopts;
    private int opt;

    private int xTouch,yTouch;
    private int yPos,xPos;
    private int yi;
    private int dy;

    private static final int h = 765, w = 1360;

    public HeroMenu(MyGame myGame) {
        this.myGame = myGame;

        myGame.camera.position.set(myGame.w / 2, myGame.h / 2, 0);
        myGame.camera.update();

        //Initialize font and store it in cache
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Font\\slkscr.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 100;
        title = generator.generateFont(parameter);
        title.setColor(Color.BLACK);

        parameter.size = 30;
        text = generator.generateFont(parameter);
        generator.dispose();

        selected = new Texture("Selected.png");
        notSelected = new Texture("NotSelected.png");

        opt = -1;
        nopts = myGame.getHeroes().size();
        opts = new Rectangle[nopts];

        xPos = 619;
        yPos = 523;
        yi = yPos;
        dy = 0;

        for (int i = 0; i < nopts; i++) {
            opts[i] = new Rectangle(xPos,yPos-i*241,700,200);
        }
        xTouch = 0;
        yTouch = 0;
    }

    public float getRelativeY(int y) {
        return h * y / Gdx.graphics.getHeight();
    }

    public float getRelativeX(int x) {
        return w * x / Gdx.graphics.getWidth();
    }

    public void touchUp(int screenX, int screenY) {
        Vector2 pos = getRelativePosition( screenX, screenY );
        xTouch = (int) pos.x;
        yTouch = (int) pos.y;
        Rectangle rect = new Rectangle(xTouch, h-yTouch, 20, 20);
        for (int i = 0;i<nopts;i++){
            if (rect.overlaps(opts[i])){
                opt = i;
                break;
            }
        }
    }

    public void touchDown(int screenX,int screenY){
        Vector2 pos = getRelativePosition( screenX, screenY );
        xTouch = (int) pos.x;
        yTouch = (int) pos.y;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.516f, 0.516f, 0.516f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Set batch to only draw what the camera sees
        myGame.batch.setProjectionMatrix(myGame.camera.combined);
        myGame.batch.begin();

        title.draw(myGame.batch, "CHOOSE", 41, 550);
        title.draw(myGame.batch, "YOUR", 41, 425);
        title.draw(myGame.batch, "HERO", 41, 300);

        Vector<CharacterStats> stats = myGame.getHeroes();

        for (int i = 0;i<nopts;i++){
            int k = i*241-dy;
            text.draw(myGame.batch, "HEALTH: " + stats.elementAt(i).getHealth(), 670, 700-k);
            text.draw(myGame.batch, "RESISTANCE: " + stats.elementAt(i).getResistance(), 670, 665-k);
            text.draw(myGame.batch, "MOVEMENT SPEED: " + stats.elementAt(i).getMovSpeed(), 670, 630-k);
            text.draw(myGame.batch, "ATTACK SPEED: " + stats.elementAt(i).getAttSpeed(), 670, 595-k);
            text.draw(myGame.batch, "ATTACK DAMAGE: " + stats.elementAt(i).getAttDamage(), 670, 560-k);
            myGame.batch.draw(notSelected,opts[i].getX(),opts[i].getY(),opts[i].getWidth(),opts[i].getHeight());
        }

        myGame.batch.end();

        if (opt != -1) {
            myGame.setSelectedHeroIndex(opt);
            myGame.changeScreen(MyGame.States.BUILD);
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        //music.pause();
    }

    @Override
    public void resume() {
        //music.play();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public Vector2 getRelativePosition(int x, int y) {
        return new Vector2(getRelativeX(x),getRelativeY(y));
    }

    public void touchDragged(int screenX, int screenY) {
        Vector2 pos = getRelativePosition( screenX, screenY );
        int deltaY = yTouch - (int)pos.y;

        if( deltaY < 5 && deltaY > -5 )
            deltaY = 0;
        else if( deltaY > 20 )
            deltaY = 20;
        else if( deltaY < -20 )
            deltaY = -20;

        int tmp = yPos + deltaY;
        System.out.println(tmp);
        if( tmp >= 523 && (tmp - (nopts-1)*241) <= 41 ){
            yPos += deltaY;
            dy = yPos-yi;
        }

        for (int i = 0;i<nopts;i++){
            opts[i].setPosition(xPos,yPos-i*241);
        }
    }
}
