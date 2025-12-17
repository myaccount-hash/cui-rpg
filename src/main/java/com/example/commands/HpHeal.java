package com.example.commands;

import com.example.entities.Entity;

public class HpHeal extends Magic {
  public HpHeal(Entity executer) {
    super("heal", "回復魔法を使う", "heal", 20, executer);
  }

  @Override
  protected void performMagic() {
    executor.heal(30);
    setCommandLog(executor.getName() + "は回復魔法を使った！");
  }
}
