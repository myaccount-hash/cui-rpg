package com.example.commands;

import com.example.entities.Entity;

public class FireBall extends Magic {
  public FireBall(Entity executer) {
    super("fireball", "ファイアボールを使う", "fireball", 25, executer);
  }

  @Override
  protected void performMagic() {
    int damage = 30 + executor.getAttack() - getTarget().getDefence();
    getTarget().takeDamage(damage);
    setCommandLog(executor.getName() + "はファイアボールを使った！");
  }
}
