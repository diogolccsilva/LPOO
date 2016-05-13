package com.lpoo.project.logic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.TimeUtils;
import com.lpoo.project.animations.Animator;

import java.util.LinkedList;

/**
 * Created by Vasco on 12/05/2016.
 */
public class Hero extends Character {

    //private Animation heroFire, heroStill;
    private LinkedList<Sprite> heroFire, heroStill;
    private int selectedImg;
    private long timeSelected;
    private boolean imgFire;
    private Animator animation;

    /**
     * @brief Constructor for the class Hero
     * @param x
     * @param y
     * @param health
     * @param resistance
     * @param strength
     */
    public Hero( int x, int y, int health, int resistance, int strength )  {

        super( x, y, health, resistance, strength );

        selectedImg = 0;
        imgFire = false;

        timeSelected = TimeUtils.nanoTime();

        heroFire = new LinkedList<Sprite>();
        heroStill = new LinkedList<Sprite>();

        Sprite tmp = new Sprite( new Texture("Hero\\Fire\\Hero-Fire0.png") );
        tmp.scale( 2 );
        heroFire.add( tmp );
        tmp = new Sprite( new Texture("Hero\\Fire\\Hero-Fire1.png") );
        tmp.scale( 2 );
        heroFire.add( tmp );
        tmp = new Sprite( new Texture("Hero\\Fire\\Hero-Fire2.png") );
        tmp.scale( 2 );
        heroFire.add( tmp );
        tmp = new Sprite( new Texture("Hero\\Fire\\Hero-Fire3.png") );
        tmp.scale( 2 );
        heroFire.add( tmp );
        tmp = new Sprite( new Texture("Hero\\Fire\\Hero-Fire4.png") );
        tmp.scale( 2 );
        heroFire.add( tmp );
        tmp = new Sprite( new Texture("Hero\\Fire\\Hero-Fire5.png") );
        tmp.scale( 2 );
        heroFire.add( tmp );
        tmp = new Sprite( new Texture("Hero\\Fire\\Hero-Fire6.png") );
        tmp.scale( 2 );
        heroFire.add( tmp );

        tmp = new Sprite( new Texture("Hero\\Still\\Hero-Still1.png") );
        tmp.scale( 2 );
        heroStill.add( tmp );
        tmp = new Sprite( new Texture("Hero\\Still\\Hero-Still2.png") );
        tmp.scale( 2 );
        heroStill.add( tmp );


    }

    public Sprite SelectImg( boolean fire ) {

        if (imgFire && selectedImg == heroFire.size()) {

            selectedImg = 0;
            imgFire = false;
        }

        if (fire && !imgFire)
            imgFire = fire;

        if (imgFire) {

            if (selectedImg == heroFire.size())
                selectedImg = 0;

            //Check if it should change img
            if (TimeUtils.timeSinceNanos(timeSelected) < 100000000)
                return heroFire.get(selectedImg);

            timeSelected = TimeUtils.nanoTime();
            selectedImg++;

            return heroFire.get(selectedImg - 1);
        } else {

            if (selectedImg == heroStill.size())
                selectedImg = 0;

            //Check if it should change img
            if (TimeUtils.timeSinceNanos(timeSelected) < 333333333)
                return heroStill.get(selectedImg);

            timeSelected = TimeUtils.nanoTime();
            selectedImg++;

            return heroStill.get(selectedImg - 1);
        }
    }

}
