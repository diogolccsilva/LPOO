package com.lpoo.project.tests;

import static org.junit.Assert.*;
import org.junit.Test;

import com.lpoo.project.logic.*;
import com.lpoo.project.logic.Character;

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
        Enemy enemy = new MeleeEnemy(game, 0, 144, 100, 50, 75);
        game.addEnemy(enemy);
        assertEquals(1, game.getEnemies().size());

        assertEquals(Enemy.EnemyStatus.MOVE_RIGHT, enemy.getState());
        assertEquals(Enemy.EnemyStatus.MOVE_RIGHT, enemy.getNextState());

        enemy.getRect().x = game.getHero().getRect().x + 10;
        enemy.update(0);

        assertEquals(Enemy.EnemyStatus.MOVE_RIGHT, enemy.getState());
        assertEquals(Enemy.EnemyStatus.ATTACK, enemy.getNextState());

        enemy.animationStatus(Enemy.EnemyStatus.ATTACK);

        assertEquals(Enemy.EnemyStatus.ATTACK, enemy.getState());
        assertEquals(Enemy.EnemyStatus.ATTACK, enemy.getNextState());

        enemy.getRect().x = game.getHero().getRect().x + 500;
        enemy.update(0);

        assertEquals(Enemy.EnemyStatus.ATTACK, enemy.getState());
        assertEquals(Enemy.EnemyStatus.MOVE_RIGHT, enemy.getNextState());

        ProjectileStats stats = new ProjectileStats(enemy.getStats().getHealth() - 1, 150);
        enemy.hit(stats);

        assertEquals( false, enemy.getState() == Enemy.EnemyStatus.DEAD ? true : false );

        enemy.hit(stats);

        assertEquals( true, enemy.getState() == Enemy.EnemyStatus.DEAD ? true : false );

        game.eraseEnemy(0);
        assertEquals(0, game.getEnemies().size());
        enemy = new RangedEnemy(game, 0, 144, 50, 10, 10);
        game.addEnemy(enemy);
        assertEquals(1, game.getEnemies().size());
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

        assertEquals(Game.GameStatus.BUILDING, game.getState());
    }

    @Test
    /**
     * Tests the projectiles
     */
    public void TestProjectiles() {
        Game game = new Game();
        Projectile projectile = new Projectile(game, game.getHero().getRect().x + 10, 150, 30, 40, 6, 500, false);
        game.addProjectile(projectile, false);
        assertEquals( 1, game.getProjectiles().size());
        assertEquals(Projectile.ProjectileStatus.TRAVELLING, projectile.getState());

        //If the projectile is against the hero then it should it him
        int heroHealth = game.getHero().getStats().getHealth();
        projectile.getRect().x = game.getHero().getRect().x + 10;
        projectile.update(0);
        assertEquals(Projectile.ProjectileStatus.HIT_TARGET, projectile.getState());
        int heroCurrHealth = game.getHero().getStats().getHealth();
        assertEquals( true, heroCurrHealth < heroHealth );

        //If the projectile is on the hero's side then it will pass right trough him
        projectile.setHeroSide(true);
        projectile.setState(Projectile.ProjectileStatus.TRAVELLING);
        heroHealth = game.getHero().getStats().getHealth();
        projectile.update(0);
        assertEquals(Projectile.ProjectileStatus.TRAVELLING, projectile.getState());
        heroCurrHealth = game.getHero().getStats().getHealth();
        assertEquals( true, heroCurrHealth == heroHealth );

        Enemy enemy = new MeleeEnemy(game, 0, 144, 50, 10, 10);
        game.addEnemy(enemy);
        assertEquals( 1, game.getEnemies().size());

        //If the projectile is on the hero's side then it should it the enemy
        projectile.getRect().x = enemy.getRect().x + 10;
        int enemyHealth = enemy.getStats().getHealth();
        projectile.update(0);
        assertEquals(Projectile.ProjectileStatus.HIT_TARGET, projectile.getState());
        int enemyCurrHealth = enemy.getStats().getHealth();
        assertEquals( true, enemyCurrHealth < enemyHealth);

        //If the projectile is against the hero then it shouldn't hit the enemies
        projectile.setHeroSide(false);
        projectile.setState(Projectile.ProjectileStatus.TRAVELLING);
        enemyHealth = enemy.getStats().getHealth();
        projectile.update(0);
        assertEquals(Projectile.ProjectileStatus.TRAVELLING, projectile.getState());
        enemyCurrHealth = enemy.getStats().getHealth();
        assertEquals( true, enemyCurrHealth == enemyHealth);
    }
}
