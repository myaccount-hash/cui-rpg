package com.example.entities;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import com.example.items.*;

/**
 * プレイヤーデータを管理するクラス
 */
public class Player extends Entity{
    private List<Item> items = new ArrayList<>();
    
    public Player() {
        super("プレイヤー", 100, 25, 26);
        this.items = new ArrayList<>(Arrays.asList(new IronSword(), new IronArmor()));
    }
    public List<Item> getItems() {
        return items;
    }
} 