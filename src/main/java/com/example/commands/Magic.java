package com.example.commands;

import com.example.entities.Entity;

/*
 * 魔法の抽象クラス。mpを消費するCommand。
 */
public abstract class Magic extends Command {
  protected int mpCost;

  public Magic(String name, String description, String commandName, int mpCost, Entity executer) {
    super(name, description, executer);
    this.mpCost = mpCost;
  }

  // 各魔法クラスで実装する抽象メソッド
  public int getMpCost() {
    return mpCost;
  }
}
