package com.example.entities;
import java.util.List;
import com.example.items.*;

/**
 * プレイヤーデータを管理するクラス
 */
public class Player extends Entity{
    private List<Item> items;
    private Armor armor;
    private Weapon weapon;
    
    
    public Player() {
        this(new IronArmor(), new IronSword());
    }
    
    public Player(Armor armor, Weapon weapon) {
        super("プレイヤー", 100, 25, 26);
        this.armor = armor;
        this.weapon = weapon;
    }

    public Weapon getWeapon() {
        return weapon;
    }
} 