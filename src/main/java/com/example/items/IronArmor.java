package com.example.items;

/**
 * 鉄の鎧
 * 基本的な防具アイテム
 * 装備アクションは親クラスのArmorで自動的に提供される
 */
public class IronArmor extends Armor {
  public IronArmor() {
    super("Iron Armor", "鉄の鎧", 10, 10);
  }
}
