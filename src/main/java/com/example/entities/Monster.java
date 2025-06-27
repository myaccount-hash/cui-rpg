package com.example.entities;

import com.example.Utils;
import com.example.actions.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public abstract class Monster extends Entity{
    private String icon;
    private int baseDropExp;
    private static final Random random = new Random();
    
    public Monster(String name, int hp, int mp, int attack, int defence, int level, int baseDropExp, String icon) {
        super(name, hp, mp, attack, defence, level, createDefaultSkills());
        this.baseDropExp = baseDropExp;
        this.icon = icon;
    }
    
    private static List<Action> createDefaultSkills() {
        List<Action> skills = new ArrayList<>();
        // デフォルトスキルは空のリスト（サブクラスで設定）
        return skills;
    }
    
    // ランダム行動実行
    public String executeRandomAction(Entity target) {
        List<Action> availableActions = getAvailableActions();
        
        if (!availableActions.isEmpty()) {
            Action selectedAction = availableActions.get(random.nextInt(availableActions.size()));
            
            // targetを設定してスキル実行
            if (selectedAction instanceof Magic) {
                Magic magic = (Magic) selectedAction;
                magic.setTarget(target);
                magic.execute(new String[]{});
                return magic.getCommandLog();
            }
        }
        
        // 通常攻撃
        int damage = getAttack();
        target.takeDamage(damage);
        return getName() + "の攻撃！ " + damage + "ダメージ！";
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