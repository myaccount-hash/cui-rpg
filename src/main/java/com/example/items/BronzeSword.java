package com.example.items;

import com.example.entities.IEntity;

public class BronzeSword extends Weapon {
  public BronzeSword(IEntity owner) {
    super("BronzeSword", "銅の剣", 10, owner, 10);
  }
}
