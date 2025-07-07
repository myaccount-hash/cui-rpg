package com.example.items;

import com.example.entities.IEntity;

/** 鉄の鎧 基本的な防具アイテム 装備アクションは親クラスのArmorで自動的に提供される */
public class IronArmor extends Armor {
  public IronArmor(IEntity owner) {
    super("Iron Armor", "鉄の鎧", 10, owner, 10);
  }
}
