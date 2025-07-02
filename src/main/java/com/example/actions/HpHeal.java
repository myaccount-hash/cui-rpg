package com.example.actions;

import com.example.entities.Entity;

public class HpHeal extends Magic {
  public HpHeal(Entity executer, Entity target) {
    super("heal", "回復魔法を使う", "heal", 20, executer);
  }

  @Override
  public boolean execute() {
    executor.heal(30);
    setCommandLog(executor.getName() + "は回復魔法を使った！");
    return true;
  }
}
