package com.lpoo.project.logic;

/**
 * Created by Diogo on 6/5/2016.
 */
public class TrapStats extends Stats {

    /**
     * Trap's attack damage
     */
    private int attDamage;

    /**
     * Trap's speed of attack
     */
    private float attackSpeed;

    /**
     * Trap's speed of recharge
     */
    private float rechargeSpeed;

    /**
     * Trap's speed of heat up
     */
    private float heatUpSpeed;

    /**
     * Trap's time of attack
     */
    private float timeAttack;

    public TrapStats(int attDamage, float attackSpeed, float rechargeSpeed, float heatUpSpeed, float timeAttack) {
        super();
        this.attDamage = attDamage;
        this.attackSpeed = attackSpeed;
        this.rechargeSpeed = rechargeSpeed;
        this.heatUpSpeed = heatUpSpeed;
        this.timeAttack = timeAttack;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }

    public float getHeatUpSpeed() {
        return heatUpSpeed;
    }

    public float getRechargeSpeed() {
        return rechargeSpeed;
    }

    public float getTimeAttack() {
        return timeAttack;
    }

    @Override
    public int getAttDamage() {
        return attDamage;
    }
}
