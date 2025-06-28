package com.example.actions;

import com.example.commands.Command;
import com.example.entities.Entity;


/*
 * 実行元と対象を持つコマンドの抽象クラス。プレイヤー・モンスターの攻撃、魔法、アイテムの使用等を含む。
 */
public abstract class Action extends Command {
  protected Entity source;
  protected Entity target;

  public Action(String name, String description, String commandName, Entity source, Entity target) {
    super(name, description);
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
