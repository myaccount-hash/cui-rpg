package com.example.entities;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import com.example.items.*;
import com.example.actions.*;
import com.example.Utils;

/**
 * プレイヤーデータを管理するクラス
 */
public class Player extends Entity{
    private List<Item> items = new ArrayList<>();
    
    public Player() {
        super("プレイヤー", 10000, 50, 25, 26, 1, new ArrayList<>());
        this.items = new ArrayList<>(Arrays.asList(new IronSword(), new IronArmor()));
        // 初期装備を設定
        setWeapon(new IronSword());
        setArmor(new IronArmor());
        
        // プレイヤーのスキルを設定
        addSkill(new Heal(this, this));
    }
    public List<Item> getItems() {
        return items;
    }
    @Override
    public String getInfoText() {
        String[] lines = {
            Utils.format("名前", 8) + ": " + getName(),
            Utils.format("レベル", 8) + ": " + getLevel(),
            Utils.format("HP", 8) + ": " + getHp() + "/" + getMaxHp(),
            Utils.format("MP", 8) + ": " + getMp() + "/" + getMaxMp(),
            Utils.format("EXP", 8) + ": " + getExp() + "/" + getRequiredExpForNextLevel(),
            Utils.format("攻撃力", 8) + ": " + getAttack(),
            Utils.format("防御力", 8) + ": " + getDefence(),
            Utils.format("武器", 8) + ": " + getWeapon().getName() + "（攻撃力: " + getWeapon().getAttack() + "）",
            Utils.format("防具", 8) + ": " + getArmor().getName() + "（防御力: " + getArmor().getDefense() + "）"
        };
        
        return String.join("\n", lines);
    }
}