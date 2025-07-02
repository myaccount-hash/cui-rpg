package com.example.actions;

import com.example.entities.Entity;

public class FireBall extends Magic {
  public FireBall(Entity executer, Entity target) {
    super("fireball", "ファイアボールを使う", "fireball", 25, executer);
  }

  @Override
  public boolean execute() {
    int damage = 30 + source.getAttack() - target.getDefence();
    target.takeDamage(damage);
    setCommandLog(source.getName() + "はファイアボールを使った！");
    return true;
  }
}
