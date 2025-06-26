package com.example.utils;

import java.io.*;
import java.nio.file.*;

/**
 * セーブデータを管理するクラス
 */
public class SaveDataManager {
    
    private static final String SAVE_FILE = "save_data.txt";
    
    /**
     * プレイヤーデータを保存
     */
    public static void savePlayer(Player player) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("name=").append(player.getName()).append("\n");
            sb.append("hp=").append(player.getHp()).append("\n");
            sb.append("maxHp=").append(player.getMaxHp()).append("\n");
            sb.append("attack=").append(player.getAttack()).append("\n");
            
            Files.write(Paths.get(SAVE_FILE), sb.toString().getBytes());
        } catch (IOException e) {
            System.err.println("セーブデータの保存に失敗しました: " + e.getMessage());
        }
    }
    
    /**
     * プレイヤーデータを読み込み
     */
    public static Player loadPlayer() {
        try {
            if (!Files.exists(Paths.get(SAVE_FILE))) {
                return new Player(); // デフォルトプレイヤーを返す
            }
            
            String content = new String(Files.readAllBytes(Paths.get(SAVE_FILE)));
            String[] lines = content.split("\n");
            
            String name = "プレイヤー";
            int hp = 100;
            int maxHp = 100;
            int attack = 15;
            
            for (String line : lines) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    
                    switch (key) {
                        case "name":
                            name = value;
                            break;
                        case "hp":
                            hp = Integer.parseInt(value);
                            break;
                        case "maxHp":
                            maxHp = Integer.parseInt(value);
                            break;
                        case "attack":
                            attack = Integer.parseInt(value);
                            break;
                    }
                }
            }
            
            return new Player(name, hp, maxHp, attack, new com.example.items.IronArmor(), new com.example.items.IronSword());
        } catch (Exception e) {
            System.err.println("セーブデータの読み込みに失敗しました: " + e.getMessage());
            return new Player(); // エラー時はデフォルトプレイヤーを返す
        }
    }
    
    /**
     * セーブデータが存在するかチェック
     */
    public static boolean hasSaveData() {
        return Files.exists(Paths.get(SAVE_FILE));
    }
} 