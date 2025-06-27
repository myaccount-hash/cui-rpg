package com.example.entities;

import com.example.items.Weapon;
import com.example.items.Armor;
import com.example.Utils;

public abstract class Entity {
    private String name;
    private int hp;
    private int maxHp;
    private int attack;
    private int defence;
    private Weapon weapon;
    private Armor armor;
    
    protected Entity(String name, int hp, int attack, int defence) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.attack = attack;
        this.defence = defence;
        this.weapon = new Weapon.NoWeapon();
        this.armor = new Armor.NoArmor();
    }
    
    // 基本ゲッター
    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public int getAttack() { return attack + weapon.getAttack(); }
    public int getDefence() { return defence + armor.getDefense(); }
    public Weapon getWeapon() { return weapon; }
    public Armor getArmor() { return armor; }
    
    // 基本セッター（必要最小限）
    protected void setName(String name) { this.name = name; }
    protected void setHp(int hp) { this.hp = hp; }
    protected void setMaxHp(int maxHp) { this.maxHp = maxHp; }
    protected void setAttack(int attack) { this.attack = attack; }
    
    // 装備管理
    public void setWeapon(Weapon weapon) { 
        this.weapon = weapon != null ? weapon : new Weapon.NoWeapon(); 
    }
    
    public void setArmor(Armor armor) { 
        this.armor = armor != null ? armor : new Armor.NoArmor(); 
    }
    
    public void unequipWeapon() { setWeapon(null); }
    public void unequipArmor() { setArmor(null); }
    

    public void takeDamage(int damage) {
        this.hp = Math.max(0, this.hp - damage);
    }
    
    public void heal(int amount) {
        this.hp = Math.min(this.maxHp, this.hp + amount);
    }
    
    // 情報表示
    protected String getInfoText() {
        return String.format("%s: %s\n%s: %d/%d\n%s: %d\n%s: %d",
                           Utils.format("名前", 8), getName(),
                           Utils.format("HP", 8), getHp(), getMaxHp(),
                           Utils.format("攻撃力", 8), getAttack(),
                           Utils.format("防御力", 8), getDefence());
    }
}