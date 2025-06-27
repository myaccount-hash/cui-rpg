package com.example.actions;

import com.example.entities.Entity;
import com.example.sessions.Session;

// 実行元と対象を持つコマンドの抽象クラス。アイテムの使用、攻撃、魔法等を含む。
public abstract class Action extends Session.Command {
  protected Entity source;
  protected Entity target;

  public Action(String name, String description, String commandName, Entity source, Entity target) {
    super(name, description, commandName);
    this.source = source;
    this.target = target;
  }

  // 実行前チェック
  protected void validateExecution() {
    if (source == null) {
      throw new IllegalStateException("使用者が設定されていません");
    }
    if (target == null) {
      throw new IllegalStateException("対象が設定されていません");
    }
  }

  // または、target設定用メソッド
  public void setTarget(Entity target) {
    this.target = target;
  }
}
