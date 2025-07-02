package com.example.actions;

import com.example.commands.Command;
import com.example.entities.Entity;

/*
 * 通常攻撃。全てのEntityがこれを可能。
 */
public class NormalAttack extends Command {
  public NormalAttack(Entity executer, Entity target) {
    super("attack", "通常攻撃", executer);
  }

  @Override
  public boolean execute() {
    int totalAttack = source.getAttack();
    if (source.getWeapon() != null) {
      totalAttack += source.getWeapon().getAttack();
    }
    int damage = totalAttack - target.getDefence();
    if (damage <= 0) damage = 1;
    target.takeDamage(damage);
    setCommandLog(source.getName() + "の通常攻撃！");
    return true;
  }
}
