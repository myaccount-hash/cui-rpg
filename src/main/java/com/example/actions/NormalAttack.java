package com.example.actions;

import com.example.commands.Command;
import com.example.entities.Entity;

/*
 * 通常攻撃。全てのEntityがこれを可能。
 */
public class NormalAttack extends Command {
  public NormalAttack(Entity source, Entity target) {
    super("attack", "剣で攻撃");
  }

  @Override
  public boolean execute() {
    int totalAttack = source.getAttack();
    if (source.getWeapon() != null) {
      totalAttack += source.getWeapon().getAttack();
    }
    totalAttack = totalAttack - target.getDefence();
    target.takeDamage(totalAttack);
    setCommandLog(source.getName() + "の通常攻撃！");
    return true;
  }
}
