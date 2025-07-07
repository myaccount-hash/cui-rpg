package com.example.entities;

import com.example.items.IItem;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** アイテムとゴールドを管理するクラス */
public class ItemBox {
  private List<IItem> IItems = new ArrayList<>();
  private int gold;

  public ItemBox() {
    this.gold = 0; // 初期所持金
  }

  public ItemBox(int initialGold) {
    this.gold = initialGold;
  }

  // アイテム管理
  public List<IItem> getItems() {
    return new ArrayList<>(IItems); // 防御的コピー
  }

  public void addItem(IItem IItem) {
    if (IItem != null) {
      IItems.add(IItem);
    }
  }

  public boolean removeItem(IItem IItem) {
    return IItems.remove(IItem);
  }

  public boolean hasItem(IItem IItem) {
    return IItems.contains(IItem);
  }

  public int getItemCount() {
    return IItems.size();
  }

  public void clearItems() {
    IItems.clear();
  }

  // アイテム集計機能
  public Map<String, IItemCount> getItemCounts() {
    Map<String, IItemCount> IItemCountMap = new LinkedHashMap<>();
    for (IItem IItem : IItems) {
      String key = IItem.getName();
      IItemCountMap.merge(
          key,
          new IItemCount(IItem, 1),
          (existing, newCount) -> {
            existing.count++;
            return existing;
          });
    }
    return IItemCountMap;
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
  public static class IItemCount {
    public final IItem IItem;
    public int count;

    public IItemCount(IItem IItem, int count) {
      this.IItem = IItem;
      this.count = count;
    }

    public String getDisplayName() {
      return count > 1 ? IItem.getName() + "(" + count + ")" : IItem.getName();
    }
  }
}
