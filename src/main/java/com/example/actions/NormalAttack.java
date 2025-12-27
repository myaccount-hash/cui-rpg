package com.example.actions;

import com.example.entities.Entity;

/*
 * 通常攻撃．全てのEntityがこれを可能．
 */
public class NormalAttack extends Action {
  public NormalAttack(Entity executor, Entity target) {
    super("attack", "通常攻撃", executor);
    if (target != null) {
      setTarget(target);
    }
  }

  @Override
  public boolean execute() {
    int totalAttack = executor.getAttack();
    if (executor.getWeapon() != null) {
      totalAttack += executor.getWeapon().getAttack();
    }
    int damage = totalAttack - getTarget().getDefence();
    if (damage <= 0) damage = 1;
    getTarget().takeDamage(damage);
    setCommandLog(executor.getName() + "の通常攻撃！");
    return true;
  }
}
