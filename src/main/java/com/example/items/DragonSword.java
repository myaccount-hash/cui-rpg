package com.example.items;

import com.example.entities.IEntity;

public class DragonSword extends Weapon {
  public DragonSword(IEntity owner) {
    super("Dragon Sword", "伝説の剣。", 100, owner, 100);
  }
}
