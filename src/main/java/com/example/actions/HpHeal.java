package com.example.actions;

import com.example.entities.Entity;

public class HpHeal extends Magic implements Heal {
  public HpHeal(Entity source, Entity target) {
    super("heal", "回復魔法を使う", "heal", source, target, 20);
  }

  @Override
  protected boolean executeMagic(String[] args) {
    target.heal(30);
    setCommandLog(source.getName() + "は回復魔法を使った！");
    return true;
  }
}
