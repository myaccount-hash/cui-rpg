package com.example.items;

import com.example.entities.IEntity;

public class IronSword extends Weapon {
  public IronSword(IEntity owner) {
    super("Iron Sword", "鉄の剣", 100, owner, 10);
  }
}
