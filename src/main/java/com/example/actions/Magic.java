package com.example.actions;

import com.example.core.Command;
import com.example.core.Entity;

/*
 * 魔法の抽象クラス。mpを消費するCommand。
 */
public abstract class Magic extends Command {
  protected int mpCost;

  public Magic(
      String name,
      String description,
      String commandName,
      int mpCost) {
    super(name, description);
    this.mpCost = mpCost;
  }

  // 各魔法クラスで実装する抽象メソッド
  public int getMpCost() {
    return mpCost;
  }
}
