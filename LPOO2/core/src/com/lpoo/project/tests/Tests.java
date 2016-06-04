package com.lpoo.project.tests;

import static org.junit.Assert.*;
import org.junit.Test;

import com.lpoo.project.logic.*;

public class Tests {

    @Test
    public void testHeroStatus() {

        Game game = new Game();
        Hero hero = new Hero(game, 0, 0, 100, 50, 75);

        assertEquals(Hero.HeroStatus.STILL, hero.getState());

        // < 50 state = Move Left
        hero.touchDown(25);
        assertEquals(Hero.HeroStatus.MOVE_LEFT, hero.getNextState());

        // > 840 state = Move Right
        hero.touchDown(860);
        assertEquals(Hero.HeroStatus.MOVE_RIGHT, hero.getNextState());

        // else state = Attack
        hero.touchDown(68);
        assertEquals(Hero.HeroStatus.ATTACK, hero.getNextState());

        // state != dead -> nextState = still
        hero.touchUp();
        assertEquals(Hero.HeroStatus.STILL, hero.getNextState());

        // state != dead ->nextState = attack
        hero.attack();
        assertEquals(Hero.HeroStatus.ATTACK, hero.getNextState());

    }
}
