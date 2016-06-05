package com.lpoo.project.tests;

import static org.junit.Assert.*;
import org.junit.Test;

import com.lpoo.project.logic.*;

public class Tests {

    @Test
    /**
     * Tests the hero
     */
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

    @Test
    /**
     * Tests the enemies
     */
    public void testEnemyStatus() {
        Game game = new Game();
        Enemy enemy = new Enemy(game, 0, 0, 80, 125, 100, 50, 75);

        assertEquals(Enemy.EnemyStatus.MOVE_RIGHT, enemy.getState());

        enemy.animationStatus(Enemy.EnemyStatus.MOVE_RIGHT);
        assertEquals(Enemy.EnemyStatus.MOVE_RIGHT, enemy.getState());

        enemy.animationStatus(Enemy.EnemyStatus.ATTACK);
        assertEquals(Enemy.EnemyStatus.ATTACK, enemy.getState());
        assertEquals(Enemy.EnemyStatus.ATTACK, enemy.getNextState());
    }

    @Test
    /**
     * Tests the game
     */
    public void TestGame() {
        Game game = new Game();

        assertNotNull(game.getHero());
        assertEquals(true, game.getEnemies().isEmpty());
        assertNotEquals(0, game.getTraps().length);
        assertEquals(true, game.getProjectiles().isEmpty());

        assertEquals(Game.GameStatus.PLAYING, game.getState());
    }

    @Test
    /**
     * Tests the projectiles
     */
    public void TestProjectiles() {
        Game game = new Game();
        Projectile projectile = new Projectile(game, 0, 0, 30, 40, 6, 500, true);
        assertEquals(Projectile.ProjectileStatus.TRAVELLING, projectile.getState());
    }
}
