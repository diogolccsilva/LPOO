package com.lpoo.project.logic;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Vasco on 10/05/2016.
 */
public class Game implements Updatable {

    public static final int ENEMY_MELEE_SPAWN_INDEX = 0;
    public static final int ENEMY_RANGED_SPAWN_INDEX = 1;
    public static final int HERO_PROJECTILE_FIRED_INDEX = 2;
    public static final int ENEMY_PROJECTILE_FIRED_INDEX = 3;

    private boolean[] frameEvents;
    public int nNewProjectiles;

    public enum GameStatus {BUILDING, PLAYING, LOST}

    private GameStatus state;

    private Hero hero;

    private LinkedList<Enemy> enemies;
    private Trap[] traps;
    private LinkedList<Projectile> projectiles;

    public float stateTime;

    private int wave = 0;
    private int nEnemies = 10, nEnemiesWon = 0;
    private static final int maxEnemiesWon = 3;
    private int enemiesSpawned = 0;
    private int diffNextEnemy = 5;
    private final int enemyResist = 20, enemyHealth = 50, enemyStrength = 20;
    private final int resistPerWave = 5, healthPerWave = 10, strengthPerWave = 2;

    private int money = 0;
    private int trapCost = 100;
    private int score = 0;

    public Game(CharacterStats heroStats) {
        frameEvents = new boolean[4];
        frameEvents[ENEMY_MELEE_SPAWN_INDEX] = false;
        frameEvents[ENEMY_RANGED_SPAWN_INDEX] = false;
        frameEvents[HERO_PROJECTILE_FIRED_INDEX] = false;
        frameEvents[ENEMY_PROJECTILE_FIRED_INDEX] = false;
        nNewProjectiles = 0;

        int x = 300;
        int y = 144;

        hero = new Hero(this, x, y, heroStats);

        enemies = new LinkedList<>();
        traps = new Trap[26];
        projectiles = new LinkedList<>();

        state = GameStatus.BUILDING;
        stateTime = 0;
    }

    public GameStatus getState() {
        return state;
    }

    public void updatePlaying(float delta) {

        if (nEnemiesWon >= maxEnemiesWon) {
            state = GameStatus.LOST;
            return;
        }

        float currTime = stateTime + delta;
        hero.update(delta);

        for (Enemy e : enemies) {
            if (e.getPosition().x >= 4000) {
                nEnemiesWon++;
                e.setStates(Enemy.EnemyStatus.DEAD);
            } else e.update(delta);
        }
        for (Projectile p : projectiles)
            p.update(delta);
        for (Trap t : traps) {
            if (t == null)
                continue;
            t.update(delta);
        }

        int nextEnemy = wave >= 5 ? 1 : diffNextEnemy / wave;
        if (enemiesSpawned < nEnemies * wave &&
                Math.floor(stateTime / (float) nextEnemy) != Math.floor(currTime / (float) nextEnemy)) {
            enemiesSpawned++;
            Enemy e;
            Random rand = new Random();
            int type = rand.nextInt(2);
            if (type == 0) {
                e = new MeleeEnemy(this, 50, 144, enemyHealth + healthPerWave * wave,
                        enemyResist + resistPerWave * wave, enemyStrength + strengthPerWave * wave);
                frameEvents[ENEMY_MELEE_SPAWN_INDEX] = true;

            } else {
                e = new RangedEnemy(this, 50, 144, enemyHealth + healthPerWave * wave,
                        enemyResist + resistPerWave * wave, enemyStrength + strengthPerWave * wave);
                frameEvents[ENEMY_RANGED_SPAWN_INDEX] = true;
            }
            addEnemy(e);
        }

        stateTime = currTime;
    }

    public void update(float delta) {
        switch (state) {
            case PLAYING:
                if (enemiesSpawned == nEnemies * wave && enemies.size() == 0) {
                    stateTime = 0;
                    state = GameStatus.BUILDING;
                } else updatePlaying(delta);
                break;
            case BUILDING:
                break;
            case LOST:
                break;
        }
    }

    public void changeState(GameStatus status) {
        if (status == GameStatus.PLAYING) {
            state = status;
            enemies.clear();
            projectiles.clear();
            enemiesSpawned = 0;
            wave++;
        }
    }

    public boolean[] getFrameEvents() {
        return frameEvents;
    }

    public void setFrameEvents() {
        for (int i = 0; i < frameEvents.length; i++)
            frameEvents[i] = false;
        nNewProjectiles = 0;
    }

    public Hero getHero() {
        return hero;
    }

    /**
     * Getter for the hero's amount of money
     *
     * @return the hero's current amount of money
     */
    public int getMoney() {
        return money;
    }

    /**
     * Getter for the hero's current score
     *
     * @return the hero's current score
     */
    public int getScore() {
        return score;
    }

    public int getnEnemiesWon() {
        return nEnemiesWon;
    }

    public static int getMaxEnemiesWon() {
        return maxEnemiesWon;
    }

    public LinkedList<Enemy> getEnemies() {
        return enemies;
    }

    public LinkedList<Projectile> getProjectiles() {
        return projectiles;
    }

    public int getnNewProjectiles() {
        return nNewProjectiles;
    }

    public final Trap[] getTraps() {
        return traps;
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public void addProjectile(Projectile projectile, boolean heroSide) {
        if (heroSide)
            frameEvents[HERO_PROJECTILE_FIRED_INDEX] = true;
        else {
            nNewProjectiles++;
            frameEvents[ENEMY_PROJECTILE_FIRED_INDEX] = true;
        }
        projectiles.add(projectile);
    }

    public void eraseEnemy(int index) {
        money += 20;
        score += enemies.get(index).getPoints();
        enemies.remove(index);
    }

    public void eraseProjectile(int index) {
        projectiles.remove(index);
    }

    public void heroMove( int dir ) {
        hero.move(dir);
    }

    public void heroAttack() {
        hero.attack();
    }

    public void stopHero() {
        hero.stop();
    }

    public void setTrap(int x, int y, int width, int height, int index) {
        if( index < 0 || index >= traps.length )
            return ;

        if (traps[index] == null && money >= trapCost) {
            money -= trapCost;
            trapCost += 40;
            traps[index] = new Trap(this, x, y, width, height, 5);
        } else if (traps[index] != null) {
            trapCost -= 40;
            money += trapCost;
            traps[index] = null;
        }
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void lose() {
        state = GameStatus.LOST;
    }

}
