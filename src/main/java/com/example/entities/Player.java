package com.example.entities;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import com.example.items.*;
import com.example.Utils;

/**
 * プレイヤーデータを管理するクラス
 */
public class Player extends Entity{
    private List<Item> items = new ArrayList<>();
    private Weapon weapon;
    private Armor armor;
    
    public Player() {
        super("プレイヤー", 100, 25, 26);
        this.items = new ArrayList<>(Arrays.asList(new IronSword(), new IronArmor()));
        this.weapon = new IronSword();
        this.armor = new IronArmor();
    }
    public List<Item> getItems() {
        return items;
    }
    public void unequipWeapon() {
        this.weapon = new Weapon.NoWeapon();
    }
    public void unequipArmor() {
        this.armor = new Armor.NoArmor();
    }
    @Override
    public String getInfoText() {
        return String.format("%s: %s\n%s: %d/%d\n%s: %d\n%s: %d\n%s: %s（攻撃力: %d）\n%s: %s（防御力: %d）",
                           Utils.format("名前", 8), getName(),
                           Utils.format("HP", 8), getHp(), getMaxHp(),
                           Utils.format("攻撃力", 8), getAttack(),
                           Utils.format("防御力", 8), getDefence(),
                           Utils.format("武器", 8), getWeapon().getName(), getWeapon().getAttack(),
                           Utils.format("防具", 8), getArmor().getName(), getArmor().getDefense());
    }
}