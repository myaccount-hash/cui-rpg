package com.example.utils;

/**
 * プレイヤーデータを管理するクラス
 */
public class Player {
    
    private String name;
    private int hp;
    private int maxHp;
    private int attack;
    
    public Player() {
        this("プレイヤー", 100, 100, 15);
    }
    
    public Player(String name, int hp, int maxHp, int attack) {
        this.name = name;
        this.hp = hp;
        this.maxHp = maxHp;
        this.attack = attack;
    }
    
    // Getters
    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public int getAttack() { return attack; }
    
    // Setters
    public void setName(String name) { this.name = name; }
    public void setHp(int hp) { this.hp = Math.min(hp, maxHp); }
    public void setMaxHp(int maxHp) { this.maxHp = maxHp; }
    public void setAttack(int attack) { this.attack = attack; }
    
    /**
     * ダメージを受ける
     */
    public void takeDamage(int damage) {
        this.hp = Math.max(0, this.hp - damage);
    }
    
    /**
     * HPを回復する
     */
    public void heal(int amount) {
        this.hp = Math.min(this.maxHp, this.hp + amount);
    }
} 