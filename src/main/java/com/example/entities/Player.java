package com.example.entities;
import java.util.List;
import com.example.items.*;

/**
 * プレイヤーデータを管理するクラス
 */
public class Player extends Entity{
    private List<Item> items;
    
    public Player() {
        super("プレイヤー", 100, 25, 26);
    }
} 