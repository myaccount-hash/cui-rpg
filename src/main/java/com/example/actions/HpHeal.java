package com.example.actions;

import com.example.entities.Entity;

public class HpHeal extends Magic implements SelfTarget {
  public HpHeal(Entity executer, Entity target) {
    super("heal", "回復魔法を使う", "heal", 20, executer);
  }

  @Override
  public boolean execute() {
    target.heal(30);
    setCommandLog(source.getName() + "は回復魔法を使った！");
    return true;
  }
}
