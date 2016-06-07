package com.lpoo.project.tests;

import com.lpoo.project.logic.CharacterStats;
import com.lpoo.project.logic.Enemy;
import com.lpoo.project.logic.Game;
import com.lpoo.project.logic.Hero;
import com.lpoo.project.logic.MeleeEnemy;
import com.lpoo.project.logic.Projectile;
import com.lpoo.project.logic.ProjectileStats;
import com.lpoo.project.logic.RangedEnemy;
import com.lpoo.project.logic.Trap;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class Tests {

    private final CharacterStats standardHero = new CharacterStats(300,100,90f,0.7f,10);

    @Test
    /**
     * Tests the hero
     */
    public void testHeroStatus() {
        Game game = new Game(standardHero);
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
        Game game = new Game(standardHero);
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

        assertTrue( enemy.getState() != Enemy.EnemyStatus.DEAD );

        enemy.hit(stats);

        assertTrue( enemy.getState() == Enemy.EnemyStatus.DEAD );

        game.eraseEnemy(0);
        assertEquals(0, game.getEnemies().size());
        enemy = new RangedEnemy(game, (int) game.getHero().getRect().x - 500, 144, 50, 10, 10);
        game.addEnemy(enemy);
        assertEquals(1, game.getEnemies().size());

        assertEquals(Enemy.EnemyStatus.MOVE_RIGHT, enemy.getState());
        assertEquals(Enemy.EnemyStatus.MOVE_RIGHT, enemy.getNextState());

        enemy.update(0);

        assertEquals(Enemy.EnemyStatus.MOVE_RIGHT, enemy.getState());
        assertEquals(Enemy.EnemyStatus.ATTACK, enemy.getNextState());

    }

    @Test
    /**
     * Tests the game
     */
    public void TestGame() {
        Game game = new Game(standardHero);

        assertNotNull(game.getHero());
        assertTrue(game.getEnemies().isEmpty());
        assertNotEquals(0, game.getTraps().length);
        assertTrue(game.getProjectiles().isEmpty());

        assertEquals(Game.GameStatus.BUILDING, game.getState());
    }

    @Test
    /**
     * Tests the traps
     */
    public void TestTraps() {
        Game game = new Game(standardHero);

        //All traps have the same size
        game.setTrap(100, 144, 128, 128, 0);

        assertTrue( game.getTraps()[0] != null );

        game.setTrap(228, 144, 128, 128, -1);
        game.setTrap(356, 144, 128, 128, game.getTraps().length);

        int nTraps = 0;
        for( Trap t : game.getTraps() ) {
            if( t != null )
                nTraps++;
        }

        assertEquals( 1, nTraps );
    }

    @Test
    /**
     * Tests the projectiles
     */
    public void TestProjectiles() {
        Game game = new Game(standardHero);
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
        assertTrue( heroCurrHealth < heroHealth );

        //If the projectile is on the hero's side then it will pass right trough him
        projectile.setHeroSide(true);
        projectile.setState(Projectile.ProjectileStatus.TRAVELLING);
        heroHealth = game.getHero().getStats().getHealth();
        projectile.update(0);
        assertEquals(Projectile.ProjectileStatus.TRAVELLING, projectile.getState());
        heroCurrHealth = game.getHero().getStats().getHealth();
        assertTrue( heroCurrHealth == heroHealth );

        Enemy enemy = new MeleeEnemy(game, 0, 144, 50, 10, 10);
        game.addEnemy(enemy);
        assertEquals( 1, game.getEnemies().size());

        //If the projectile is on the hero's side then it should it the enemy
        projectile.getRect().x = enemy.getRect().x + 10;
        int enemyHealth = enemy.getStats().getHealth();
        projectile.update(0);
        assertEquals(Projectile.ProjectileStatus.HIT_TARGET, projectile.getState());
        int enemyCurrHealth = enemy.getStats().getHealth();
        assertTrue( enemyCurrHealth < enemyHealth);

        //If the projectile is against the hero then it shouldn't hit the enemies
        projectile.setHeroSide(false);
        projectile.setState(Projectile.ProjectileStatus.TRAVELLING);
        enemyHealth = enemy.getStats().getHealth();
        projectile.update(0);
        assertEquals(Projectile.ProjectileStatus.TRAVELLING, projectile.getState());
        enemyCurrHealth = enemy.getStats().getHealth();
        assertTrue( enemyCurrHealth == enemyHealth);

        game.eraseProjectile(0);
        assertTrue( game.getProjectiles().size() == 0 );
    }
}
