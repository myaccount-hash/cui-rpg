package com.example.entities;

import com.example.Utils;

public abstract class Monster extends Entity{
    private String icon;
    private int baseDropExp;
    
    public Monster(String name, int hp, int mp, int attack, int defence, int level, int baseDropExp, String icon) {
        super(name, hp, mp, attack, defence, level);
        this.baseDropExp = baseDropExp;
        this.icon = icon;
    }
    public String getIcon() {
       return icon;
    }
    
    public int getDropExp() {
        return baseDropExp + (getLevel() - 1) * 20;
    }

    @Override
    public String getInfoText() {
        return String.format("%s: %s\n%s: %d\n%s: %d/%d\n%s: %d/%d\n%s: %d",
                           Utils.format("名前", 8), getName(),
                           Utils.format("レベル", 8), getLevel(),
                           Utils.format("HP", 8), getHp(), getMaxHp(),
                           Utils.format("MP", 8), getMp(), getMaxMp(),
                           Utils.format("攻撃力", 8), getAttack());
    }
}