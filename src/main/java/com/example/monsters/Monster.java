package com.example.monsters;

public abstract class Monster {
    protected String name;
    protected int hp;
    protected int maxHp;
    protected int attack;
    protected String icon;
    
    public Monster(String name, int hp, int attack, String icon) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.attack = attack;
        this.icon = icon;
    }
    
    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public int getAttack() { return attack; }
    public String getIcon() { return icon; }
    
    public void setName(String name) { this.name = name; }
    public void setHp(int hp) { this.hp = hp; }
    public void setMaxHp(int maxHp) { this.maxHp = maxHp; }
    public void setAttack(int attack) { this.attack = attack; }
    public void setIcon(String icon) { this.icon = icon; }
    
    public void takeDamage(int damage) { this.hp -= damage; }
}