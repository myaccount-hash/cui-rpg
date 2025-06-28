package com.example.core;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;

/**
 * アイテムとゴールドを管理するクラス
 */
public class ItemBox {
    private List<Item> items = new ArrayList<>();
    private int gold;
    
    public ItemBox() {
        this.gold = 1000; // 初期所持金
    }
    
    public ItemBox(int initialGold) {
        this.gold = initialGold;
    }
    
    // アイテム管理
    public List<Item> getItems() {
        return new ArrayList<>(items); // 防御的コピー
    }
    
    public void addItem(Item item) {
        if (item != null) {
            items.add(item);
        }
    }
    
    public boolean removeItem(Item item) {
        return items.remove(item);
    }
    
    public boolean hasItem(Item item) {
        return items.contains(item);
    }
    
    public int getItemCount() {
        return items.size();
    }
    
    public void clearItems() {
        items.clear();
    }
    
    // アイテム集計機能
    public Map<String, ItemCount> getItemCounts() {
        Map<String, ItemCount> itemCountMap = new LinkedHashMap<>();
        for (Item item : items) {
            String key = item.getName();
            itemCountMap.merge(key, new ItemCount(item, 1), 
                (existing, newCount) -> {
                    existing.count++;
                    return existing;
                });
        }
        return itemCountMap;
    }
    
    // ゴールド管理
    public int getGold() {
        return gold;
    }
    
    public void setGold(int gold) {
        this.gold = Math.max(0, gold);
    }
    
    public void addGold(int amount) {
        if (amount > 0) {
            this.gold += amount;
        }
    }
    
    public boolean subtractGold(int amount) {
        if (amount > 0 && this.gold >= amount) {
            this.gold -= amount;
            return true;
        }
        return false;
    }
    
    public boolean canAfford(int cost) {
        return this.gold >= cost;
    }
    
    // アイテム個数管理用クラス
    public static class ItemCount {
        public final Item item;
        public int count;
        
        public ItemCount(Item item, int count) {
            this.item = item;
            this.count = count;
        }
        
        public String getDisplayName() {
            return count > 1 ? item.getName() + "(" + count + ")" : item.getName();
        }
    }
}