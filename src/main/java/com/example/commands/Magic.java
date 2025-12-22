package com.example.commands;

import com.example.entities.Entity;

/*
 * 魔法の抽象クラス．mpを消費するCommand．
 */
public abstract class Magic extends Command {
  protected final int mpCost;

  public Magic(String name, String description, String commandName, int mpCost, Entity executer) {
    super(name, description, executer);
    this.mpCost = mpCost;
  }

  @Override
  public final boolean execute() {
    if (executor.getMp() < mpCost) {
      setCommandLog(executor.getName() + "はMPが足りない！");
      return false;
    }

    executor.useMp(mpCost);
    performMagic();
    return true;
  }

  protected abstract void performMagic();

  public int getMpCost() {
    return mpCost;
  }
}
