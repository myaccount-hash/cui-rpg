package com.example.entities;

import com.example.items.Weapon;
import com.example.items.Armor;
import com.example.Utils;

public abstract class Entity {
    private String name;
    private int hp;
    private int mp;
    private int exp;
    private int level;
    private int baseHp;
    private int baseMp;
    private int baseAttack;
    private int baseDefence;
    private Weapon weapon;
    private Armor armor;
    
    protected Entity(String name, int baseHp, int baseMp, int baseAttack, int baseDefence, int level) {
        this.name = name;
        this.level = level;
        this.baseHp = baseHp;
        this.baseMp = baseMp;
        this.baseAttack = baseAttack;
        this.baseDefence = baseDefence;
        
        // 初期HPとMPを設定
        this.hp = getMaxHp();
        this.mp = getMaxMp();
        this.exp = 0;
        
        this.weapon = new Weapon.NoWeapon();
        this.armor = new Armor.NoArmor();
    }
    
    // 基本ゲッター
    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getMp() { return mp; }
    public int getExp() { return exp; }
    public int getLevel() { return level; }
    
    // 種族値ゲッター
    public int getBaseHp() { return baseHp; }
    public int getBaseMp() { return baseMp; }
    public int getBaseAttack() { return baseAttack; }
    public int getBaseDefence() { return baseDefence; }
    
    // レベルに応じたステータス計算
    public int getMaxHp() { return baseHp + (level - 1) * 10; }
    public int getMaxMp() { return baseMp + (level - 1) * 5; }
    public int getAttack() { return baseAttack + (level - 1) * 2 + weapon.getAttack(); }
    public int getDefence() { return baseDefence + (level - 1) * 1 + armor.getDefense(); }
    
    public Weapon getWeapon() { return weapon; }
    public Armor getArmor() { return armor; }
    
    // 基本セッター（必要最小限）
    protected void setName(String name) { this.name = name; }
    protected void setHp(int hp) { this.hp = hp; }
    protected void setMp(int mp) { this.mp = mp; }
    protected void setExp(int exp) { this.exp = exp; }
    protected void setLevel(int level) { this.level = level; }
    protected void setBaseHp(int baseHp) { this.baseHp = baseHp; }
    protected void setBaseMp(int baseMp) { this.baseMp = baseMp; }
    protected void setBaseAttack(int baseAttack) { this.baseAttack = baseAttack; }
    protected void setBaseDefence(int baseDefence) { this.baseDefence = baseDefence; }
    
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
        this.hp = Math.min(getMaxHp(), this.hp + amount);
    }
    
    public void useMp(int amount) {
        this.mp = Math.max(0, this.mp - amount);
    }
    
    public void restoreMp(int amount) {
        this.mp = Math.min(getMaxMp(), this.mp + amount);
    }
    
    public void gainExp(int amount) {
        this.exp += amount;
        checkLevelUp();
    }
    
    // レベルアップ判定と処理
    private void checkLevelUp() {
        int requiredExp = getRequiredExpForNextLevel();
        while (exp >= requiredExp) {
            levelUp();
            requiredExp = getRequiredExpForNextLevel();
        }
    }
    
    private void levelUp() {
        level++;
        exp -= getRequiredExpForCurrentLevel();
        
        // HPとMPを全回復
        hp = getMaxHp();
        mp = getMaxMp();
    }
    
    // 次のレベルに必要な経験値
    public int getRequiredExpForNextLevel() {
        return level * 100;
    }
    
    // 現在のレベルに必要な経験値
    private int getRequiredExpForCurrentLevel() {
        return (level - 1) * 100;
    }
    
    // 情報表示
    protected String getInfoText() {
        return String.format("%s: %s\n%s: %d\n%s: %d/%d\n%s: %d/%d\n%s: %d\n%s: %d\n%s: %d",
                           Utils.format("名前", 8), getName(),
                           Utils.format("レベル", 8), getLevel(),
                           Utils.format("HP", 8), getHp(), getMaxHp(),
                           Utils.format("MP", 8), getMp(), getMaxMp(),
                           Utils.format("EXP", 8), getExp(),
                           Utils.format("攻撃力", 8), getAttack(),
                           Utils.format("防御力", 8), getDefence());
    }
}