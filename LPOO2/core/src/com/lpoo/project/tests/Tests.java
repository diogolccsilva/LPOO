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

import java.util.Vector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class Tests {

    private final CharacterStats standardHero = new CharacterStats(300,100,90f,0.7f,10);
    private final CharacterStats standardMeleeEnemy = new CharacterStats(80,2,60f,1.4f,20);
    private final CharacterStats standardRangedEnemy = new CharacterStats(50,1,80f,0.6f,10);

    @Test
    /**
     * Tests the hero
     */
    public void testHero() {
        Vector<CharacterStats> enemyStats = new Vector<>();
        enemyStats.add(standardMeleeEnemy);
        enemyStats.add(standardRangedEnemy);

        Game game = new Game(standardHero, enemyStats);
        Hero hero = game.getHero();

        assertEquals(Hero.HeroStatus.STILL, hero.getState());

        hero.move(-1);
        assertEquals(Hero.HeroStatus.MOVE_LEFT, hero.getNextState());

        game.heroMove(1);
        assertEquals(Hero.HeroStatus.MOVE_RIGHT, hero.getNextState());

        game.heroMove(-1);
        assertEquals(Hero.HeroStatus.MOVE_LEFT, hero.getNextState());

        game.heroAttack();
        assertEquals(Hero.HeroStatus.ATTACK, hero.getNextState());

        hero.move(1);
        assertEquals(Hero.HeroStatus.MOVE_RIGHT, hero.getNextState());

        hero.stop();
        assertEquals(Hero.HeroStatus.STILL, hero.getNextState());

        hero.attack();
        assertEquals(Hero.HeroStatus.ATTACK, hero.getNextState());

        game.stopHero();
        assertEquals(Hero.HeroStatus.STILL, hero.getNextState());

        float xPos = hero.getRect().x;

        hero.animationStatus(Hero.HeroStatus.MOVE_RIGHT);
        hero.update(0.1f);

        assertTrue( hero.getRect().x > xPos );

        hero.animationStatus(Hero.HeroStatus.MOVE_LEFT);
        hero.update(0.2f);

        assertTrue( hero.getRect().x < xPos );

        xPos = 200;
        hero.getRect().x = 200;
        hero.animationStatus(Hero.HeroStatus.MOVE_LEFT);
        hero.update(0.2f);
        assertTrue( hero.getRect().x == xPos );
        hero.animationStatus(Hero.HeroStatus.MOVE_RIGHT);
        hero.update(0f);
        assertTrue( hero.getRect().x == xPos );
        hero.update(0.2f);
        assertTrue( hero.getRect().x > xPos );

        xPos = 3800;
        hero.getRect().x = 3800;
        hero.animationStatus(Hero.HeroStatus.MOVE_RIGHT);
        hero.update(0.2f);
        assertTrue( hero.getRect().x == xPos );
        hero.animationStatus(Hero.HeroStatus.MOVE_LEFT);
        hero.update(0f);
        assertTrue( hero.getRect().x == xPos );
        hero.update(0.2f);
        assertTrue( hero.getRect().x < xPos );

        assertEquals(0, game.getProjectiles().size());

        hero.animationStatus(Hero.HeroStatus.ATTACK);
        hero.update(0f);

        assertEquals(0, game.getProjectiles().size());

        hero.update(hero.getStats().getAttSpeed());
        assertEquals(1, game.getProjectiles().size());

        ProjectileStats stats = new ProjectileStats(0, 0);
        hero.hit(stats);
        assertEquals(hero.getStats().getHealth(), hero.getStats().getMaxHealth());
        stats.setAttDamage(hero.getStats().getHealth());

        hero.hit(stats);
        assertEquals(0, hero.getStats().getHealth());
        assertEquals( hero.getState(), Hero.HeroStatus.DEAD );
        assertEquals( hero.getNextState(), Hero.HeroStatus.DEAD );

        hero.update(3f);
        assertEquals( hero.getState(), Hero.HeroStatus.STILL );
        assertEquals( hero.getNextState(), Hero.HeroStatus.STILL );
        assertEquals( hero.getStats().getHealth(), hero.getStats().getMaxHealth() );
    }

    @Test
    /**
     * Tests the enemies
     */
    public void testEnemy() {
        Vector<CharacterStats> enemyStats = new Vector<>();
        enemyStats.add(standardMeleeEnemy);
        enemyStats.add(standardRangedEnemy);

        Game game = new Game(standardHero, enemyStats);
        Enemy enemy = new MeleeEnemy(game, 0, 144, standardMeleeEnemy);
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
        enemy = new RangedEnemy(game, (int) game.getHero().getRect().x - 500, 144, standardRangedEnemy);
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
        Vector<CharacterStats> enemyStats = new Vector<>();
        enemyStats.add(standardMeleeEnemy);
        enemyStats.add(standardRangedEnemy);

        Game game = new Game(standardHero, enemyStats);

        assertNotNull(game.getHero());
        assertTrue(game.getEnemies().isEmpty());
        assertNotEquals(0, game.getTraps().length);
        assertTrue(game.getProjectiles().isEmpty());

        assertEquals(Game.GameStatus.BUILDING, game.getState());
        assertEquals(0, game.getWave());

        game.update(10);

        assertEquals(Game.GameStatus.BUILDING, game.getState());
        assertEquals(0, game.getWave());

        game.changeState(Game.GameStatus.PLAYING);
        assertEquals(Game.GameStatus.PLAYING, game.getState());
        assertEquals(1, game.getWave());

        game.update( 4.9f );
        assertEquals(0, game.getEnemies().size());
        game.update( 0.1f );
        assertEquals(1, game.getEnemies().size());
        assertEquals(1, game.getEnemiesSpawned());

        Enemy e = game.getEnemies().get(0);
        e.getRect().x = 3999;
        game.update( 0 );
        assertEquals( 0, game.getnEnemiesWon() );
        e.getRect().x = 4000;
        game.update( 0 );
        assertEquals( 1, game.getnEnemiesWon() );

        game.setEnemiesSpawned(10);
        game.getEnemies().clear();

        game.update(0);
        assertEquals(Game.GameStatus.BUILDING, game.getState());
        game.changeState(Game.GameStatus.PLAYING);
        assertEquals(Game.GameStatus.PLAYING, game.getState());
        assertEquals(2, game.getWave());

        game.setnEnemiesWon(3);
        game.update(0);
        assertEquals(Game.GameStatus.LOST, game.getState());
    }

    @Test
    /**
     * Tests the traps
     */
    public void TestTraps() {
        Vector<CharacterStats> enemyStats = new Vector<>();
        enemyStats.add(standardMeleeEnemy);
        enemyStats.add(standardRangedEnemy);

        Game game = new Game(standardHero, enemyStats);

        //All traps have the same size
        game.setTrap(100, 144, 128, 128, 0);
        assertTrue( game.getTraps()[0] == null );
        game.setMoney(99);
        game.setTrap(100, 144, 128, 128, 0);
        assertTrue( game.getTraps()[0] == null );
        game.setMoney(100);
        game.setTrap(100, 144, 128, 128, 0);
        assertTrue( game.getTraps()[0] != null );
        assertEquals(0, game.getMoney());

        game.setTrap(228, 144, 128, 128, -1);
        game.setTrap(356, 144, 128, 128, game.getTraps().length);

        int nTraps = 0;
        for( Trap t : game.getTraps() ) {
            if( t != null )
                nTraps++;
        }

        assertEquals( 1, nTraps );

        game.getHero().getRect().x = 105;

        game.getTraps()[0].update( 0 );

        assertTrue( game.getHero().getRect().overlaps( game.getTraps()[0].getRect() ) );
        assertTrue( game.getTraps()[0].getState() != Trap.TrapStatus.HEATUP );

        Enemy enemy = new MeleeEnemy(game, 105, 144, standardMeleeEnemy);
        game.addEnemy(enemy);
        assertEquals(1, game.getEnemies().size());

        game.getTraps()[0].update( 0 );

        assertTrue( game.getHero().getRect().overlaps( game.getTraps()[0].getRect() ) );
        assertTrue( game.getTraps()[0].getState() == Trap.TrapStatus.HEATUP );

        game.getTraps()[0].update( game.getTraps()[0].getStats().getHeatUpSpeed() );

        assertTrue( game.getTraps()[0].getState() == Trap.TrapStatus.ATTACK );

        game.getTraps()[0].update( game.getTraps()[0].getStats().getTimeAttack() );

        assertEquals( 2, game.getTraps()[0].getnAttacks() );

        game.getTraps()[0].update( game.getTraps()[0].getStats().getAttackSpeed() );

        assertEquals( 1, game.getTraps()[0].getnAttacks() );
        assertTrue( game.getTraps()[0].getState() == Trap.TrapStatus.RECHARGE );

        game.getTraps()[0].update( 0 );

        assertTrue( game.getTraps()[0].getState() == Trap.TrapStatus.RECHARGE );

        game.getTraps()[0].update( game.getTraps()[0].getStats().getRechargeSpeed() - 0.1f );

        assertTrue( game.getTraps()[0].getState() == Trap.TrapStatus.RECHARGE );

        game.getTraps()[0].update( 0.1f );
        assertTrue( game.getTraps()[0].getState() == Trap.TrapStatus.WAIT );

        game.setTrap(0, 0, 0, 0, 0);
        assertTrue( game.getTraps()[0] == null );
    }

    @Test
    /**
     * Tests the projectiles
     */
    public void TestProjectiles() {
        Vector<CharacterStats> enemyStats = new Vector<>();
        enemyStats.add(standardMeleeEnemy);
        enemyStats.add(standardRangedEnemy);

        Game game = new Game(standardHero, enemyStats);

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

        Enemy enemy = new MeleeEnemy(game, 0, 144, standardMeleeEnemy);
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
