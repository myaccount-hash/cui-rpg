package com.example.commands;

import com.example.entities.Entity;

public class FireBall extends Magic {
  public FireBall(Entity executer, Entity target) {
    super("fireball", "ファイアボールを使う", "fireball", 25, executer);
  }

  @Override
  public boolean execute() {
    int damage = 30 + executor.getAttack() - getTarget().getDefence();
    getTarget().takeDamage(damage);
    setCommandLog(executor.getName() + "はファイアボールを使った！");
    return true;
  }
}
