package com.example.items;

import com.example.entities.Entity;

public class IronSword extends Weapon {
  public IronSword(Entity owner) {
    super("Iron Sword", "鉄の剣", 100, owner, 10);
  }
}
