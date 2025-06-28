package com.example.actions;

import com.example.core.Entity;

public class FireBall extends Magic {
  public FireBall(Entity source, Entity target) {
    super("fireball", "ファイアボールを使う", "fireball", source, target, 25);
  }

  @Override
  public boolean execute() {
    int damage = 30 + source.getAttack() - target.getDefence();
    target.takeDamage(damage);
    setCommandLog(source.getName() + "はファイアボールを使った！");
    return true;
  }
}
