package com.example.items;

import com.example.entities.Entity;

/** 鉄の鎧 基本的な防具アイテム 装備アクションは親クラスのArmorで自動的に提供される */
public class IronArmor extends Armor {
  public IronArmor(Entity owner) {
    super("Iron Armor", "鉄の鎧", 10, owner, 10);
  }
}
