package com.example.commands;

import com.example.entities.IEntity;

/*
 * 魔法の抽象クラス。mpを消費するCommand。
 */
public abstract class Magic extends Command {
  protected int mpCost;

  public Magic(String name, String description, String commandName, int mpCost, IEntity executer) {
    super(name, description, executer);
    this.mpCost = mpCost;
  }

  public int getMpCost() {
    return mpCost;
  }
}
