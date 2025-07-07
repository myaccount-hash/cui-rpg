package com.example.items;

import com.example.entities.IEntity;

public class LeatherArmor extends Armor {
  public LeatherArmor(IEntity owner) {
    super("LeatherArmor", "革の鎧", 2, owner, 5);
  }
}
