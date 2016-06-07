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
import com.lpoo.project.MyGame;

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

    private Rectangle opt1;
    private Rectangle opt2;
    private Rectangle opt3;

    private int opt;

    private static final int h = 765, w = 1360;

    public HeroMenu ( MyGame myGame ) {
        this.myGame = myGame;

        myGame.camera.position.set( myGame.w / 2, myGame.h / 2, 0 );
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

        opt1 = new Rectangle(619, 523, 700, 200);
        opt2 = new Rectangle(619, 282, 700, 200);
        opt3 = new Rectangle(619, 41, 700, 200);
    }

    public float getRelativeY(int y) {
        return h * y / Gdx.graphics.getHeight();
    }

    public float getRelativeX(int x) {
        return w * x / Gdx.graphics.getWidth();
    }

    public void touchUp( int screenX, int screenY ) {
        Rectangle rect = new Rectangle( getRelativeX(screenX), h - getRelativeY(screenY), 20, 20 );
        if( rect.overlaps(opt1))
            opt = 1;
        else if ( rect.overlaps(opt2))
            opt = 2;
        else if ( rect.overlaps(opt3))
            opt = 3;
        else opt = -1;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.516f, 0.516f, 0.516f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Set batch to only draw what the camera sees
        myGame.batch.setProjectionMatrix( myGame.camera.combined );
        myGame.batch.begin();

        title.draw(myGame.batch, "CHOOSE", 41, 550);
        title.draw(myGame.batch, "YOUR", 41, 425);
        title.draw(myGame.batch, "HERO", 41, 300);

        text.draw(myGame.batch, "HEALTH: " + 300, 670, 700);
        text.draw(myGame.batch, "RESISTANCE: " + 10, 670, 665);
        text.draw(myGame.batch, "MOVEMENT SPEED: " + 80, 670, 630);
        text.draw(myGame.batch, "ATTACK SPEED: " + 1, 670, 595);
        text.draw(myGame.batch, "ATTACK DAMAGE: " + 10, 670, 560);

        text.draw(myGame.batch, "HEALTH: " + 150, 670, 459);
        text.draw(myGame.batch, "RESISTANCE: " + 5, 670, 424);
        text.draw(myGame.batch, "MOVEMENT SPEED: " + 100, 670, 389);
        text.draw(myGame.batch, "ATTACK SPEED: " + 0.7, 670, 354);
        text.draw(myGame.batch, "ATTACK DAMAGE: " + 20, 670, 319);

        text.draw(myGame.batch, "HEALTH: " + 100, 670, 218);
        text.draw(myGame.batch, "RESISTANCE: " + 3, 670, 183);
        text.draw(myGame.batch, "MOVEMENT SPEED: " + 200, 670, 148);
        text.draw(myGame.batch, "ATTACK SPEED: " + 0.4, 670, 113);
        text.draw(myGame.batch, "ATTACK DAMAGE: " + 12, 670, 78);

        myGame.batch.draw( opt == 3 ? selected: notSelected, 619, 41, 700, 200 );
        myGame.batch.draw( opt == 2 ? selected: notSelected, 619, 282, 700, 200 );
        myGame.batch.draw( opt == 1 ? selected: notSelected, 619, 523, 700, 200 );

        myGame.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        music.pause();
    }

    @Override
    public void resume() {
        music.play();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
