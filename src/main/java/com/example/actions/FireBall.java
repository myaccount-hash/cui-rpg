package com.example.actions;

import com.example.entities.Entity;

public class FireBall extends Magic {
  public FireBall(Entity executer) {
    super("fireball", "ファイアボールを使う", 25, executer);
  }

  @Override
  protected void performMagic() {
    int damage = 30 + executor.getAttack() - getTarget().getDefence();
    getTarget().takeDamage(damage);
    setCommandLog(executor.getName() + "はファイアボールを使った！");
  }
}
